import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class HydraClass {
    private String name;
    private String accessModifier;
    private Map<String, HydraValue> attributes;
    private Map<String, HydraFunction> methods;
    private HydraFunction constructor;
    private String parentClass;
    private List<String> interfaces;
    
    public HydraClass(String name, String accessModifier) {
        this.name = name;
        this.accessModifier = accessModifier;
        this.attributes = new HashMap<>();
        this.methods = new HashMap<>();
        this.constructor = null;
        this.parentClass = null;
        this.interfaces = new ArrayList<>();
    }
    
    // Métodos para manejar atributos
    public void addAttribute(String name, HydraValue value, String access) {
        attributes.put(name, value);
    }
    
    public HydraValue getAttribute(String name) {
        return attributes.get(name);
    }
    
    public boolean hasAttribute(String name) {
        return attributes.containsKey(name);
    }
    
    // Métodos para manejar métodos
    public void addMethod(String name, HydraFunction method) {
        methods.put(name, method);
    }
    
    public HydraFunction getMethod(String name) {
        return methods.get(name);
    }
    
    public boolean hasMethod(String name) {
        return methods.containsKey(name);
    }
    
    // Constructor
    public void setConstructor(HydraFunction constructor) {
        this.constructor = constructor;
    }
    
    public HydraFunction getConstructor() {
        return constructor;
    }
    
    // Herencia e interfaces
    public void setParentClass(String parentClass) {
        this.parentClass = parentClass;
    }
    
    public String getParentClass() {
        return parentClass;
    }
    
    public void addInterface(String interfaceName) {
        this.interfaces.add(interfaceName);
    }
    
    public List<String> getInterfaces() {
        return interfaces;
    }
    
    // Crear instancia
    public HydraInstance createInstance() {
        return new HydraInstance(this);
    }
    
    // Getters
    public String getName() { return name; }
    public String getAccessModifier() { return accessModifier; }
    public Map<String, HydraValue> getAttributes() { return attributes; }
    public Map<String, HydraFunction> getMethods() { return methods; }
}