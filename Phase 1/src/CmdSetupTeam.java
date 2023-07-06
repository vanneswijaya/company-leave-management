public class CmdSetupTeam extends RecordedCommand {
    private Company company;
    private Team team;
	
	@Override
	public void execute(String[] cmdParts)
	{
		try {
			if (cmdParts.length < 3) {
				throw new ExInsufficientArguments();
			}
			company = Company.getInstance();
			team = company.createTeam(cmdParts[1], cmdParts[2]);

			addUndoCommand(this);
			clearRedoList();

			System.out.println("Done.");
		} catch (ExInsufficientArguments e) {
			System.out.println(e.getMessage());
		} catch (ExTeamExists e) {
			System.out.println(e.getMessage());
		} catch (ExEmployeeNotFound e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void undoMe()
	{
		company.removeTeam(team);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
		try {
			company.createTeam(team.getName(), team.getHead().getName());
			addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
		} catch (ExTeamExists e) {
			System.out.println(e.getMessage());
		} catch (ExEmployeeNotFound e) {
			System.out.println(e.getMessage());
		} 
	}
}
