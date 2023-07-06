import java.util.ArrayList;
import java.util.Collections; //Provides sorting

public class Company {
    private ArrayList<Employee> allEmployees;
    private ArrayList<Team> allTeams;

    private static Company instance = new Company();

    private Company() {
        allEmployees = new ArrayList<Employee>();
        allTeams = new ArrayList<Team>();
    }

    public static Company getInstance() {
        return instance;
    }

    public Employee findEmployee(String name) {
        for (int i = 0; i < allEmployees.size(); i++) {
            if (allEmployees.get(i).getName().equals(name)) {
                return allEmployees.get(i);
            }
        }
        return null;
    } 

    public Team findTeam(String name) {
        for (int i = 0; i < allTeams.size(); i++) {
            if (allTeams.get(i).getName().equals(name)) {
                return allTeams.get(i);
            }
        }
        return null;
    } 

    public void listTeams() {
        Team.list(allTeams);
    }

    public void listEmployees() {
        for (Employee employee : allEmployees) {
            System.out.println(employee.getName() + " (Entitled Annual Leaves: " + employee.getAnnualLeaves() + " days)");
        }
    }

    public void listTeamMembers() {
        for (Team team : allTeams) {
            System.out.println(team.getName() + ":");
            team.listMembers();
            team.listActingHeads();
            System.out.println();
        }
    }

    public void listLeaves() {
        for (Employee employee : allEmployees) {
            System.out.println(employee.getName() + ":");
            employee.listLeaves();
        }
    }

    public Employee createEmployee(String n, int yle) // See how it is called in main()
    {
        Employee e = new Employee(n, yle);
        allEmployees.add(e);
        Collections.sort(allEmployees); //allEmployees
        return e;
    }

    public Team createTeam(String teamName, String head) throws ExEmployeeNotFound, ExTeamExists // See how it is called in main()
    {
        Employee e = Employee.searchEmployee(allEmployees, head);
        if (e == null) {
            throw new ExEmployeeNotFound();
        }
        if (findTeam(teamName) != null) {
            throw new ExTeamExists();
        }
        Team t = new Team(teamName, e);
        allTeams.add(t);
        Collections.sort(allTeams); //allTeams
        return t; //Why return?  Ans: Later you'll find it useful for undoable comments.
    }

    public void addTeam(Team team) {
        allTeams.add(team);
        Collections.sort(allTeams);
    }

    public void addEmployee(Employee e) {
        allEmployees.add(e);
        Collections.sort(allEmployees);
    }

    public void removeEmployee(Employee e) {
        allEmployees.remove(e);
    }

    public void removeTeam(Team t) {
        t.getHead().removeTeam(t);
        allTeams.remove(t);
    }

    public void addActingHeadRecord(ActingHeadRecord ahr) {
        ahr.getTeam().addActingHeadRecord(ahr);
    }

    public void removeActingHeadRecord(ActingHeadRecord ahr) {
        ahr.getTeam().removeActingHeadRecord(ahr);
    }
    
    public void checkActingHeadLeaves(LeaveRecord inp_lr, String actingHeadName) throws ExActingHeadLeaves {
        Day startA = inp_lr.getStartDay();
        Day endA = inp_lr.getEndDay();
        for (Team team : allTeams) {
            for (ActingHeadRecord ahr : team.getActingHeadRecords()) {
                Day startB = ahr.getStartDay();
                Day endB = ahr.getEndDay();
                if (ahr.getActingHead().getName().equals(actingHeadName) && startA.compareTo(endB) <= 0 && endA.compareTo(startB) >= 0) {
                    throw new ExActingHeadLeaves(ahr);
                }
            }
        }   
    }
}
