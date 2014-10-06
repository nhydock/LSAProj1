package domain.model;

import java.util.HashMap;

public abstract class DomainModelObject {

    private Uow work = new Uow();
    protected HashMap<String, Object> values = new HashMap<String, Object>();
    
    public Uow getUnitOfWork() {
        return work;
    }

    /**
     * Flags the domain object to be deleted from the system
     */
    public void delete() {
        getUnitOfWork().markDeleted();
    }

    /**
     * Marks the domain object as loaded and saves its current values for a rollback point
     */
    public void loaded() {
        getUnitOfWork().markLoaded();
        saveValues();
    }
    
    /**
     * Reverts changes on the DMO to the initial fetched values
     */
    public void rollback()
    {
        if (getUnitOfWork().getState() == Uow.State.Changed)
        {
            getUnitOfWork().markLoaded();
            restoreValues();
        }
    }
    
    /**
     * Restores the values of the DMO to the values that were last saved
     */
    protected abstract void restoreValues();
    
    /**
     * Saves the current values of the object as a restore point of them DMO
     */
    protected abstract void saveValues();
}
