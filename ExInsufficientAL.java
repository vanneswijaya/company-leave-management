public class ExInsufficientAL extends Exception {
    public ExInsufficientAL(int annualLeaves) { super("Insufficient balance of annual leaves. " + annualLeaves + " days left only!"); }
}
