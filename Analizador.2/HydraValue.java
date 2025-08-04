public class HydraValue {
    private Object value;
    private String type;
    private Object[] arrayValue;
    private int[] dimensions;
    private boolean isConstant = false;
    private HydraInstance objectInstance;
    
    
    // Constructor básico
    public HydraValue(Object value, String type) {
        this.value = value;
        this.type = type;
        this.arrayValue = null;
        this.dimensions = null;
    }

    public void setConstant(boolean isConstant) {
        this.isConstant = isConstant;
    }
    
    public boolean isConstant() {
        return this.isConstant;
    }
    
    // Constructor con inferencia de tipo
    public HydraValue(Object value) {
        this.value = value;
        this.arrayValue = null;
        this.dimensions = null;
        
        if (value instanceof Integer) {
            this.type = "int";
        } else if (value instanceof Double || value instanceof Float) {
            this.type = "float";
        } else if (value instanceof String) {
            this.type = "string";
        } else if (value instanceof Boolean) {
            this.type = "bool";
        } else if (value instanceof Character) {
            this.type = "char";
        } else {
            this.type = "unknown";
        }
    }
    
    // Constructor para arreglos
    public HydraValue(Object[] array, String type, int[] dims) {
        this.value = null;
        this.arrayValue = array;
        this.type = type;
        this.dimensions = dims;
    }
    
    // Getters básicos
    public Object getValue() { return value; }
    public String getType() { return type; }
    
    // Métodos para arreglos
    public boolean isArray() {
        return arrayValue != null;
    }
    
    public Object[] getArrayValue() {
        return arrayValue;
    }
    
    public int[] getDimensions() {
        return dimensions;
    }
    
    // Conversiones de tipo
    public int asInt() {
        if (value instanceof Integer) return (Integer) value;
        if (value instanceof Double) return ((Double) value).intValue();
        if (value instanceof Float) return ((Float) value).intValue();
        if (value instanceof Character) return (int) ((Character) value);
        if (value instanceof Boolean) return ((Boolean) value) ? 1 : 0;
        if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }


    public void setObjectInstance(HydraInstance instance) {
        this.objectInstance = instance;
    }


    
    
    public double asDouble() {
        if (value instanceof Double) return (Double) value;
        if (value instanceof Float) return ((Float) value).doubleValue();
        if (value instanceof Integer) return ((Integer) value).doubleValue();
        if (value instanceof Character) return (double) ((Character) value);
        if (value instanceof Boolean) return ((Boolean) value) ? 1.0 : 0.0;
        if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }
    
    public String asString() {
        if (isArray()) {
            return arrayToString(arrayValue, 0);
        }
        
        if (value == null) return "null";
        
        // Formatear números sin decimales innecesarios
        if (value instanceof Double) {
            double d = (Double) value;
            if (d == (int) d) {
                return String.valueOf((int) d);
            }
        }
        if (value instanceof Float) {
            float f = (Float) value;
            if (f == (int) f) {
                return String.valueOf((int) f);
            }
        }
        
        return value.toString();
    }
    
    public boolean asBool() {
        if (value instanceof Boolean) return (Boolean) value;
        if (value instanceof Integer) return (Integer) value != 0;
        if (value instanceof Double) return (Double) value != 0.0;
        if (value instanceof Character) return (Character) value != '\0';
        if (value instanceof String) return !((String) value).isEmpty();
        return false;
    }

            
    // Método auxiliar para convertir arreglo a string
    private String arrayToString(Object[] arr, int depth) {
        if (arr == null) return "null";
        
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) sb.append(", ");
            
            if (arr[i] instanceof Object[]) {
                sb.append(arrayToString((Object[]) arr[i], depth + 1));
            } else if (arr[i] instanceof HydraValue) {
                sb.append(((HydraValue) arr[i]).asString());
            } else {
                sb.append(arr[i]);
            }
        }
        
        sb.append("]");
        return sb.toString();
    }

    public HydraValue(HydraInstance instance) {
        this.value = instance;
        this.objectInstance = instance;
        this.type = instance.getClassName();
        this.arrayValue = null;
        this.dimensions = null;
        this.isConstant = false;
    }

    // =================== NUEVOS MÉTODOS PARA OBJETOS ===================
    public boolean isObject() {
        return objectInstance != null;
    }

    public HydraInstance getObjectInstance() {
        return objectInstance;
    }

    // =================== ACTUALIZAR asString() PARA OBJETOS ===================
    @Override
    public String toString() {
        // Soporte para objetos
        if (isObject()) {
            return objectInstance.toString();
        }
        
        // Comportamiento original para otros tipos
        return asString();
    }
}