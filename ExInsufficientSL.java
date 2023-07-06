public class ExInsufficientSL extends Exception {
    public ExInsufficientSL(int sickLeaves) { super("Insufficient balance of sick leaves. " + sickLeaves + " days left only!"); }
}
