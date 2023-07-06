public class CmdAddTeamMember extends RecordedCommand {
    private Employee e;
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
			team = company.findTeam(cmdParts[1]);
			if (team == null) {
				throw new ExTeamNotFound();
			}
			e = company.findEmployee(cmdParts[2]);
			if (e == null) {
				throw new ExEmployeeNotFound();
			}
			if (team.findMember(e.getName()) != null) {
				throw new ExEmployeeJoined();
			}
			team.addMember(e);
			e.addTeam(team);

			addUndoCommand(this);
			clearRedoList();

			System.out.println("Done.");
		} catch (ExInsufficientArguments e) {
			System.out.println(e.getMessage());
		} catch (ExTeamNotFound e) {
			System.out.println(e.getMessage());
		} catch (ExEmployeeNotFound e) {
			System.out.println(e.getMessage());
		} catch (ExEmployeeJoined e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void undoMe()
	{
		team.removeMember(e);
		e.removeTeam(team);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
		team.addMember(e);
		e.addTeam(team);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}
