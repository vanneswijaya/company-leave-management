public class ExActingHeadOnLeave extends Exception {
    public ExActingHeadOnLeave(String name, LeaveRecord leave) { super(name + " is on leave during " + leave.getPrintRecord() + "!"); }
}
