package commands;

import system.Session;

/**
 * Tells the system to cancel any pending changes
 * 
 * @author merlin
 *
 */
public class CommandToCancelChanges implements Command {

    /**
     * 
     * @see Command#execute()
     */
    @Override
    public void execute() {
        Session.getUnitOfWork().rollback();
    }

    /**
     * Nothing needs to be returned here (null). The tests will retrieve
     * anything they want to check by re-finding appropriate records
     * 
     * @see Command#getResult()
     */
    @Override
    public Object getResult() {
        // TODO Auto-generated method stub
        return null;
    }

}
