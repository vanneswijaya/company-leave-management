import java.util.ArrayList;
import java.util.Collections; //Provides sorting

public class Employee implements Comparable<Employee> {
    private String name;
    private int annualLeaves;
    private ArrayList<Team> allTeams;

    public Employee(String n, int yle) {
        name = n;
        annualLeaves = yle;
        allTeams = new ArrayList<Team>();
    }

    @Override
    public int compareTo(Employee another) {
        return this.name.compareTo(another.name);
    }

    public String getName() { return name; }

    public int getAnnualLeaves() { return annualLeaves; }

    public void addTeam(Team t) {
        allTeams.add(t);
        Collections.sort(allTeams);
    }

    public void removeTeam(Team t) {
        allTeams.remove(t);
    }

    public void listRoles() {
        if (allTeams.isEmpty()) {
            System.out.println("No role.");
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

    public static Employee searchEmployee(ArrayList<Employee> list, String nameToSearch) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(nameToSearch)) {
                return list.get(i);
            }
        }
        return null;
    }
}
