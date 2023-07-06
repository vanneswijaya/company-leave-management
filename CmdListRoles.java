public class CmdListRoles implements Command {
    @Override
    public void execute(String[] cmdParts)
    {
        try {
            Company company = Company.getInstance(); 
            Employee e = company.findEmployee(cmdParts[1]);
            if (e == null) {
				throw new ExEmployeeNotFound();
			}
            e.listRoles();
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
        }
    } 
}
