import java.util.Stack;
import java.util.HashMap;
import java.util.Map;

public class FunctionCallStack {
    private Stack<StackFrame> callStack;
    
    public FunctionCallStack() {
        this.callStack = new Stack<>();
    }
    
    public void pushFrame(String functionName, Map<String, HydraValue> parameters) {
        StackFrame frame = new StackFrame(functionName, parameters);
        callStack.push(frame);
    }
    
    public StackFrame popFrame() {
        if (callStack.isEmpty()) {
            throw new RuntimeException("Call stack está vacío");
        }
        return callStack.pop();
    }
    
    public StackFrame getCurrentFrame() {
        if (callStack.isEmpty()) {
            return null;
        }
        return callStack.peek();
    }
    
    public boolean isEmpty() {
        return callStack.isEmpty();
    }
    
    public int size() {
        return callStack.size();
    }
    
    // Clase interna para representar un frame del stack
    public static class StackFrame {
        private String functionName;
        private Map<String, HydraValue> parameters;
        private HydraValue returnValue;
        
        public StackFrame(String functionName, Map<String, HydraValue> parameters) {
            this.functionName = functionName;
            this.parameters = parameters != null ? parameters : new HashMap<>();
            this.returnValue = null;
        }
        
        public String getFunctionName() { return functionName; }
        public Map<String, HydraValue> getParameters() { return parameters; }
        public HydraValue getReturnValue() { return returnValue; }
        public void setReturnValue(HydraValue returnValue) { this.returnValue = returnValue; }
    }
}