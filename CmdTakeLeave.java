import java.util.ArrayList;

public class CmdTakeLeave extends RecordedCommand {
    private Employee e;
    private Company company;
    private String leaveType;
    private String startDay;
    private String endDay;
    private LeaveRecord leaveRecord;
	private ArrayList<ActingHeadRecord> actingHeadRecords = new ArrayList<ActingHeadRecord>();
	
	@Override
	public void execute(String[] cmdParts)
	{
		try {
			if (cmdParts.length < 5) {
				throw new ExInsufficientArguments();
			}
			company = Company.getInstance();
			e = company.findEmployee(cmdParts[1]);
            leaveType = cmdParts[2];
            startDay = cmdParts[3];
            endDay = cmdParts[4];

            if (leaveType.equals("AL")) {
                leaveRecord = new LeaveRecord_AL(startDay, endDay);
            } else if (leaveType.equals("BL")) {
                leaveRecord = new LeaveRecord_BL(startDay, endDay);
            }  else if (leaveType.equals("SL")) {
                leaveRecord = new LeaveRecord_SL(startDay, endDay);
            }  else if (leaveType.equals("NL")) {
                leaveRecord = new LeaveRecord_NL(startDay, endDay);
            }

			if (e.isHead()) {
				e.checkActingHeadInput(cmdParts);
				e.checkActingHeadExists(cmdParts);
				e.checkActingHeadOnLeave(leaveRecord, cmdParts);
			}

			company.checkActingHeadLeaves(leaveRecord, cmdParts[1]);
			e.checkOverlappingLeaves(leaveRecord);
			e.checkValidStartLeave(leaveRecord);
			e.checkBalance(leaveRecord);
			e.checkALBL(leaveRecord);
			e.checkValidAL(leaveRecord);

            e.addLeave(leaveRecord);
			
			for (int i = 5; i < cmdParts.length; i += 2) {
				ActingHeadRecord ahr = new ActingHeadRecord(leaveRecord.startDay, leaveRecord.endDay, company.findEmployee(cmdParts[i+1]), company.findTeam(cmdParts[i]));
				company.addActingHeadRecord(ahr);
				actingHeadRecords.add(ahr);
			 }

			addUndoCommand(this);
			clearRedoList();

			System.out.println("Done.");
		} catch (ExInsufficientArguments e) {
			System.out.println(e.getMessage());
		} catch (ExLeaveOverlapped e) {
			System.out.println(e.getMessage());
		} catch (ExInvalidStartLeave e) {
			System.out.println(e.getMessage());
		} catch (ExInsufficientAL e) {
			System.out.println(e.getMessage());
		} catch (ExInsufficientSL e) {
			System.out.println(e.getMessage());
		} catch (ExALType e) {
			System.out.println(e.getMessage());
		} catch (ExBLType e) {
			System.out.println(e.getMessage());
		} catch (ExInvalidAL e) {
			System.out.println(e.getMessage());
		} catch (ExMissingActingHead e) {
			System.out.println(e.getMessage());
		} catch (ExActingHeadNotFound e) {
			System.out.println(e.getMessage());
		} catch (ExActingHeadOnLeave e) {
			System.out.println(e.getMessage());
		} catch (ExActingHeadLeaves e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void undoMe()
	{
		e.removeLeave(leaveRecord);
		for (ActingHeadRecord ahr : actingHeadRecords) {
			company.removeActingHeadRecord(ahr);
		}
		addRedoCommand(this); //<====== upon undo, we should keep a copy in the redo list (addRedoCommand is implemented in RecordedCommand.java)
	}
	
	@Override
	public void redoMe()
	{
		e.addLeave(leaveRecord);
		for (ActingHeadRecord ahr : actingHeadRecords) {
			company.addActingHeadRecord(ahr);
		}
		addUndoCommand(this); //<====== upon redo, we should keep a copy in the undo list
	}
}
