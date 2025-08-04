import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Scanner;

class HydraUserException extends RuntimeException {
    private HydraValue exceptionValue;
    private String exceptionType;
    
    public HydraUserException(HydraValue value, String type) {
        super(value.asString());
        this.exceptionValue = value;
        this.exceptionType = type;
    }
    
    public HydraValue getExceptionValue() {
        return exceptionValue;
    }
    
    public String getExceptionType() {
        return exceptionType;
    }
}

public class HydraExecutor extends HydraBaseVisitor<HydraValue> {
    private SymbolTable symbolTable;
    private Stack<SymbolTable> scopeStack;
    private FunctionCallStack callStack;
    private Scanner globalScanner;
    
    // Excepciones para control de flujo
    public static class BreakException extends RuntimeException {}
    public static class ContinueException extends RuntimeException {}
    
    public HydraExecutor() {
        this.symbolTable = new SymbolTable();
        this.scopeStack = new Stack<>();
        this.callStack = new FunctionCallStack(); 
        this.globalScanner = new Scanner(System.in); 
    }
    
    // ================ GESTIÓN DE SCOPES ================
    
    private void pushScope() {
        scopeStack.push(symbolTable);
        symbolTable = new SymbolTable(symbolTable);
    }

    private void popScope() {
        if (!scopeStack.isEmpty()) {
            symbolTable = scopeStack.pop();
        }
    }
    
    // ================ PROGRAMA PRINCIPAL ================
    
    @Override
    public HydraValue visitPrograma(HydraParser.ProgramaContext ctx) {
        // 1) Si hay importList, visítala para cargar las librerías
        if (ctx.importList() != null) {
            visit(ctx.importList());
        }
        // 2) Luego visita el cuerpo del programa
        visit(ctx.cuerpo());
        return null;
    }

    
    @Override
    public HydraValue visitCuerpo(HydraParser.CuerpoContext ctx) {
        for (HydraParser.ElementocuerpoContext element : ctx.elementocuerpo()) {
            visit(element);
        }
        return null;
    }
    
    @Override
    public HydraValue visitBloque(HydraParser.BloqueContext ctx) {
        pushScope();
        try {
            visit(ctx.cuerpoGeneral());
        } finally {
            popScope();
        }
        return null;
    }
    
    @Override
    public HydraValue visitCuerpoGeneral(HydraParser.CuerpoGeneralContext ctx) {
        if (ctx.declaracion() != null) {
            for (HydraParser.DeclaracionContext decl : ctx.declaracion()) {
                visit(decl);
            }
        }
        if (ctx.sentencia() != null) {
            for (HydraParser.SentenciaContext stmt : ctx.sentencia()) {
                visit(stmt);
            }
        }
        return null;
    }
    
    // ================ ESTRUCTURAS DE CONTROL ================
    
    @Override
    public HydraValue visitIfStatement(HydraParser.IfStatementContext ctx) {
        HydraValue condition = visit(ctx.expression());
        boolean conditionValue = condition.asBool();
        
        if (conditionValue) {
            visit(ctx.sentencia(0));
        } else if (ctx.sentencia().size() > 1) {
            visit(ctx.sentencia(1));
        }
        return null;
    }
    
    @Override
    public HydraValue visitWhileStatement(HydraParser.WhileStatementContext ctx) {
        while (true) {
            HydraValue condition = visit(ctx.expression());
            boolean conditionValue = condition.asBool();
            
            if (!conditionValue) break;
            
            try {
                visit(ctx.sentencia());
            } catch (BreakException e) {
                break;
            } catch (ContinueException e) {
                continue;
            }
        }
        return null;
    }
    
    @Override
    public HydraValue visitDoWhileStatement(HydraParser.DoWhileStatementContext ctx) {
        do {
            try {
                visit(ctx.sentencia());
            } catch (BreakException e) {
                break;
            } catch (ContinueException e) {
                // Continúa con la evaluación de la condición
            }
            
            HydraValue condition = visit(ctx.expression());
            boolean conditionValue = condition.asBool();
            if (!conditionValue) break;
            
        } while (true);
        return null;
    }
    
    @Override
    public HydraValue visitForStatement(HydraParser.ForStatementContext ctx) {
        pushScope();
        
        try {
            if (ctx.forInit() != null) {
                visit(ctx.forInit());
            }
            
            while (true) {
                if (ctx.expression() != null) {
                    HydraValue condition = visit(ctx.expression());
                    boolean conditionValue = condition.asBool();
                    if (!conditionValue) break;
                } else {
                    System.err.println("Advertencia: For sin condición detectado, terminando para evitar bucle infinito");
                    break;
                }
                
                try {
                    visit(ctx.sentencia());
                } catch (BreakException e) {
                    break;
                } catch (ContinueException e) {
                    // Continúa con la actualización
                }
                
                if (ctx.forUpdate() != null) {
                    visit(ctx.forUpdate());
                }
            }
        } finally {
            popScope();
        }
        
        return null;
    }
    
    @Override
    public HydraValue visitForInit(HydraParser.ForInitContext ctx) {
        if (ctx.forInitDeclaracion() != null) {
            visit(ctx.forInitDeclaracion());
        } else if (ctx.expressionList() != null) {
            visit(ctx.expressionList());
        }
        return null;
    }
    
    @Override
    public HydraValue visitForInitDeclaracion(HydraParser.ForInitDeclaracionContext ctx) {
        String tipo = ctx.tipo().getText();
        
        for (HydraParser.DeclaratorContext declarator : ctx.declaratorList().declarator()) {
            String varName = declarator.IDENTIFIER() != null ? 
                           declarator.IDENTIFIER().getText() : 
                           declarator.CONSTID().getText();
            
            HydraValue value;
            if (declarator.expression() != null) {
                value = visit(declarator.expression());
            } else {
                value = getDefaultValue(tipo);
            }
            
            symbolTable.declare(varName, value);
        }
        return null;
    }
    
    @Override
    public HydraValue visitForUpdate(HydraParser.ForUpdateContext ctx) {
        if (ctx.expressionList() != null) {
            visit(ctx.expressionList());
        }
        return null;
    }
    
    @Override
    public HydraValue visitExpressionList(HydraParser.ExpressionListContext ctx) {
        HydraValue lastValue = null;
        for (HydraParser.ExpressionContext expr : ctx.expression()) {
            lastValue = visit(expr);
        }
        return lastValue;
    }
    
    // ================ SWITCH-CASE ================
    
    @Override
    public HydraValue visitSwitchStatement(HydraParser.SwitchStatementContext ctx) {
        HydraValue switchValue = visit(ctx.expression());
        boolean foundMatch = false;
        boolean fallThrough = false;
        
        pushScope();
        
        try {
            for (HydraParser.CaseBlockContext caseBlock : ctx.caseBlock()) {
                HydraValue caseValue = visit(caseBlock.expression());
                
                if (!foundMatch && compareValues(switchValue, caseValue, "==")) {
                    foundMatch = true;
                    fallThrough = true;
                }
                
                if (fallThrough) {
                    try {
                        visitCaseBlockContent(caseBlock);
                    } catch (BreakException e) {
                        fallThrough = false;
                        break;
                    }
                }
            }
            
            if (!foundMatch && ctx.defaultBlock() != null) {
                try {
                    visitDefaultBlockContent(ctx.defaultBlock());
                } catch (BreakException e) {
                    // Salir del switch
                }
            }
        } finally {
            popScope();
        }
        
        return null;
    }

    // ================ TRY-CATCH-FINALLY ================

