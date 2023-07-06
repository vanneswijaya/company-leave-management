public class ExInvalidStartLeave extends Exception {
    public ExInvalidStartLeave(Day systemDate) { super("Wrong Date. Leave start date must not be earlier than the system date (" + systemDate.toString() + ")!"); }
}
