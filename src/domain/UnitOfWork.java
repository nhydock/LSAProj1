package domain;

import java.util.HashMap;

import system.Session;
import domain.mappers.DataMapper;
import domain.model.DomainModelObject;

/**
 * Unit of work handler
 * 
 * @author nhydock
 *
 */
public class UnitOfWork {
    
    /**
     * Possible states of the unit of work
     */
    public static enum State {
        Created, Loaded, Deleted, Changed;
    }

    private HashMap<Class<? extends DomainModelObject>, HashMap<DomainModelObject, State>> register;
    
    public UnitOfWork()
    {
        register = new HashMap<Class<? extends DomainModelObject>, HashMap<DomainModelObject, State>>();
    }
    
    private void ensureTypeExists(Class<? extends DomainModelObject> cls)
    {
        if (!register.containsKey(cls))
        {
            register.put(cls, new HashMap<DomainModelObject, State>());
        }
    }
    
    /**
     * Marks an object as newly created
     * @param obj
     */
    public void markNew(DomainModelObject obj) {
        ensureTypeExists(obj.getClass());
        register.get(obj.getClass()).put(obj, State.Created);
    }
    
    /**
     * Marks an object as changed. If the state is marked as deleted,
     * changes will not matter.
     */
    public void markChanged(DomainModelObject obj) {
        ensureTypeExists(obj.getClass());
        State s = register.get(obj.getClass()).get(obj);
        if (s == null)
        {
            register.get(obj.getClass()).put(obj, State.Changed);
        }
    }

    /**
     * Marks a unit of work as to be deleted. Deletion overrides changes in
     * priority
     */
    public void markDeleted(DomainModelObject obj) {
        ensureTypeExists(obj.getClass());
        State s = register.get(obj.getClass()).get(obj);
        if (s == null)
        {
            register.get(obj.getClass()).put(obj, State.Deleted);
        }
    }
    
    /**
     * Attempts to persist all objects registered in the Unit of Work
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void persist() {
        for (Class<? extends DomainModelObject> type : register.keySet())
        {
            DataMapper mapper = Session.getMapper(type);
            HashMap<DomainModelObject, State> objects = register.get(type);
            for (DomainModelObject obj : objects.keySet())
            {
                State state = objects.get(obj);
                if (state == State.Changed)
                {
                    mapper.update(obj);
                }
                else if (state == State.Deleted)
                {
                    mapper.delete(obj);
                }
                else if (state == State.Created)
                {
                    mapper.insert(obj);
                }
            }
        }
        register.clear();
    }
    
    /**
     * Rollbacks all objects that have been registered in the unit of work
     */
    public void rollback() {
        for (HashMap<DomainModelObject, State> objects : register.values())
        {
            for (DomainModelObject obj : objects.keySet())
            {
                obj.rollbackValues();
            }
        }
        register.clear();
    }
    
    public State getState(DomainModelObject key) {
        return register.get(key.getClass()).get(key);
    }
}