    @Override
    public HydraValue visitTryStatement(HydraParser.TryStatementContext ctx) {
        HydraValue result = null;
        boolean exceptionCaught = false;
        
        try {
            pushScope();
            result = visit(ctx.bloque());
            
        } catch (HydraUserException e) {
            for (HydraParser.CatchBlockContext catchBlock : ctx.catchBlock()) {
                String catchType = extractCatchType(catchBlock);
                String exceptionType = e.getExceptionType();
                
                if (isTypeCompatible(exceptionType, catchType)) {
                    exceptionCaught = true;
                    
                    pushScope();
                    try {
                        String exceptionVarName = catchBlock.IDENTIFIER().getText();
                        symbolTable.declare(exceptionVarName, e.getExceptionValue());
                        result = visit(catchBlock.bloque());
                    } finally {
                        popScope();
                    }
                    break;
                }
            }
            
            if (!exceptionCaught) {
                throw e;
            }
            
        } catch (BreakException | ContinueException | ReturnException e) {
            popScope();
            
            if (ctx.finallyBlock() != null) {
                pushScope();
                try {
                    visit(ctx.finallyBlock().bloque());
                } finally {
                    popScope();
                }
            }
            
            throw e;
            
        } catch (Exception e) {
            popScope();
            
            if (ctx.finallyBlock() != null) {
                pushScope();
                try {
                    visit(ctx.finallyBlock().bloque());
                } finally {
                    popScope();
                }
            }
            
            throw new RuntimeException("Error del sistema: " + e.getMessage(), e);
            
        } finally {
            popScope();
        }
        
        if (ctx.finallyBlock() != null) {
            pushScope();
            try {
                visit(ctx.finallyBlock().bloque());
            } finally {
                popScope();
            }
        }
        
        return result;
    }

    private String extractCatchType(HydraParser.CatchBlockContext ctx) {
        return ctx.tipo().getText();
    }

    private boolean isTypeCompatible(String thrownType, String catchType) {
        if (thrownType.equals(catchType)) {
            return true;
        }
        
        switch (catchType) {
            case "string":
                return true;
            case "int":
                return thrownType.equals("int");
            case "float":
                return thrownType.equals("float") || thrownType.equals("int");
            case "double":
                return thrownType.equals("double") || thrownType.equals("float") || thrownType.equals("int");
            case "bool":
                return thrownType.equals("bool");
            case "char":
                return thrownType.equals("char");
            default:
                return false;
        }
    }
    
    private void visitCaseBlockContent(HydraParser.CaseBlockContext ctx) {
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);
            
