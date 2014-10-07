package domain;

import java.util.HashMap;

import domain.model.DomainModelObject;

/**
 * Unit of work handler
 * 
 * @author nhydock
 *
 */
public class UnitOfWork {

    /**
     * Handle assigning unit of work per thread
     */
    private static final ThreadLocal<UnitOfWork> unitOfWork = new ThreadLocal<UnitOfWork>() {
        /**
         * Generate a new unit of work per thread
         */
        public UnitOfWork initialValue() {
            return new UnitOfWork();
        }
    };

    /**
     * Get the current unit of work for the thread
     */
    public static UnitOfWork get() {
        return unitOfWork.get();
    }
    
    
    /**
     * Destroys the current unit of work by replacing it with a new one
     */
    public static void destroy() {
        unitOfWork.set(new UnitOfWork());
    }
    /**
     * Possible states of the unit of work
     */
    public static enum State {
        Created, Loaded, Deleted, Changed;
    }

    private HashMap<DomainModelObject, State> register;
    
    private UnitOfWork()
    {
        register = new HashMap<DomainModelObject, State>();
    }
    
    /**
     * Marks a unit of work as changed. If the state is marked as deleted,
     * changes will not matter.
     */
    public void markChanged(DomainModelObject obj) {
        State s = register.get(obj);
        if (s == null)
        {
            register.put(obj, State.Changed);
        }
    }

    /**
     * Marks a unit of work as to be deleted. Deletion overrides changes in
     * priority
     */
    public void markDeleted(DomainModelObject obj) {
        State s = register.get(obj);
        if (s == null)
        {
            register.put(obj, State.Deleted);
        }
    }
    
    /**
     * Attempts to persist all objects registered in the Unit of Work
     */
    public void persist() {
        for (DomainModelObject o : register.keySet())
        {
            DataMapper.get().persist(o, register.get(o));
        }
        register.clear();
    }
    
    /**
     * Rollbacks all objects that have been registered in the unit of work
     */
    public void rollback() {
        for (DomainModelObject o : register.keySet())
        {
            o.rollbackValues();
        }
        register.clear();
    }
    
    public State getState(DomainModelObject key) {
        return register.get(key);
    }
}
