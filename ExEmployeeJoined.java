public class ExEmployeeJoined extends Exception {
    public ExEmployeeJoined() { super("The employee has joined the team already!"); }
    public ExEmployeeJoined(String message) { super(message); }
}
