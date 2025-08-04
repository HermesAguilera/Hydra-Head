import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class SymbolTable {
    private Map<String, HydraValue> symbols;
    private SymbolTable parent; // Referencia al scope padre
    private Map<String, HydraFunction> functions;
    private Map<String, HydraClass> classes;
    
    public SymbolTable() {
    this.symbols = new HashMap<>();
    this.functions = new HashMap<>();
    this.classes = new HashMap<>();  
    this.parent = null;
    }

    public Set<String> getAllVariables() {
    return symbols.keySet();
}
    
    // CONSTRUCTOR CON PARENT
    public SymbolTable(SymbolTable parent) {
    this.symbols = new HashMap<>();
    this.functions = new HashMap<>();
    this.classes = new HashMap<>();  
    this.parent = parent;
    }
    
    /**
     * Declara una nueva variable en la tabla de símbolos
     * @param name Nombre de la variable
     * @param value Valor inicial de la variable
     */
    public void declare(String name, HydraValue value) {
        if (symbols.containsKey(name)) {
            throw new RuntimeException("Variable ya declarada: " + name);
        }
        symbols.put(name, value);
    }
    
    /**
     * Obtiene el valor de una variable (busca en scope actual y padres)
     * @param name Nombre de la variable
     * @return Valor de la variable
     * @throws RuntimeException si la variable no existe
     */
    public HydraValue get(String name) {
        // Buscar primero en scope local
        if (symbols.containsKey(name)) {
            return symbols.get(name);
        }
        // Si no está local, buscar en scope padre
        if (parent != null && parent.exists(name)) {
            return parent.get(name);
        }
        // Si no se encuentra en ningún lado, error
        throw new RuntimeException("Variable no declarada: " + name);
    }
    
    // ✅ NUEVO MÉTODO ALIAS PARA COMPATIBILIDAD
    /**
     * Método alias para get() - usado por compatibilidad con código existente
     * @param name Nombre de la variable
     * @return Valor de la variable
     */
    public HydraValue getValue(String name) {
        return get(name);
    }
    
    /**
     * Actualiza el valor de una variable existente (busca en scope actual y padres)
     * @param name Nombre de la variable
     * @param value Nuevo valor
     * @throws RuntimeException si la variable no existe
     */
    public void set(String name, HydraValue value) {
        // Si existe localmente, actualizar
        if (symbols.containsKey(name)) {
            symbols.put(name, value);
            return;
        }
        // Si tiene padre y existe en el padre, actualizar el padre
        if (parent != null) {
            try {
                parent.set(name, value);
                return;
            } catch (RuntimeException e) {
                // Si falla en el padre, continuar con el error normal
            }
        }
        // Si no existe en ningún lado, error
        throw new RuntimeException("No se puede asignar a variable no declarada: " + name);
    }

    /**
     * Método para forzar la propagación de cambios al scope padre
     */
    public void forceSet(String name, HydraValue value) {
        symbols.put(name, value);
        if (parent != null && parent.existsLocally(name)) {
            parent.forceSet(name, value);
        }
    }
        
    /**
     * Verifica si una variable está declarada (busca en scope actual y padres)
     * @param name Nombre de la variable
     * @return true si la variable existe, false en caso contrario
     */
    public boolean exists(String name) {
        return symbols.containsKey(name) || (parent != null && parent.exists(name));
    }
    
    /**
     * Obtiene el número de variables declaradas SOLO en este scope
     * @return Cantidad de variables en la tabla local
     */
    public int size() {
        return symbols.size();
    }
    
    /**
     * Muestra todas las variables y sus valores (para debugging)
     */
    public void printAll() {
        System.out.println("=== TABLA DE SÍMBOLOS ===");
        System.out.println("SCOPE LOCAL:");
        for (Map.Entry<String, HydraValue> entry : symbols.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue().asString() + 
                             " (tipo: " + entry.getValue().getType() + ")");
        }
        System.out.println("Total local: " + symbols.size());
        
        // Mostrar también el scope padre si existe
        if (parent != null) {
            System.out.println("SCOPE PADRE:");
            parent.printAll();
        }
        System.out.println("========================");
    }
    
    /**
     * Limpia la tabla de símbolos SOLO del scope actual
     */
    public void clear() {
        symbols.clear();
    }
    
    /**
     * Obtiene una copia de todos los nombres de variables del scope actual
     * @return Set con todos los nombres de variables locales
     */
    public java.util.Set<String> getAllVariableNames() {
        return new java.util.HashSet<>(symbols.keySet());
    }
    
    /**
     * Actualiza una variable existente o la declara si no existe
     * SIEMPRE en el scope actual (no busca en padres)
     * @param name Nombre de la variable
     * @param value Valor a asignar
     */
    public void setOrDeclare(String name, HydraValue value) {
        symbols.put(name, value);
    }
    
    /**
     * Obtiene el scope padre
     * @return SymbolTable padre o null si es el scope raíz
     */
    public SymbolTable getParent() {
        return parent;
    }
    
    /**
     * Verifica si una variable existe SOLO en el scope local (no en padres)
     * @param name Nombre de la variable
     * @return true si existe localmente, false si no
     */
    public boolean existsLocally(String name) {
        return symbols.containsKey(name);
    }
    
    // ✅ NUEVO MÉTODO PARA GESTIÓN DE CONSTANTES
    /**
     * Declara una variable con verificación de constantes
     * @param name Nombre de la variable
     * @param value Valor inicial (puede ser constante)
     */
    public void declareWithConstCheck(String name, HydraValue value) {
        if (symbols.containsKey(name)) {
            throw new RuntimeException("Variable ya declarada: " + name);
        }
        symbols.put(name, value);
    }
    
    // ✅ NUEVO MÉTODO PARA ASIGNACIÓN CON VERIFICACIÓN DE CONSTANTES
    /**
     * Actualiza una variable verificando si es constante
     * @param name Nombre de la variable
     * @param value Nuevo valor
     * @throws RuntimeException si intenta modificar una constante
     */
    public void setWithConstCheck(String name, HydraValue value) {
        HydraValue currentValue = get(name); // Esto lanza excepción si no existe
        
        if (currentValue.isConstant()) {
            throw new RuntimeException("No se puede modificar la constante: " + name);
        }
        
        set(name, value);
    }

    
    // Declara una nueva función en la tabla de símbolos
     
    public void declareFunction(String name, HydraFunction function) {
        if (functions.containsKey(name)) {
            throw new RuntimeException("Función ya declarada: " + name);
        }
        functions.put(name, function);
    }

    /**
     * Obtiene una función (busca en scope actual y padres)
     */
    public HydraFunction getFunction(String name) {
        if (functions.containsKey(name)) {
            return functions.get(name);
        }
        if (parent != null) {
            return parent.getFunction(name);
        }
        throw new RuntimeException("Función no declarada: " + name);
    }

   
    // Verifica si una función existe
  
    public boolean functionExists(String name) {
        return functions.containsKey(name) || (parent != null && parent.functionExists(name));
    }

   
    // Obtiene todas las funciones declaradas
   
    public java.util.Set<String> getAllFunctionNames() {
        java.util.Set<String> allNames = new java.util.HashSet<>(functions.keySet());
        if (parent != null) {
            allNames.addAll(parent.getAllFunctionNames());
        }
        return allNames;
    }

    public void declareClass(String name, HydraClass classDefinition) {
        if (classes.containsKey(name)) {
            throw new RuntimeException("Clase ya declarada: " + name);
        }
        classes.put(name, classDefinition);
    }

    public HydraClass getClass(String name) {
        if (classes.containsKey(name)) {
            return classes.get(name);
        }
        if (parent != null) {
            return parent.getClass(name);
        }
        throw new RuntimeException("Clase no declarada: " + name);
    }

    public boolean classExists(String name) {
        return classes.containsKey(name) || (parent != null && parent.classExists(name));
    }

    public java.util.Set<String> getAllClassNames() {
        java.util.Set<String> allNames = new java.util.HashSet<>(classes.keySet());
        if (parent != null) {
            allNames.addAll(parent.getAllClassNames());
        }
        return allNames;
    }
}