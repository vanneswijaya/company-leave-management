public class ExLeaveOverlapped extends Exception {
    public ExLeaveOverlapped(Day startDay, Day endDay, String leaveType) { super("Leave overlapped: The leave period " + startDay.toString() + " to " + endDay.toString() + " [" + leaveType + "] is found!"); }
}
