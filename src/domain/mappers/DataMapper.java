package domain.mappers;

import system.Session;
import data.keys.Key;
import domain.model.DomainModelObject;

public abstract class DataMapper<T extends DomainModelObject> {

	/**
	 * Searches for an object through the mapper.
	 * Defaults to checking the identity map before going through the gateway
	 * @param key
	 * @return
	 */
    public final T find(Key key)
    {
    	return find(key, false);
    }
    
    /**
     * Checks if the key and and object is registered in the corresponding identity map
     *  for this mapper's object type
     * @param key
     * @return
     */
    protected T checkExists(Key key)
    {
    	if (Session.getIdentityMap(getType()).containsKey(key)) {
    		return Session.getIdentityMap(getType()).get(key);
    	}
    	return null;
    }
    
    /**
     * Forcibly loads data through the gateway instead of checking
     * against the identity map first.  All abstracted implementations
     * 
     * @param key
     * @param forceLoad
     * @return
     */
    public final T find(Key key, boolean forceLoad)
    {
    	if (!forceLoad)
    	{
    		T obj = checkExists(key);
    		if (obj != null)
    			return obj;
    	}
    	return read(key);
    }
    
    /**
     * Loads data through the gateway
     * @param key
     * @return
     */
    abstract protected T read(Key key);

    abstract public void update(T[] obj);

    abstract public void insert(T[] obj);

    abstract public void delete(T[] obj);

    abstract public Class<T> getType();
}
