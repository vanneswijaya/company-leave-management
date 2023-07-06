public class ExBLType extends Exception {
    public ExBLType() { super("To apply annual leave for 7 days or more, please use the Block Leave (BL) type."); }
    public ExBLType(String message) { super(message); }
}
