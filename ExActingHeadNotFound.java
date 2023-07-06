public class ExActingHeadNotFound extends Exception {
    public ExActingHeadNotFound(String name, Team team) { super("Employee (" + name + ") not found for " + team.getName() + "!"); }
}
