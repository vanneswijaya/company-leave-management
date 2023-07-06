public class ExMissingActingHead extends Exception {
    public ExMissingActingHead(Team team) { super("Missing input: Please give the name of the acting head for " + team.getName()); }
}
