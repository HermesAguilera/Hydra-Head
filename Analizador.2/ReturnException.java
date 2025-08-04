public class ReturnException extends RuntimeException {
    private HydraValue returnValue;
    
    public ReturnException(HydraValue returnValue) {
        super("Return statement executed");
        this.returnValue = returnValue;
    }
    
    public HydraValue getReturnValue() {
        return returnValue;
    }
}