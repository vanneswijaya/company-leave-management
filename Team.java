import java.util.ArrayList;
import java.util.Collections; //Provides sorting

public class Team implements Comparable<Team> {
    /* Very simple - please follow lab sheet */
    private String teamName;
    private Employee head;
    private Day dateSetup;
    private ArrayList<Employee> allTeamMembers;
    private ArrayList<ActingHeadRecord> actingHeadRecords;

    public Team(String n, Employee hd) {
        teamName = n;
        head = hd;
        dateSetup = SystemDate.getInstance().clone();
        allTeamMembers = new ArrayList<Employee>();
        allTeamMembers.add(hd);
        hd.addTeam(this);
        actingHeadRecords = new ArrayList<ActingHeadRecord>();
    }

    public String getName() {
        return teamName;
    }

    public Employee getHead() {
        return head;
    }

    public ArrayList<ActingHeadRecord> getActingHeadRecords() {
        return actingHeadRecords;
    }

    public void addMember(Employee e) {
        allTeamMembers.add(e);
        Collections.sort(allTeamMembers);
    }

    public void removeMember(Employee e) {
        allTeamMembers.remove(e);
    }

    public Employee findMember(String name) {
        for (int i = 0; i < allTeamMembers.size(); i++) {
            if (allTeamMembers.get(i).getName().equals(name)) {
                return allTeamMembers.get(i);
            }
        }
        return null;
    } 

    public void listMembers() {
        for (Employee employee : allTeamMembers) {
            if (employee == head) {
                System.out.println(employee.getName() + " (Head of Team)");
            } else {
                System.out.println(employee.getName());
            }
        }
    }

    public void listActingHeads() {
        if (!actingHeadRecords.isEmpty()) {
            System.out.println("Acting heads:");
            for (ActingHeadRecord ahr : actingHeadRecords) {
                ahr.printRecord();
            }
        }
    }

    public void addActingHeadRecord(ActingHeadRecord ahr) {
        actingHeadRecords.add(ahr);
        Collections.sort(actingHeadRecords);
    }

    public void removeActingHeadRecord(ActingHeadRecord ahr) {
        actingHeadRecords.remove(ahr);
    }

    @Override
    public int compareTo(Team another) {
        return this.teamName.compareTo(another.teamName);
    }

    public static void list(ArrayList<Team> list) {
        //Learn: "-" means left-aligned
        System.out.printf("%-30s%-10s%-13s\n", "Team Name", "Leader", "Setup Date");
        for (Team t : list)
            System.out.printf("%-30s%-10s%-13s\n", t.teamName, t.head.getName(), t.dateSetup); //display t.teamName, etc..
    }
}