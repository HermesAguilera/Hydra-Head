import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre del archivo (.hy): ");
        String inputFileName = scanner.nextLine();
        // ✅ NO CERRAR EL SCANNER AQUÍ
        // scanner.close(); // ❌ COMENTADO

        CharStream input = CharStreams.fromFileName(inputFileName);
        HydraLexer lexer = new HydraLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        try (PrintWriter tokenWriter = new PrintWriter("lexer_tokens.txt")) {
            tokens.fill();
            for (Token token : tokens.getTokens()) {
                String typeName = HydraLexer.VOCABULARY.getSymbolicName(token.getType());
                tokenWriter.printf("Línea %d: <%s> '%s'%n", token.getLine(), typeName, token.getText());
            }
        }

        HydraParser parser = new HydraParser(tokens);
        PrintWriter errorWriter = new PrintWriter(new FileWriter("travel_log.txt", true));
        parser.removeErrorListeners();
        parser.addErrorListener(new ErrorListener(errorWriter));

        ParseTree tree = parser.programa();

        try (PrintWriter treeWriter = new PrintWriter("parser_tree.txt")) {
            printTree(tree, parser, treeWriter, 0);
        }

        if (parser.getNumberOfSyntaxErrors() == 0) {
            try {
                HydraExecutor executor = new HydraExecutor();
                executor.visit(tree);
                
                // ✅ CERRAR SCANNER DEL EXECUTOR AL FINAL
                executor.closeScanner();
                
            } catch (Exception e) {
                System.err.println("Error de ejecución: " + e.getMessage());
                e.printStackTrace(); // ✅ AGREGAR STACK TRACE PARA DEBUG
            }
        }

        errorWriter.close();
        
        // ✅ CERRAR SCANNER PRINCIPAL AL FINAL
        scanner.close();
    }

    public static void printTree(ParseTree tree, Parser parser, PrintWriter writer, int indent) {
        String indentStr = "  ".repeat(indent);
        String nodeText = Trees.getNodeText(tree, parser);
        writer.println(indentStr + nodeText);
        for (int i = 0; i < tree.getChildCount(); i++) {
            printTree(tree.getChild(i), parser, writer, indent + 1);
        }
    }

    public static class ErrorListener extends BaseErrorListener {
        private final PrintWriter writer;

        public ErrorListener(PrintWriter writer) {
            this.writer = writer;
        }

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol,
                                int line, int charPositionInLine, String msg, RecognitionException e) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.printf("[%s] Error de sintaxis en línea %d:%d - %s%n", timestamp, line, charPositionInLine, msg);
        }
    }
}