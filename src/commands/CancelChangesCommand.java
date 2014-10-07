package commands;

import domain.UnitOfWork;

/**
 * Tells the system to cancel any pending changes
 * 
 * @author merlin
 *
 */
public class CancelChangesCommand implements Command {

    /**
     * 
     * @see Command#execute()
     */
    @Override
    public void execute() {
        UnitOfWork.get().rollback();
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
