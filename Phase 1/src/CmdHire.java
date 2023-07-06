public class CmdHire extends RecordedCommand {
    private Employee e;
    private Company company;
	
	@Override
	public void execute(String[] cmdParts)
	{
		try {
			if (cmdParts.length < 3) {
				throw new ExInsufficientArguments();
			}
			company = Company.getInstance();
			String inpName = cmdParts[1];
			int inpYle = Integer.parseInt(cmdParts[2]);
			if (company.findEmployee(inpName) != null) {
				throw new ExEmployeeExists();
			}
			if (inpYle > 300) {
				throw new ExOutOfRange();
			}
			e = new Employee(inpName, inpYle);
			company.addEmployee(e);

			addUndoCommand(this);
			clearRedoList();

			System.out.println("Done.");
		} catch (ExInsufficientArguments e) {
			System.out.println(e.getMessage());
		} catch (ExEmployeeExists e) {
			System.out.println(e.getMessage());
		} catch (ExOutOfRange e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void undoMe()
	{
		company.removeEmployee(e);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
		company.addEmployee(e);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}
