public class ActingHeadRecord implements Comparable<ActingHeadRecord> {
    private Day startDay;
    private Day endDay;
    private Employee actingHead;
    private Team team;

    public ActingHeadRecord(Day sDay, Day eDay, Employee aHead, Team t) {
        startDay = sDay;
        endDay = eDay;
        actingHead = aHead;
        team = t;
    }

    @Override
    public int compareTo(ActingHeadRecord another) {
        return this.startDay.compareTo(another.startDay);
    }

    public Day getStartDay() {
        return startDay;
    }

    public Day getEndDay() {
        return endDay;
    }

    public Team getTeam() {
        return team;
    }

    public Employee getActingHead() {
        return actingHead;
    }

    public void printRecord() {
        System.out.println(startDay.toString() + " to " + endDay.toString() + ": " + actingHead.getName());
    }
    
}