            if (child instanceof HydraParser.DeclaracionContext) {
                visit((HydraParser.DeclaracionContext) child);
            } else if (child instanceof HydraParser.SentenciaContext) {
                visit((HydraParser.SentenciaContext) child);
            }
        }
    }
    
    private void visitDefaultBlockContent(HydraParser.DefaultBlockContext ctx) {
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);
            
            if (child instanceof HydraParser.DeclaracionContext) {
                visit((HydraParser.DeclaracionContext) child);
            } else if (child instanceof HydraParser.SentenciaContext) {
                visit((HydraParser.SentenciaContext) child);
            }
        }
    }
    
    // ================ CONTROL DE FLUJO ================
    
    @Override
    public HydraValue visitSentencia(HydraParser.SentenciaContext ctx) {
        if (ctx.BREAK() != null) {
            throw new BreakException();
        }       
        if (ctx.CONTINUE() != null) {
            throw new ContinueException();
        }
        
        if (ctx.RETURN() != null) {
            HydraValue returnValue = null;
            if (ctx.expression() != null) {
                returnValue = visit(ctx.expression());
            } else {
                returnValue = new HydraValue(null, "void");
            }
            throw new ReturnException(returnValue);
        }
        
        if (ctx.THROW() != null) {
            HydraValue exceptionValue = visit(ctx.expression());
            String exceptionType = exceptionValue.getType();
            throw new HydraUserException(exceptionValue, exceptionType);
        }
        
        return visitChildren(ctx);
    }
    
    // ================ DECLARACIONES ================
    
    @Override
    public HydraValue visitDeclaracion(HydraParser.DeclaracionContext ctx) {
        boolean isConst = ctx.CONST() != null;
        String tipo = ctx.tipo().getText();
        
        for (HydraParser.DeclaratorContext declarator : ctx.declaratorList().declarator()) {
            String varName = declarator.IDENTIFIER() != null ? 
                        declarator.IDENTIFIER().getText() : 
                        declarator.CONSTID().getText();
            
            HydraValue value;
            if (declarator.expression() != null) {
                value = visit(declarator.expression());
                
                if (tipo.contains("[]")) {
                    if (value.isArray()) {
                        value = new HydraValue(value.getArrayValue(), tipo, value.getDimensions());
                    } else {
                        String errorMsg = "No se puede asignar '" + value.getType() + "' a '" + tipo + "'";
                        throw new HydraUserException(new HydraValue(errorMsg), "string");
                    }
                }
            } else {
                if (tipo.contains("[]")) {
                    value = new HydraValue(new Object[0], tipo, new int[]{0});
                } else {
                    value = getDefaultValue(tipo);
                }
            }
            
            value.setConstant(isConst);
            symbolTable.declare(varName, value);
        }
        return null;
    }

    // ================ ENTRADA/SALIDA ================

    @Override
    public HydraValue visitIoStatement(HydraParser.IoStatementContext ctx) {
        if (ctx.OUTPUT() != null) {
            HydraValue result = visit(ctx.expression());
            System.out.println(result.asString());
        } else if (ctx.INPUT() != null) {
            return handleInputStatement(ctx);
        }
        return null;
    }

    private HydraValue handleInputStatement(HydraParser.IoStatementContext ctx) {
        try {
            HydraParser.ExpressionContext expr = ctx.expression();
            String variableName = extractVariableNameFromExpression(expr);
            
            if (variableName == null) {
                throw new RuntimeException("INPUT requiere una variable válida");
            }
            
            if (!symbolTable.exists(variableName)) {
                throw new RuntimeException("Variable no declarada: " + variableName);
            }
            
            HydraValue existingVar = symbolTable.getValue(variableName);
            String varType = existingVar.getType();
            
            HydraValue inputValue = readInputByType(varType, variableName);
            symbolTable.set(variableName, inputValue);
            
            System.out.println("DEBUG: Input leído para " + variableName + ": " + inputValue.asString());
            
            return inputValue;
            
        } catch (Exception e) {
            throw new RuntimeException("Error en operación INPUT: " + e.getMessage());
        }
    }

    private HydraValue readInputByType(String varType, String varName) {
        System.out.print("Ingrese valor para " + varName + " (" + varType + "): ");
        System.out.flush();
        
        try {
            String input = globalScanner.nextLine();
            
            switch (varType) {
                case "int":
                    try {
                        int intValue = Integer.parseInt(input.trim());
                        return new HydraValue(intValue, "int");
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Formato inválido para entero: " + input);
                    }
                    
                case "float":
                case "double":
                    try {
                        double doubleValue = Double.parseDouble(input.trim());
                        return new HydraValue(doubleValue, varType);
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Formato inválido para decimal: " + input);
                    }
                    
                case "string":
                    return new HydraValue(input, "string");
                    
                case "bool":
                    boolean boolValue = "true".equalsIgnoreCase(input.trim()) || 
                                      "1".equals(input.trim()) || 
                                      "yes".equalsIgnoreCase(input.trim()) ||
                                      "si".equalsIgnoreCase(input.trim());
                    return new HydraValue(boolValue, "bool");
                    
                case "char":
                    char charValue = input.length() > 0 ? input.charAt(0) : '\0';
                    return new HydraValue(charValue, "char");
                    
                default:
                    return new HydraValue(input, varType);
            }
            
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo entrada: " + e.getMessage());
        }
    }

    private String extractVariableNameFromExpression(HydraParser.ExpressionContext ctx) {
        try {
            HydraParser.AssignmentExprContext assignExpr = ctx.assignmentExpr();
            HydraParser.LogicalOrExprContext orExpr = assignExpr.logicalOrExpr();
            HydraParser.LogicalAndExprContext andExpr = orExpr.logicalAndExpr(0);
            HydraParser.EqualityExprContext eqExpr = andExpr.equalityExpr(0);
            HydraParser.RelationalExprContext relExpr = eqExpr.relationalExpr(0);
            HydraParser.AdditiveExprContext addExpr = relExpr.additiveExpr(0);
            HydraParser.MultiplicativeExprContext multExpr = addExpr.multiplicativeExpr(0);
            HydraParser.ExponentialExprContext expExpr = multExpr.exponentialExpr(0);
            HydraParser.UnaryExprContext unaryExpr = expExpr.unaryExpr();
            HydraParser.PostfixExprContext postExpr = unaryExpr.postfixExpr();
            HydraParser.PrimaryExprContext primExpr = postExpr.primaryExpr();
            
            if (primExpr.IDENTIFIER() != null) {
                return primExpr.IDENTIFIER().getText();
            }
            
            if (primExpr.CONSTID() != null) {
                return primExpr.CONSTID().getText();
            }
            
            return null;
            
        } catch (Exception e) {
            String fullText = ctx.getText();
            if (fullText.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                return fullText;
            }
            return null;
        }
    }

    public void closeScanner() {
        if (globalScanner != null) {
            globalScanner.close();
        }
    }
    
    // ================ LITERALES ================
    
    @Override
    public HydraValue visitLiteral(HydraParser.LiteralContext ctx) {
        if (ctx.NUMBER() != null) {
            return new HydraValue(Integer.parseInt(ctx.NUMBER().getText()));
        }
        if (ctx.REAL() != null) {
            return new HydraValue(Double.parseDouble(ctx.REAL().getText()));
        }
        if (ctx.STRING_LITERAL() != null) {
            String str = ctx.STRING_LITERAL().getText();
            str = str.substring(1, str.length() - 1);
            return new HydraValue(str);
        }
        if (ctx.CHAR_LITERAL() != null) {
            String charStr = ctx.CHAR_LITERAL().getText();
            char c = charStr.charAt(1);
            return new HydraValue(c);
        }
        if (ctx.TRUE() != null) {
            return new HydraValue(true);
        }
        if (ctx.FALSE() != null) {
            return new HydraValue(false);
        }
        return new HydraValue(0);
    }



















    // ================ EXPRESIONES ================
    
    @Override
    public HydraValue visitPrimaryExpr(HydraParser.PrimaryExprContext ctx) {
        System.out.println("DEBUG PRIMARY: " + ctx.getText());
        // Expresión entre paréntesis
        if (ctx.expression() != null) {
            return visit(ctx.expression());
        }
        
        // NEW (construcción de objetos) - IMPLEMENTACIÓN COMPLETA
        if (ctx.NEW() != null) {
            String className = ctx.IDENTIFIER().getText();
            
            //System.out.println("DEBUG: Intentando crear instancia de clase: " + className);
            
            // Verificar que la clase existe
            if (!symbolTable.classExists(className)) {
                throw new RuntimeException("Clase no declarada: " + className);
            }
            
            HydraClass classDefinition = symbolTable.getClass(className);
            
            // Crear instancia
            HydraInstance instance = classDefinition.createInstance();
            
            // Evaluar argumentos del constructor
            List<HydraValue> arguments = new ArrayList<>();
            if (ctx.expressionList() != null) {
                for (HydraParser.ExpressionContext expr : ctx.expressionList().expression()) {
                    arguments.add(visit(expr));
                }
            }
            
            // Llamar constructor si existe
            HydraFunction constructor = classDefinition.getConstructor();
            if (constructor != null) {
                // Verificar número de parámetros del constructor
                if (arguments.size() != constructor.getParameterCount()) {
                    throw new RuntimeException("Número incorrecto de argumentos para constructor de " + 
                                            className + ". Esperados: " + constructor.getParameterCount() + 
                                            ", Recibidos: " + arguments.size());
                }
                
                // Llamar constructor
                callConstructor(constructor, instance, arguments);
            } else if (!arguments.isEmpty()) {
                throw new RuntimeException("Clase " + className + " no tiene constructor que acepte argumentos");
            }
            
            System.out.println("DEBUG: Instancia creada exitosamente: " + className);
            
            // Retornar objeto
            return new HydraValue(instance);
        }
        
        // Llamadas a funciones (ANTES que variables)
        if (ctx.IDENTIFIER() != null && ctx.LPAREN() != null) {
            String functionName = ctx.IDENTIFIER().getText();
            
            //System.out.println("DEBUG: Intentando llamar función: " + functionName);
            
            // Verificar que la función existe
            if (!symbolTable.functionExists(functionName)) {
                throw new RuntimeException("Función no declarada: " + functionName);
            }
            
            HydraFunction function = symbolTable.getFunction(functionName);
            
            // Evaluar argumentos
            List<HydraValue> arguments = new ArrayList<>();
            if (ctx.expressionList() != null) {
                for (HydraParser.ExpressionContext expr : ctx.expressionList().expression()) {
                    arguments.add(visit(expr));
                }
            }
            
            // Verificar número de parámetros
            if (arguments.size() != function.getParameterCount()) {
                throw new RuntimeException("Número incorrecto de argumentos para función " + 
                                        functionName + ". Esperados: " + function.getParameterCount() + 
                                        ", Recibidos: " + arguments.size());
            }
            
            // Llamar a la función
            return callFunction(function, arguments);
        }
        
        // Variables normales y self
        if (ctx.IDENTIFIER() != null) {
            String varName = ctx.IDENTIFIER().getText();
            HydraValue value = symbolTable.get(varName);
            if (value == null) {
                throw new RuntimeException("Variable no declarada: " + varName);
            }
            return value;
        }

        
        // Constantes
        if (ctx.CONSTID() != null) {
            String constName = ctx.CONSTID().getText();
            return symbolTable.getValue(constName);
        }
        
        // Literales
        if (ctx.literal() != null) {
            return visitLiteral(ctx.literal());
        }
        
        // Inicialización de arreglos
        if (ctx.arrayInitializer() != null) {
            return visitArrayInitializer(ctx.arrayInitializer());
        }

        // Acceso a self
        if (ctx.SELF() != null) {
        HydraValue selfVal = symbolTable.get("self");
        if (selfVal == null || !selfVal.isObject()) {
            throw new RuntimeException("Uso de 'self' fuera del contexto de un objeto");
        }
        return selfVal;
    }
        
        throw new RuntimeException("Expresión primaria no reconocida");
    }




















    
    // ================ INCREMENTO/DECREMENTO - NUEVA IMPLEMENTACIÓN ================



    
    @Override
    public HydraValue visitPostfixExpr(HydraParser.PostfixExprContext ctx) {
        HydraValue base = visit(ctx.primaryExpr());
        
        // Procesar todos los operadores postfix
        for (int i = 1; i < ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);
            String op = child.getText();
            
            switch (op) {
                case "++":
                    // POST-INCREMENTO: devuelve valor actual, luego incrementa
                    if (ctx.primaryExpr().IDENTIFIER() != null) {
                        String varName = ctx.primaryExpr().IDENTIFIER().getText();
                        HydraValue currentValue = base; // Valor antes del incremento
                        
                        // Incrementar la variable en el SymbolTable
                        if (base.getType().equals("int")) {
                            int newValue = base.asInt() + 1;
                            symbolTable.set(varName, new HydraValue(newValue, "int"));
                        } else if (base.getType().equals("float") || base.getType().equals("double")) {
                            double newValue = base.asDouble() + 1.0;
                            symbolTable.set(varName, new HydraValue(newValue, base.getType()));
                        } else {
                            throw new RuntimeException("Operador ++ no soportado para tipo: " + base.getType());
                        }
                        
                        // Retornar el valor ANTES del incremento (post-incremento)
                        return currentValue;
                    } else if (ctx.primaryExpr().CONSTID() != null) {
                        String varName = ctx.primaryExpr().CONSTID().getText();
                        HydraValue currentValue = base; // Valor antes del incremento
                        
                        // Incrementar la variable en el SymbolTable
                        if (base.getType().equals("int")) {
                            int newValue = base.asInt() + 1;
                            symbolTable.set(varName, new HydraValue(newValue, "int"));
                        } else if (base.getType().equals("float") || base.getType().equals("double")) {
                            double newValue = base.asDouble() + 1.0;
                            symbolTable.set(varName, new HydraValue(newValue, base.getType()));
                        } else {
                            throw new RuntimeException("Operador ++ no soportado para tipo: " + base.getType());
                        }
                        
                        return currentValue;
                    } else {
                        throw new RuntimeException("Operador ++ solo puede aplicarse a variables");
                    }
                    
                case "--":
                    // POST-DECREMENTO: devuelve valor actual, luego decrementa
                    if (ctx.primaryExpr().IDENTIFIER() != null) {
                        String varName = ctx.primaryExpr().IDENTIFIER().getText();
                        HydraValue currentValue = base; // Valor antes del decremento
                        
                        // Decrementar la variable en el SymbolTable
                        if (base.getType().equals("int")) {
                            int newValue = base.asInt() - 1;
                            symbolTable.set(varName, new HydraValue(newValue, "int"));
                        } else if (base.getType().equals("float") || base.getType().equals("double")) {
                            double newValue = base.asDouble() - 1.0;
                            symbolTable.set(varName, new HydraValue(newValue, base.getType()));
                        } else {
                            throw new RuntimeException("Operador -- no soportado para tipo: " + base.getType());
                        }
                        
                        // Retornar el valor ANTES del decremento (post-decremento)
                        return currentValue;
                    } else if (ctx.primaryExpr().CONSTID() != null) {
                        String varName = ctx.primaryExpr().CONSTID().getText();
                        HydraValue currentValue = base; // Valor antes del decremento
                        
                        // Decrementar la variable en el SymbolTable
                        if (base.getType().equals("int")) {
                            int newValue = base.asInt() - 1;
                            symbolTable.set(varName, new HydraValue(newValue, "int"));
                        } else if (base.getType().equals("float") || base.getType().equals("double")) {
                            double newValue = base.asDouble() - 1.0;
                            symbolTable.set(varName, new HydraValue(newValue, base.getType()));
                        } else {
                            throw new RuntimeException("Operador -- no soportado para tipo: " + base.getType());
                        }
                        
                        return currentValue;
                    } else {
                        throw new RuntimeException("Operador -- solo puede aplicarse a variables");
                    }
                    
                case ".":
    // ACCESO A PROPIEDADES/MÉTODOS DE OBJETOS
    if (i + 1 < ctx.getChildCount()) {
        String memberName = ctx.getChild(i + 1).getText();

        if (base.isObject()) {
            HydraInstance instance = base.getObjectInstance();
            System.out.println("DEBUG: Acceso a objeto de clase: " + instance.getClassName());

            // Primero intentamos acceder a un atributo
            try {
                HydraValue attributeValue = instance.getAttribute(memberName);
                base = attributeValue;
                i++; // Saltamos el nombre del atributo
                System.out.println("DEBUG: Atributo encontrado: " + memberName + " = " + attributeValue.asString());
                continue;
            } catch (RuntimeException e) {
                System.out.println("DEBUG: " + memberName + " no es atributo, verificando si es método");

                // Si no es atributo, verificamos si es un método
                HydraFunction method = instance.getMethod(memberName);
                if (method != null) {
                    // ¿Es una llamada a método? (verificamos si sigue un paréntesis)
                    if (i + 2 < ctx.getChildCount() && "(".equals(ctx.getChild(i + 2).getText())) {
                        HydraValue methodMarker = new HydraValue("METHOD:" + memberName + ":" + instance.getClassName());
                        methodMarker.setObjectInstance(instance);
                        base = methodMarker;
                        i += 2; // Saltamos el nombre del método y el '('
                        continue;
                    } else {
                        String errorMsg = "Método '" + memberName + "' requiere llamada con ()";
                        throw new HydraUserException(new HydraValue(errorMsg), "string");
                    }
                }

                // Ni atributo ni método
                String errorMsg = "Miembro '" + memberName + "' no encontrado en clase " + instance.getClassName();
                throw new HydraUserException(new HydraValue(errorMsg), "string");
            }
        }

        // Manejo especial para arreglos: propiedad length
        if ("length".equals(memberName)) {
            if (base.isArray()) {
                Object[] array = base.getArrayValue();
                int length = array != null ? array.length : 0;
                base = new HydraValue(length, "int");
                i++; // Saltamos 'length'
                continue;
            } else {
                String errorMsg = "El tipo '" + base.getType() + "' no tiene propiedad 'length'";
                throw new HydraUserException(new HydraValue(errorMsg), "string");
            }
        }

        // Si no era objeto ni arreglo
        String errorMsg = "Propiedad/método '" + memberName + "' no reconocido para tipo '" + base.getType() + "'";
        throw new HydraUserException(new HydraValue(errorMsg), "string");
    }
    break;

                    
                case "[":
                    // ACCESO A ARREGLO - IMPLEMENTACIÓN COMPLETA
                    if (i + 2 < ctx.getChildCount()) {
                        ParseTree indexChild = ctx.getChild(i + 1);
                        
                        // Evaluar el índice
                        HydraValue indexValue;
                        if (indexChild instanceof HydraParser.ExpressionContext) {
                            indexValue = visit((HydraParser.ExpressionContext) indexChild);
                        } else {
                            // Buscar la expresión en los hijos
                            HydraValue tempIndex = null;
                            for (int j = i + 1; j < ctx.getChildCount(); j++) {
                                ParseTree temp = ctx.getChild(j);
                                if (temp.getText().equals("]")) break;
                                if (temp instanceof HydraParser.ExpressionContext) {
                                    tempIndex = visit((HydraParser.ExpressionContext) temp);
                                    break;
                                }
                            }
                            indexValue = tempIndex != null ? tempIndex : new HydraValue(0);
                        }
                        
                        // Acceder al elemento del arreglo
                        if (base.isArray()) {
                            int index = indexValue.asInt();
                            Object[] array = base.getArrayValue();
                            
                            if (index >= 0 && index < array.length) {
                                Object element = array[index];
                                if (element instanceof HydraValue) {
                                    base = (HydraValue) element;
                                } else {
                                    base = new HydraValue(element);
                                }
                            } else {
                                String errorMsg = "Índice fuera de rango: " + index;
                                throw new HydraUserException(new HydraValue(errorMsg), "string");
                            }
                        } else {
                            String errorMsg = "No se puede acceder por índice a un tipo que no es arreglo";
                            throw new HydraUserException(new HydraValue(errorMsg), "string");
                        }
                        
                        // Saltar hasta después del ']'
                        while (i < ctx.getChildCount() && !ctx.getChild(i).getText().equals("]")) {
                            i++;
                        }
                    }
                    break;
                    
                case "(":
                    // LLAMADA A MÉTODO DE OBJETO
                    if (base.getType().startsWith("METHOD:")) {
                        // Extraer información del marcador
                        String methodInfo = base.asString(); // e.g., METHOD:saludar:Persona
                        String[] parts = methodInfo.split(":");
                        String methodName = parts[1];
                        String className = parts[2];

                        System.out.println("DEBUG: Llamando método " + methodName + " de clase " + className);

                        // Obtener la instancia asociada al método
                        HydraInstance instance = base.getObjectInstance();
                        if (instance == null) {
                            throw new RuntimeException("No se pudo recuperar la instancia del objeto para el método " + methodName);
                        }

                        // Obtener el método desde la clase
                        HydraFunction method = instance.getMethod(methodName);
                        if (method == null) {
                            throw new RuntimeException("Método '" + methodName + "' no encontrado en clase " + className);
                        }

                        // Evaluar argumentos si existen
                        List<HydraValue> arguments = new ArrayList<>();
                        if (i + 1 < ctx.getChildCount()) {
                            for (int j = i + 1; j < ctx.getChildCount(); j++) {
                                ParseTree child2 = ctx.getChild(j);
                                if (child2.getText().equals(")")) break;

                                if (child2 instanceof HydraParser.ExpressionListContext) {
                                    HydraParser.ExpressionListContext exprList = (HydraParser.ExpressionListContext) child2;
                                    for (HydraParser.ExpressionContext expr : exprList.expression()) {
                                        arguments.add(visit(expr));
                                    }
                                    break;
                                }
                            }
                        }

                        // Validar número de parámetros
                        if (arguments.size() != method.getParameterCount()) {
                            throw new RuntimeException("Número incorrecto de argumentos para método '" + methodName +
                                "'. Esperados: " + method.getParameterCount() + ", Recibidos: " + arguments.size());
                        }

                        // Crear nuevo scope para el método
                        SymbolTable methodScope = new SymbolTable(symbolTable);
                        SymbolTable previousScope = symbolTable;

                        try {
                            symbolTable = methodScope;

                            // Inyectar 'self'
                            symbolTable.declare("self", new HydraValue(instance));

                            // Inyectar parámetros
                            List<String> paramNames = method.getParameterNames();
                            List<String> paramTypes = method.getParameterTypes();
                            for (int k = 0; k < arguments.size(); k++) {
                                String paramName = paramNames.get(k);
                                String paramType = paramTypes.get(k);
                                HydraValue arg = arguments.get(k);

                                if (!isFunctionTypeCompatible(arg.getType(), paramType)) {
                                    throw new RuntimeException("Tipo incompatible en parámetro '" + paramName +
                                        "'. Esperado: " + paramType + ", recibido: " + arg.getType());
                                }

                                symbolTable.declare(paramName, arg);
                            }

                            // Ejecutar el cuerpo del método
                            try {
                                visit(method.getBody());
                                return new HydraValue(null, "void");
                            } catch (ReturnException e) {
                                return e.getReturnValue();
                            }
                        } finally {
                            symbolTable = previousScope;
                        }
                    }
                    break;


                    
                default:
                    // Ignorar otros operadores no reconocidos
                    break;
            }
        }
        
        return base;
    }











