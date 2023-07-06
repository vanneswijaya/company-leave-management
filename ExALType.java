public class ExALType extends Exception {
    public ExALType() { super("To apply annual leave for 6 days or less, you should use the normal Annual Leave (AL) type."); }
    public ExALType(String message) { super(message); }
}
