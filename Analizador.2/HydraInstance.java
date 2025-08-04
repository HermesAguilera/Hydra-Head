import java.util.HashMap;
import java.util.Map;

public class HydraInstance {
    private HydraClass classDefinition;
    private Map<String, HydraValue> instanceAttributes;
    
    public HydraInstance(HydraClass classDefinition) {
        this.classDefinition = classDefinition;
        this.instanceAttributes = new HashMap<>();
        
        // Inicializar atributos con valores por defecto de la clase
        for (Map.Entry<String, HydraValue> attr : classDefinition.getAttributes().entrySet()) {
            // Crear copia del valor por defecto
            HydraValue defaultValue = attr.getValue();
            HydraValue instanceValue = new HydraValue(defaultValue.getValue(), defaultValue.getType());
            instanceAttributes.put(attr.getKey(), instanceValue);
        }
    }
    
    public HydraValue getAttribute(String name) {
        if (instanceAttributes.containsKey(name)) {
            return instanceAttributes.get(name);
        }
        throw new RuntimeException("Atributo no encontrado: " + name + " en clase " + classDefinition.getName());
    }
    
    public void setAttribute(String name, HydraValue value) {
        if (instanceAttributes.containsKey(name)) {
            instanceAttributes.put(name, value);
        } else {
            throw new RuntimeException("Atributo no encontrado: " + name + " en clase " + classDefinition.getName());
        }
    }
    
    public boolean hasAttribute(String name) {
        return instanceAttributes.containsKey(name);
    }
    
    public HydraFunction getMethod(String name) {
        HydraFunction method = classDefinition.getMethod(name);
        if (method == null) {
            throw new RuntimeException("Método no encontrado: " + name + " en clase " + classDefinition.getName());
        }
        return method;
    }
    
    public boolean hasMethod(String name) {
        return classDefinition.hasMethod(name);
    }
    
    public String getClassName() {
        return classDefinition.getName();
    }
    
    public HydraClass getClassDefinition() {
        return classDefinition;
    }
    
    @Override
    public String toString() {
        return classDefinition.getName() + "@" + Integer.toHexString(this.hashCode());
    }
}