/////////////////////// 


    @Override
    public HydraValue visitArrayInitializer(HydraParser.ArrayInitializerContext ctx) {
        java.util.List<HydraParser.ExpressionContext> expressions = ctx.expression();
        
        if (expressions == null || expressions.isEmpty()) {
            // Arreglo vacío {}
            return new HydraValue(new Object[0], "unknown[]", new int[]{0});
        }
        
        // Evaluar todas las expresiones
        Object[] elements = new Object[expressions.size()];
        String inferredType = null;
        
        for (int i = 0; i < expressions.size(); i++) {
            HydraValue value = visit(expressions.get(i));
            elements[i] = value;
            
            // Inferir tipo del primer elemento
            if (i == 0) {
                inferredType = value.getType() + "[]";
            }
        }
        
        // Crear arreglo con tipo inferido
        return new HydraValue(elements, inferredType, new int[]{elements.length});
    }
    
    // ================ RESTO DE EXPRESIONES ================
    




    @Override
public HydraValue visitAssignmentExpr(HydraParser.AssignmentExprContext ctx) {
    // Si hay RHS, es una asignación
    if (ctx.assignmentExpr() != null) {
        String operator = ctx.getChild(1).getText();               // "=" o "+=" etc.
        HydraValue rightSide = visit(ctx.assignmentExpr());        // valor de la derecha

        // 1) CASO: asignación a elemento de arreglo
        // Detectamos si el LHS es un postfixExpr con "["
        ParseTree lhs = ctx.getChild(0);
        if (lhs instanceof HydraParser.LogicalOrExprContext) {
            HydraParser.LogicalOrExprContext lor = (HydraParser.LogicalOrExprContext) lhs;
            HydraParser.PostfixExprContext post = lor
                .logicalAndExpr(0)
                .equalityExpr(0)
                .relationalExpr(0)
                .additiveExpr(0)
                .multiplicativeExpr(0)
                .exponentialExpr(0)
                .unaryExpr()
                .postfixExpr();

            if (post.getChildCount() > 1 && "[".equals(post.getChild(1).getText())) {
                // nombre del arreglo
                String arrayName = post.primaryExpr().IDENTIFIER().getText();
                HydraValue arrayVal = symbolTable.getValue(arrayName);
                if (!arrayVal.isArray()) {
                    throw new HydraUserException(new HydraValue("No es un arreglo: " + arrayName), "string");
                }

                // índice
                HydraParser.ExpressionContext idxCtx = (HydraParser.ExpressionContext) post.getChild(2);
                int idx = visit(idxCtx).asInt();

                Object[] raw = arrayVal.getArrayValue();
                if (idx < 0 || idx >= raw.length) {
                    throw new HydraUserException(new HydraValue("Índice fuera de rango: " + idx), "string");
                }

                // aquí sí actualizamos sólo el elemento
                raw[idx] = rightSide;
                return rightSide;
            }
        }

        // 2) CASO “normal” de variable simple
        String varName = extractVariableName(ctx.logicalOrExpr());
        if (varName != null) {
            HydraValue current = symbolTable.getValue(varName);
            HydraValue result = performAssignment(current, rightSide, operator);
            symbolTable.set(varName, result);
            return result;
        }
    }

    // No era asignación: devolvemos la expresión lógica
    return visit(ctx.logicalOrExpr());
}












    
    private String extractVariableName(HydraParser.LogicalOrExprContext ctx) {
        try {
            HydraParser.LogicalAndExprContext andCtx = ctx.logicalAndExpr(0);
            HydraParser.EqualityExprContext eqCtx = andCtx.equalityExpr(0);
            HydraParser.RelationalExprContext relCtx = eqCtx.relationalExpr(0);
            HydraParser.AdditiveExprContext addCtx = relCtx.additiveExpr(0);
            HydraParser.MultiplicativeExprContext multCtx = addCtx.multiplicativeExpr(0);
            HydraParser.ExponentialExprContext expCtx = multCtx.exponentialExpr(0);
            
            if (expCtx.unaryExpr() != null) {
                HydraParser.UnaryExprContext unaryCtx = expCtx.unaryExpr();
                if (unaryCtx.postfixExpr() != null) {
                    HydraParser.PostfixExprContext postCtx = unaryCtx.postfixExpr();
                    if (postCtx.primaryExpr() != null) {
                        HydraParser.PrimaryExprContext primCtx = postCtx.primaryExpr();
                        if (primCtx.IDENTIFIER() != null) {
                            return primCtx.IDENTIFIER().getText();
                        }
                        if (primCtx.CONSTID() != null) {
                            return primCtx.CONSTID().getText();
                        }
                    }
                }
            }
        } catch (Exception e) {
            String text = ctx.getText();
            if (text.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
                return text;
            }
        }
        return null;
    }
    
    private HydraValue performAssignment(HydraValue current, HydraValue right, String operator) {
        switch (operator) {
            case "=":
                return right;
            case "+=":
                if (current.getType().equals("string") || right.getType().equals("string")) {
                    return new HydraValue(current.asString() + right.asString());
                } else if (current.getType().equals("int") && right.getType().equals("int")) {
                    return new HydraValue(current.asInt() + right.asInt(), "int");
                } else {
                    return new HydraValue(current.asDouble() + right.asDouble(), "float");
                }
            case "-=":
                if (current.getType().equals("int") && right.getType().equals("int")) {
                    return new HydraValue(current.asInt() - right.asInt(), "int");
                } else {
                    return new HydraValue(current.asDouble() - right.asDouble(), "float");
                }
            case "*=":
                if (current.getType().equals("int") && right.getType().equals("int")) {
                    return new HydraValue(current.asInt() * right.asInt(), "int");
                } else {
                    return new HydraValue(current.asDouble() * right.asDouble(), "float");
                }
            case "/=":
                if (right.asDouble() == 0) {
                    throw new RuntimeException("Error: División por cero en /=");
                }
                double resultado = current.asDouble() / right.asDouble();
                if (current.getType().equals("int") && right.getType().equals("int") && 
                    resultado == Math.floor(resultado)) {
                    return new HydraValue((int)resultado, "int");
                } else {
                    return new HydraValue(resultado, "float");
                }
            case "%=":
                if (right.asInt() == 0) {
                    throw new RuntimeException("Error: Módulo por cero en %=");
                }
                return new HydraValue(current.asInt() % right.asInt(), "int");
            default:
                return right;
        }
    }
    
    @Override
    public HydraValue visitLogicalOrExpr(HydraParser.LogicalOrExprContext ctx) {
        HydraValue left = visit(ctx.logicalAndExpr(0));
        
        for (int i = 1; i < ctx.logicalAndExpr().size(); i++) {
            if (left.asBool()) {
                return new HydraValue(true, "bool");
            }
            HydraValue right = visit(ctx.logicalAndExpr(i));
            left = new HydraValue(left.asBool() || right.asBool(), "bool");
        }
        return left;
    }
    
    @Override
    public HydraValue visitLogicalAndExpr(HydraParser.LogicalAndExprContext ctx) {
        HydraValue left = visit(ctx.equalityExpr(0));
        
        for (int i = 1; i < ctx.equalityExpr().size(); i++) {
            if (!left.asBool()) {
                return new HydraValue(false, "bool");
            }
            HydraValue right = visit(ctx.equalityExpr(i));
            left = new HydraValue(left.asBool() && right.asBool(), "bool");
        }
        return left;
    }
    
    @Override
    public HydraValue visitEqualityExpr(HydraParser.EqualityExprContext ctx) {
        HydraValue left = visit(ctx.relationalExpr(0));
        
        for (int i = 1; i < ctx.relationalExpr().size(); i++) {
            HydraValue right = visit(ctx.relationalExpr(i));
            String op = ctx.getChild(2 * i - 1).getText();
            
            boolean result = compareValues(left, right, op);
            left = new HydraValue(result, "bool");
        }
        return left;
    }
    
    @Override
    public HydraValue visitRelationalExpr(HydraParser.RelationalExprContext ctx) {
        HydraValue left = visit(ctx.additiveExpr(0));
        
        for (int i = 1; i < ctx.additiveExpr().size(); i++) {
            HydraValue right = visit(ctx.additiveExpr(i));
            String op = ctx.getChild(2 * i - 1).getText();
            
            boolean result = compareValues(left, right, op);
            left = new HydraValue(result, "bool");
        }
        return left;
    }
    
    private boolean compareValues(HydraValue left, HydraValue right, String op) {
        if (left.getType().equals("string") || right.getType().equals("string")) {
            String leftStr = left.asString();
            String rightStr = right.asString();
            switch (op) {
                case "==": return leftStr.equals(rightStr);
                case "!=": return !leftStr.equals(rightStr);
                case "<": return leftStr.compareTo(rightStr) < 0;
                case "<=": return leftStr.compareTo(rightStr) <= 0;
                case ">": return leftStr.compareTo(rightStr) > 0;
                case ">=": return leftStr.compareTo(rightStr) >= 0;
            }
        }
        
        if (left.getType().equals("bool") && right.getType().equals("bool")) {
            boolean leftBool = left.asBool();
            boolean rightBool = right.asBool();
            switch (op) {
                case "==": return leftBool == rightBool;
                case "!=": return leftBool != rightBool;
                case "<": return !leftBool && rightBool;
                case "<=": return !leftBool || rightBool;
                case ">": return leftBool && !rightBool;
                case ">=": return leftBool || !rightBool;
            }
        }
        
        double leftNum = left.asDouble();
        double rightNum = right.asDouble();
        switch (op) {
            case "==": return Math.abs(leftNum - rightNum) < 1e-10;
            case "!=": return Math.abs(leftNum - rightNum) >= 1e-10;
            case "<": return leftNum < rightNum;
            case "<=": return leftNum <= rightNum;
            case ">": return leftNum > rightNum;
            case ">=": return leftNum >= rightNum;
            default: return false;
        }
    }
    
    @Override
    public HydraValue visitAdditiveExpr(HydraParser.AdditiveExprContext ctx) {
        HydraValue left = visit(ctx.multiplicativeExpr(0));
        
        for (int i = 1; i < ctx.multiplicativeExpr().size(); i++) {
            HydraValue right = visit(ctx.multiplicativeExpr(i));
            String op = ctx.getChild(2 * i - 1).getText();
            
            if (op.equals("+")) {
                if (left.getType().equals("string") || right.getType().equals("string")) {
                    left = new HydraValue(left.asString() + right.asString());
                } else {
                    if (left.getType().equals("int") && right.getType().equals("int")) {
                        left = new HydraValue(left.asInt() + right.asInt(), "int");
                    } else {
                        left = new HydraValue(left.asDouble() + right.asDouble(), "float");
                    }
                }
            } else if (op.equals("-")) {
                if (left.getType().equals("int") && right.getType().equals("int")) {
                    left = new HydraValue(left.asInt() - right.asInt(), "int");
                } else {
                    left = new HydraValue(left.asDouble() - right.asDouble(), "float");
                }
            }
        }
        return left;
    }



    //////////////MATEMATICAS//////////////////////////////////////////
    @Override
    public HydraValue visitImportStatement(HydraParser.ImportStatementContext ctx) {
        String pkg = ctx.IDENTIFIER(0).getText().toLowerCase();
        String lib = ctx.IDENTIFIER(1).getText().toLowerCase();
        if (!"math".equals(pkg)) {
            throw new RuntimeException("Librería desconocida: " + pkg + "." + lib);
        }
        switch(lib) {
            case "sin":
                symbolTable.declareFunction("sin",
                    new BuiltinFunction("sin", 1,
                        args -> new HydraValue(Math.sin(args.get(0).asDouble()), "double")));
                break;
            case "cos":
                symbolTable.declareFunction("cos",
                    new BuiltinFunction("cos", 1,
                        args -> new HydraValue(Math.cos(args.get(0).asDouble()), "double")));
                break;
            case "tan":
                symbolTable.declareFunction("tan",
                    new BuiltinFunction("tan", 1,
                        args -> new HydraValue(Math.tan(args.get(0).asDouble()), "double")));
                break;
            case "log":
                symbolTable.declareFunction("log",
                    new BuiltinFunction("log", 1,
                        args -> new HydraValue(Math.log(args.get(0).asDouble()), "double")));
                break;
            case "sqrt":
                symbolTable.declareFunction("sqrt",
                    new BuiltinFunction("sqrt", 1,
                        args -> new HydraValue(Math.sqrt(args.get(0).asDouble()), "double")));
                break;
            case "pow":
                symbolTable.declareFunction("pow",
                    new BuiltinFunction("pow", 2,
                        args -> new HydraValue(
                            Math.pow(args.get(0).asDouble(), args.get(1).asDouble()),
                            "double")));
                break;
            default:
                throw new RuntimeException("Función desconocida en math: " + lib);
        }
        return null;
    }


    private void loadMathBuiltins() {
        symbolTable.declareFunction("sin",  new BuiltinFunction("sin",  1, args -> new HydraValue(Math.sin(args.get(0).asDouble()), "double")));
        symbolTable.declareFunction("cos",  new BuiltinFunction("cos",  1, args -> new HydraValue(Math.cos(args.get(0).asDouble()), "double")));
        symbolTable.declareFunction("tan",  new BuiltinFunction("tan",  1, args -> new HydraValue(Math.tan(args.get(0).asDouble()), "double")));
        symbolTable.declareFunction("log",  new BuiltinFunction("log",  1, args -> new HydraValue(Math.log(args.get(0).asDouble()), "double")));
        symbolTable.declareFunction("sqrt", new BuiltinFunction("sqrt", 1, args -> new HydraValue(Math.sqrt(args.get(0).asDouble()), "double")));
        symbolTable.declareFunction("pow",  new BuiltinFunction("pow",  2, args -> new HydraValue(Math.pow(args.get(0).asDouble(), args.get(1).asDouble()), "double")));
    }













    
    @Override
    public HydraValue visitMultiplicativeExpr(HydraParser.MultiplicativeExprContext ctx) {
        HydraValue left = visit(ctx.exponentialExpr(0));
        
        for (int i = 1; i < ctx.exponentialExpr().size(); i++) {
            HydraValue right = visit(ctx.exponentialExpr(i));
            String op = ctx.getChild(2 * i - 1).getText();
            
            if (op.equals("*")) {
                if (left.getType().equals("string") || right.getType().equals("string")) {
                    throw new RuntimeException("Error: No se puede multiplicar strings");
                } else if (left.getType().equals("int") && right.getType().equals("int")) {
                    left = new HydraValue(left.asInt() * right.asInt(), "int");
                } else {
                    left = new HydraValue(left.asDouble() * right.asDouble(), "float");
                }
            } else if (op.equals("/")) {
                double rightVal = right.asDouble();
                if (rightVal == 0) {
                    throw new RuntimeException("Error: División por cero");
                }
                
                double resultado = left.asDouble() / rightVal;
                
                if (left.getType().equals("int") && right.getType().equals("int") && 
                    resultado == Math.floor(resultado)) {
                    left = new HydraValue((int)resultado, "int");
                } else {
                    left = new HydraValue(resultado, "float");
                }
                
            } else if (op.equals("%")) {
                if (left.getType().equals("string") || right.getType().equals("string")) {
                    throw new RuntimeException("Error: No se puede aplicar módulo a strings");
                }
                
                if (right.asInt() == 0) {
                    throw new RuntimeException("Error: Módulo por cero");
                }
                
                left = new HydraValue(left.asInt() % right.asInt(), "int");
            }
        }
        
        return left;
    }
    
    @Override
    public HydraValue visitExponentialExpr(HydraParser.ExponentialExprContext ctx) {
        if (ctx.exponentialExpr() != null) {
            HydraValue base = visit(ctx.unaryExpr());
            HydraValue exponent = visit(ctx.exponentialExpr());
            
            double result = Math.pow(base.asDouble(), exponent.asDouble());
            
            if (result == (int) result && base.getType().equals("int") && 
                exponent.getType().equals("int") && exponent.asInt() >= 0) {
                return new HydraValue((int) result, "int");
            } else {
                return new HydraValue(result, "float");
            }
        } else {
            return visit(ctx.unaryExpr());
        }
    }
    
    @Override
    public HydraValue visitUnaryExpr(HydraParser.UnaryExprContext ctx) {
        if (ctx.MINUS() != null) {
            HydraValue value = visit(ctx.unaryExpr());
            if (value.getType().equals("int")) {
                return new HydraValue(-value.asInt(), "int");
            } else {
                return new HydraValue(-value.asDouble(), "float");
            }
        } else if (ctx.NOT() != null) {
            HydraValue value = visit(ctx.unaryExpr());
            return new HydraValue(!value.asBool(), "bool");
        } else {
            return visit(ctx.postfixExpr());
        }
    }
    
    @Override
    public HydraValue visitExpression(HydraParser.ExpressionContext ctx) {
        return visit(ctx.assignmentExpr());
    }
    
        // ================ MÉTODOS AUXILIARES ================
    
    private HydraValue getDefaultValue(String tipo) {
        switch (tipo) {
            case "int": return new HydraValue(0, "int");
            case "float": return new HydraValue(0.0, "float");
            case "string": return new HydraValue("", "string");
            case "bool": return new HydraValue(false, "bool");
            case "char": return new HydraValue('\0', "char");
            default: return new HydraValue(null, tipo);
        }
    } 

    @Override
    public HydraValue visitCatchBlock(HydraParser.CatchBlockContext ctx) {
        // Este método no se llama directamente, 
        // la lógica está en visitTryStatement
        return visit(ctx.bloque());
    }

    @Override
    public HydraValue visitFinallyBlock(HydraParser.FinallyBlockContext ctx) {
        // Este método no se llama directamente,
        // la lógica está en visitTryStatement
        return visit(ctx.bloque());
    }

    @Override
    public HydraValue visitFuncion(HydraParser.FuncionContext ctx) {
        // Obtener modificadores
        boolean isStatic = ctx.STATIC() != null;
        String accessModifier = ctx.accessModifier() != null ? 
                            ctx.accessModifier().getText() : "public";
        
        // Obtener nombre de la función
        String functionName = ctx.IDENTIFIER().getText();
        
        // Obtener parámetros
        List<String> parameterNames = new ArrayList<>();
        List<String> parameterTypes = new ArrayList<>();
        
        if (ctx.paramList() != null) {
            for (HydraParser.ParametroContext param : ctx.paramList().parametro()) {
                String paramType = param.tipo().getText();
                String paramName = param.IDENTIFIER().getText();
                
                parameterTypes.add(paramType);
                parameterNames.add(paramName);
            }
        }
        
        // Obtener tipo de retorno
        String returnType = null;
        if (ctx.tipo() != null) {
            returnType = ctx.tipo().getText();
        }
        
        // Crear función
        HydraFunction function = new HydraFunction(
            functionName, parameterNames, parameterTypes, 
            returnType, ctx.bloque(), isStatic, accessModifier
        );
        
        // Declarar función en la tabla de símbolos
        symbolTable.declareFunction(functionName, function);
        
        return null; // Las declaraciones de función no retornan valor
    }
    
    private HydraValue callFunction(HydraFunction function, List<HydraValue> arguments) {
    // --- 1) Si es una función nativa, se ejecuta directamente ---
    if (function instanceof BuiltinFunction) {
        return ((BuiltinFunction) function).invoke(arguments);
    }

    // --- 2) Si es función de usuario, creamos un nuevo scope ---
    SymbolTable functionScope = new SymbolTable(symbolTable);
    SymbolTable previousScope = symbolTable;

    try {
        // Cambiar al scope de la función
        symbolTable = functionScope;

        // Map para el call stack
        Map<String, HydraValue> parameters = new HashMap<>();

        // Declarar parámetros en el nuevo scope
        List<String> paramNames = function.getParameterNames();
        List<String> paramTypes = function.getParameterTypes();
        for (int i = 0; i < arguments.size(); i++) {
            String paramName = paramNames.get(i);
            String paramType = paramTypes.get(i);
            HydraValue argument = arguments.get(i);

            if (!isFunctionTypeCompatible(argument.getType(), paramType)) {
                throw new RuntimeException(
                  "Tipo incompatible para parámetro " + paramName +
                  ". Esperado: " + paramType + ", Recibido: " + argument.getType()
                );
            }

            symbolTable.declare(paramName, argument);
            parameters.put(paramName, argument);
        }

        // Push al call stack
        callStack.pushFrame(function.getName(), parameters);

        // Ejecutar cuerpo
        try {
            visit(function.getBody());

            // Si no devolvió nada y debía devolver, error
            if (function.hasReturn()) {
                throw new RuntimeException(
                  "Función " + function.getName() +
                  " debe retornar un valor de tipo " + function.getReturnType()
                );
            }

            return new HydraValue(null, "void");
        } catch (ReturnException e) {
            // Capturar return
            HydraValue returnValue = e.getReturnValue();
            if (function.hasReturn() &&
                !isFunctionTypeCompatible(returnValue.getType(), function.getReturnType())) {
                throw new RuntimeException(
                  "Tipo de retorno incompatible. Esperado: " +
                  function.getReturnType() + ", Recibido: " + returnValue.getType()
                );
            }
            return returnValue;
        }
    } finally {
        // Pop del call stack y restaurar scope
        if (!callStack.isEmpty()) {
            callStack.popFrame();
        }
        symbolTable = previousScope;
    }
}














    @Override
    public HydraValue visitDefinicionClase(HydraParser.DefinicionClaseContext ctx) {
        // Obtener modificadores
        String accessModifier = ctx.accessModifier() != null ? 
                            ctx.accessModifier().getText() : "public";
        
        // Obtener nombre de la clase
        String className = ctx.IDENTIFIER(0).getText();
        
        System.out.println("DEBUG: Procesando definición de clase: " + className);
        
        // Crear clase
        HydraClass hydraClass = new HydraClass(className, accessModifier);
        
        // Procesar herencia
        if (ctx.EXTENDS() != null) {
            String parentClassName = ctx.IDENTIFIER(1).getText();
            hydraClass.setParentClass(parentClassName);
            System.out.println("DEBUG: Clase " + className + " extiende de " + parentClassName);
        }
        
        // Procesar interfaces
        if (ctx.IMPLEMENTS() != null) {
            for (int i = 2; i < ctx.IDENTIFIER().size(); i++) {
                hydraClass.addInterface(ctx.IDENTIFIER(i).getText());
                System.out.println("DEBUG: Clase " + className + " implementa " + ctx.IDENTIFIER(i).getText());
            }
        }
        
        // Procesar miembros de la clase
        if (ctx.miembro() != null) {
            for (HydraParser.MiembroContext miembro : ctx.miembro()) {
                String memberAccess = miembro.accessModifier() != null ? 
                                    miembro.accessModifier().getText() : "public";
                boolean isStatic = miembro.STATIC() != null;
                
                if (miembro.declaracion() != null) {
                    // Atributo de clase
                    processClassAttribute(miembro.declaracion(), hydraClass, memberAccess, isStatic);
                } else if (miembro.funcion() != null) {
                    // Método de clase
                    processClassMethod(miembro.funcion(), hydraClass, memberAccess, isStatic);
                } else if (miembro.constructor() != null) {
                    // Constructor
                    processConstructor(miembro.constructor(), hydraClass, memberAccess);
                }
            }
        }
        
        // Declarar clase en symbol table
        symbolTable.declareClass(className, hydraClass);
        
        System.out.println("DEBUG: Clase " + className + " declarada exitosamente");
        
        return null;
    }

    private void processClassAttribute(HydraParser.DeclaracionContext ctx, 
                                    HydraClass hydraClass, String access, boolean isStatic) {
        String tipo = ctx.tipo().getText();
        
        for (HydraParser.DeclaratorContext declarator : ctx.declaratorList().declarator()) {
            String attrName = declarator.IDENTIFIER() != null ? 
                            declarator.IDENTIFIER().getText() : 
                            declarator.CONSTID().getText();
            
            HydraValue value;
            if (declarator.expression() != null) {
                value = visit(declarator.expression());
            } else {
                value = getDefaultValue(tipo);
            }
            
            hydraClass.addAttribute(attrName, value, access);
            System.out.println("DEBUG: Atributo agregado: " + attrName + " (" + tipo + ")");
        }
    }

    private void processClassMethod(HydraParser.FuncionContext ctx, 
                                HydraClass hydraClass, String access, boolean isStatic) {
        String methodName = ctx.IDENTIFIER().getText();
        
        // Crear función usando el método existente
        HydraFunction method = createFunctionFromContext(ctx, access, isStatic);
        
        // Agregar a la clase
        hydraClass.addMethod(methodName, method);
        System.out.println("DEBUG: Método agregado: " + methodName);
    }

    private void processConstructor(HydraParser.ConstructorContext ctx, 
                                HydraClass hydraClass, String access) {
        String constructorName = ctx.IDENTIFIER().getText();
        
        // Verificar que el nombre del constructor coincida con la clase
        if (!constructorName.equals(hydraClass.getName())) {
            throw new RuntimeException("El constructor debe tener el mismo nombre que la clase");
        }
        
        // Crear función constructor
        HydraFunction constructor = createConstructorFromContext(ctx, access);
        
        // Asignar constructor a la clase
        hydraClass.setConstructor(constructor);
        System.out.println("DEBUG: Constructor agregado para clase: " + hydraClass.getName());
    }

    private HydraFunction createFunctionFromContext(HydraParser.FuncionContext ctx, 
                                                String access, boolean isStatic) {
        String functionName = ctx.IDENTIFIER().getText();
        
        // Obtener parámetros
        List<String> parameterNames = new ArrayList<>();
        List<String> parameterTypes = new ArrayList<>();
        
        if (ctx.paramList() != null) {
            for (HydraParser.ParametroContext param : ctx.paramList().parametro()) {
                String paramType = param.tipo().getText();
                String paramName = param.IDENTIFIER().getText();
                
                parameterTypes.add(paramType);
                parameterNames.add(paramName);
            }
        }
        
        // Obtener tipo de retorno
        String returnType = null;
        if (ctx.tipo() != null) {
            returnType = ctx.tipo().getText();
        }
        
        // Crear función
        return new HydraFunction(
            functionName, parameterNames, parameterTypes, 
            returnType, ctx.bloque(), isStatic, access
        );
    }

    private HydraFunction createConstructorFromContext(HydraParser.ConstructorContext ctx, 
                                                    String access) {
        String constructorName = ctx.IDENTIFIER().getText();
        
        // Obtener parámetros
        List<String> parameterNames = new ArrayList<>();
        List<String> parameterTypes = new ArrayList<>();
        
        if (ctx.paramList() != null) {
            for (HydraParser.ParametroContext param : ctx.paramList().parametro()) {
                String paramType = param.tipo().getText();
                String paramName = param.IDENTIFIER().getText();
                
                parameterTypes.add(paramType);
                parameterNames.add(paramName);
            }
        }
        
        // Los constructores no tienen tipo de retorno explícito
        return new HydraFunction(
            constructorName, parameterNames, parameterTypes, 
            null, ctx.bloque(), false, access
        );
    }

    // ✅ ÚNICA FUNCIÓN isFunctionTypeCompatible
    private boolean isFunctionTypeCompatible(String actualType, String expectedType) {
        // Coincidencia exacta
        if (actualType.equals(expectedType)) {
            return true;
        }
        
        // Compatibilidad de tipos específicos
        switch (expectedType) {
            case "int":
                return "int".equals(actualType) || "number".equals(actualType);
                
            case "float":
            case "double":
                return "float".equals(actualType) || 
                       "double".equals(actualType) || 
                       "int".equals(actualType) || 
                       "number".equals(actualType);
                
            case "string":
                // Cualquier tipo puede convertirse a string
                return true;
                
            case "bool":
                return "bool".equals(actualType);
                
            case "char":
                return "char".equals(actualType);
                
            default:
                // Para tipos de objeto o tipos personalizados
                return actualType.equals(expectedType);
        }
    }


    

    // ✅ ÚNICA FUNCIÓN callConstructor
    private HydraValue callConstructor(HydraFunction constructor, HydraInstance instance, 
                                     List<HydraValue> arguments) {
        //System.out.println("DEBUG: Ejecutando constructor con " + arguments.size() + " argumentos");
        
        // Crear nuevo scope para el constructor
        SymbolTable constructorScope = new SymbolTable(symbolTable);
        SymbolTable previousScope = symbolTable;
        
        try {
            // Cambiar al scope del constructor
            symbolTable = constructorScope;
            
            // Agregar 'self' al scope (referencia a la instancia actual)
            symbolTable.declare("self", new HydraValue(instance));
            
            // Agregar todos los atributos de la instancia al scope del constructor
            Map<String, HydraValue> classAttributes = instance.getClassDefinition().getAttributes();
            for (Map.Entry<String, HydraValue> attr : classAttributes.entrySet()) {
                String attrName = attr.getKey();
                HydraValue attrValue = instance.getAttribute(attrName);
                
                // Declarar el atributo en el scope del constructor para que sea accesible
                symbolTable.declare(attrName, attrValue);
                System.out.println("DEBUG: Atributo '" + attrName + "' disponible en constructor con valor: " + attrValue.asString());
            }
            
            // Asignar parámetros del constructor
            List<String> paramNames = constructor.getParameterNames();
            List<String> paramTypes = constructor.getParameterTypes();
            
            for (int i = 0; i < arguments.size(); i++) {
                String paramName = paramNames.get(i);
                String paramType = paramTypes.get(i);
                HydraValue argument = arguments.get(i);
                
                // Verificar compatibilidad de tipos básica
                if (!isFunctionTypeCompatible(argument.getType(), paramType)) {
                    throw new RuntimeException("Tipo incompatible para parámetro " + paramName + 
                                            " del constructor. Esperado: " + paramType + 
                                            ", Recibido: " + argument.getType());
                }
                
                // Declarar parámetro en el scope del constructor
                symbolTable.declare(paramName, argument);
                System.out.println("DEBUG: Parámetro '" + paramName + "' declarado con valor: " + argument.asString());
            }
            
            // Ejecutar el cuerpo del constructor
            try {
                visit(constructor.getBody());
            } catch (ReturnException e) {
                // Los constructores no deberían tener return, pero si lo tienen, 
                // simplemente continuar (el constructor ya inicializó la instancia)
                System.out.println("ADVERTENCIA: Constructor con return statement detectado");
            }
            
            // Sincronizar cambios de atributos de vuelta a la instancia
            for (String attrName : classAttributes.keySet()) {
                try {
                    HydraValue updatedValue = symbolTable.getValue(attrName);
                    instance.setAttribute(attrName, updatedValue);
                    System.out.println("DEBUG: Atributo '" + attrName + "' actualizado en instancia a: " + updatedValue.asString());
                } catch (RuntimeException e) {
                    // Si el atributo no fue modificado en el constructor, mantener valor original
                    System.out.println("DEBUG: Atributo '" + attrName + "' no fue modificado en constructor");
                }
            }
            
            System.out.println("DEBUG: Constructor ejecutado exitosamente");
            
            return new HydraValue(instance);
            
        } finally {
            // Restaurar scope anterior
            symbolTable = previousScope;
        }
    }


} 