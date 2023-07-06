import java.util.ArrayList;
import java.util.Collections; //Provides sorting

public class Employee implements Comparable<Employee> {
    private String name;
    private int annualLeaves;
    private int sickLeaves;
    private int blockLeavesDone;
    private ArrayList<Team> allTeams;
    private ArrayList<LeaveRecord> leaveRecords;

    public Employee(String n, int yle) {
        name = n;
        annualLeaves = yle;
        allTeams = new ArrayList<Team>();
        leaveRecords = new ArrayList<LeaveRecord>();
        sickLeaves = 135;
        blockLeavesDone = 0;
    }

    @Override
    public int compareTo(Employee another) {
        return this.name.compareTo(another.name);
    }

    public String getName() { return name; }

    public int getAnnualLeaves() { return annualLeaves; }

    public ArrayList<LeaveRecord> getLeaveRecords() { return leaveRecords; }

    public void addTeam(Team t) {
        allTeams.add(t);
        Collections.sort(allTeams);
    }

    public void removeTeam(Team t) {
        allTeams.remove(t);
    }

    public void listRoles() {
        if (allTeams.isEmpty()) {
            System.out.println("No role");
        } else {
            for (Team team : allTeams) {
                if (team.getHead() == this) {
                    System.out.println(team.getName() + " (Head of Team)");
                } else {
                    System.out.println(team.getName());
                }
            }
        }
    }

    public boolean isHead() {
        for (Team team : allTeams) {
            if (team.getHead() == this) {
                return true;
            } 
        }
        return false;
    }

    public void addLeave(LeaveRecord leaveRecord) {
        leaveRecords.add(leaveRecord);
        Collections.sort(leaveRecords);
        if (leaveRecord.getLeaveType() == "AL") {
            annualLeaves -= leaveRecord.getDurationInDays();
        } else if (leaveRecord.getLeaveType() == "SL") {
            sickLeaves -= leaveRecord.getDurationInDays();
        } else if (leaveRecord.getLeaveType() == "BL") {
            blockLeavesDone += 1;
            annualLeaves -= leaveRecord.getDurationInDays(); 
        }
    }

    public void removeLeave(LeaveRecord leaveRecord) {
        leaveRecords.remove(leaveRecord);
        if (leaveRecord.getLeaveType() == "AL") {
            annualLeaves += leaveRecord.getDurationInDays();
        } else if (leaveRecord.getLeaveType() == "SL") {
            sickLeaves += leaveRecord.getDurationInDays();
        } else if (leaveRecord.getLeaveType() == "BL") {
            blockLeavesDone -= 1;
            annualLeaves += leaveRecord.getDurationInDays(); 
        }
    }

    public void listLeaves() {
        if (leaveRecords.isEmpty()) {
            System.out.println("No leave record");
        } else {
            for (LeaveRecord leaveRecord : leaveRecords) {
                leaveRecord.printRecord();
            }
        }
    }

    public void checkActingHeadInput(String[] cmdParts) throws ExMissingActingHead {
        ArrayList<Team> headTeams = new ArrayList<Team>();
        for (Team team : allTeams) {
            if (team.getHead() == this) {
                headTeams.add(team);
            }
        }
        for (int i = 5; i < cmdParts.length; i += 2) {
            Team found = null;
            for (Team team : headTeams) {
                if (cmdParts[i].equals(team.getName())) {
                    found = team;
                    break;
                }
            }
            headTeams.remove(found);
        }
        if (!headTeams.isEmpty()) {
            throw new ExMissingActingHead(headTeams.get(0));
        }
    }

    public void checkActingHeadExists(String[] cmdParts) throws ExActingHeadNotFound {
        for (int i = 5; i < cmdParts.length; i += 2) {
            for (Team team : allTeams) {
                if (team.getName().equals(cmdParts[i])) {
                    Employee found = team.findMember(cmdParts[i+1]);
                    if (found == null) {
                        throw new ExActingHeadNotFound(cmdParts[i+1], team);
                    }
                    break;
                }
            }
         }
    }

    public void checkActingHeadOnLeave(LeaveRecord inp_lr, String[] cmdParts) throws ExActingHeadOnLeave {
        Day startA = inp_lr.getStartDay();
        Day endA = inp_lr.getEndDay();
        for (int i = 5; i < cmdParts.length; i += 2) {
            for (Team team : allTeams) {
                if (team.getName().equals(cmdParts[i])) {
                    Employee found = team.findMember(cmdParts[i+1]);
                    for (LeaveRecord lr : found.getLeaveRecords()) {
                        Day startB = lr.getStartDay();
                        Day endB = lr.getEndDay();
                        if (startA.compareTo(endB) <= 0 && endA.compareTo(startB) >= 0) {
                            throw new ExActingHeadOnLeave(cmdParts[i+1], lr);
                        }
                    }
                    break;
                }
            }
         }
    }

    public void checkValidAL(LeaveRecord inp_lr) throws ExInvalidAL {
        if (inp_lr.getLeaveType().equals("AL") && blockLeavesDone == 0 && annualLeaves - inp_lr.getDurationInDays() < 7) {
            throw new ExInvalidAL(annualLeaves);
        }
    }

    public void checkALBL(LeaveRecord inp_lr) throws ExALType, ExBLType {
        if (inp_lr.getLeaveType().equals("AL") && inp_lr.getDurationInDays() >= 7) {
            throw new ExBLType();
        } else if (inp_lr.getLeaveType().equals("BL") && inp_lr.getDurationInDays() <= 6) {
            throw new ExALType();
        }
    }

    public void checkBalance(LeaveRecord inp_lr) throws ExInsufficientAL, ExInsufficientSL {
        if ((inp_lr.getLeaveType().equals("AL") || inp_lr.getLeaveType().equals("BL")) && inp_lr.getDurationInDays() > annualLeaves) {
            throw new ExInsufficientAL(annualLeaves);
        } else if (inp_lr.getLeaveType().equals("SL") && inp_lr.getDurationInDays() > sickLeaves) {
            throw new ExInsufficientSL(sickLeaves);
        }
    }

    public void checkOverlappingLeaves(LeaveRecord inp_lr) throws ExLeaveOverlapped {
        Day startA = inp_lr.getStartDay();
        Day endA = inp_lr.getEndDay();
        if (!leaveRecords.isEmpty()) {
            for (LeaveRecord lr : leaveRecords) {
                Day startB = lr.getStartDay();
                Day endB = lr.getEndDay();
                if (startA.compareTo(endB) <= 0 && endA.compareTo(startB) >= 0) {
                    throw new ExLeaveOverlapped(startB, endB, lr.getLeaveType());
                }
            }
        }
    }

    public void checkValidStartLeave(LeaveRecord inp_lr) throws ExInvalidStartLeave {
        Day systemDate = SystemDate.getInstance();
        if (inp_lr.getStartDay().compareTo(systemDate) < 0) {
            throw new ExInvalidStartLeave(systemDate);
        }
    }

    public static Employee searchEmployee(ArrayList<Employee> list, String nameToSearch) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(nameToSearch)) {
                return list.get(i);
            }
        }
        return null;
    }
}
