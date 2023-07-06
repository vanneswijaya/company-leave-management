public abstract class LeaveRecord implements Comparable<LeaveRecord> {
    protected Day startDay;
    protected Day endDay;
    protected int durationInDays;
    protected String leaveType;

    public LeaveRecord(String sDay, String eDay) {
        startDay = new Day(sDay);
        endDay = new Day(eDay);
        durationInDays = Day.getDifferenceInDays(startDay, endDay) + 1;
    }

    @Override
    public int compareTo(LeaveRecord another) {
        return this.startDay.compareTo(another.startDay);
    }

    public Day getStartDay() {
        return startDay;
    }

    public Day getEndDay() {
        return endDay;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void printRecord() {
        System.out.println(startDay.toString() + " to " + endDay.toString() + " [" + leaveType + "]");
    }

    public String getPrintRecord() {
        return startDay.toString() + " to " + endDay.toString() + " [" + leaveType + "]";
    }
}
