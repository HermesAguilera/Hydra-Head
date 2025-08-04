import java.util.List;

public class HydraFunction {
    private String name;
    private List<String> parameterNames;
    private List<String> parameterTypes;
    private String returnType;
    private HydraParser.BloqueContext body;
    private boolean isStatic;
    private String accessModifier;
    
    public HydraFunction(String name, List<String> parameterNames, List<String> parameterTypes, 
                        String returnType, HydraParser.BloqueContext body, 
                        boolean isStatic, String accessModifier) {
        this.name = name;
        this.parameterNames = parameterNames;
        this.parameterTypes = parameterTypes;
        this.returnType = returnType != null ? returnType : "void";
        this.body = body;
        this.isStatic = isStatic;
        this.accessModifier = accessModifier != null ? accessModifier : "public";
    }
    
    // Getters
    public String getName() { return name; }
    public List<String> getParameterNames() { return parameterNames; }
    public List<String> getParameterTypes() { return parameterTypes; }
    public String getReturnType() { return returnType; }
    public HydraParser.BloqueContext getBody() { return body; }
    public boolean isStatic() { return isStatic; }
    public String getAccessModifier() { return accessModifier; }
    
    public int getParameterCount() {
        return parameterNames != null ? parameterNames.size() : 0;
    }
    
    public boolean hasReturn() {
        return !"void".equals(returnType);
    }

    public HydraValue invoke(List<HydraValue> args) {
        throw new UnsupportedOperationException(
            "invoke() no soportado en funciones de usuario: " + name
        );
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(accessModifier).append(" ");
        if (isStatic) sb.append("static ");
        sb.append("ft ").append(name).append("(");
        
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(parameterTypes.get(i)).append(" ").append(parameterNames.get(i));
            }
        }
        
        sb.append(")");
        if (hasReturn()) {
            sb.append(" -> ").append(returnType);
        }
        
        return sb.toString();
    }


}