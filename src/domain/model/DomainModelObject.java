package domain.model;

import java.util.HashMap;

import system.Session;

public abstract class DomainModelObject {

    protected HashMap<String, Object> values = new HashMap<String, Object>();
    
    /**
     * Flags the domain object to be deleted from the system
     */
    public void delete() {
        Session.getUnitOfWork().markDeleted(this);
    }

    /**
     * Restores the values of the DMO to the values that were last saved
     */
    public abstract void rollbackValues();
    
    /**
     * Saves the current values of the object as a restore point of them DMO
     */
    public abstract void saveValues();
}
