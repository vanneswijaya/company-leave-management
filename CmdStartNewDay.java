public class CmdStartNewDay extends RecordedCommand {
    private String prev_d;
    private String new_d;
	
	@Override
	public void execute(String[] cmdParts)
	{
		prev_d = SystemDate.getSDay();
        SystemDate.createTheInstance(cmdParts[1]);
        new_d = SystemDate.getSDay();

		addUndoCommand(this);
		clearRedoList();

		System.out.println("Done.");
	}
	
	@Override
	public void undoMe()
	{
		SystemDate.createTheInstance(prev_d);
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
		SystemDate.createTheInstance(new_d);
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}
