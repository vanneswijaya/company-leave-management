public class ExActingHeadLeaves extends Exception {
    public ExActingHeadLeaves(ActingHeadRecord acting) { super("Cannot take leave. " + acting.getActingHead().getName() + " is the acting head of " + acting.getTeam().getName() + " during " + acting.getStartDay().toString() + " to " + acting.getEndDay().toString() + "!"); }
}
