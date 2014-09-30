package domain;

import java.util.HashMap;

import data.keys.Key;

/**
 * Wrapped hashmap for mapping keys to the domain model objects loaded
 * by the data mapper
 * @author nhydock
 *
 */
public class IdentityMap {

	/**
	 * Class this identity map is registered for
	 */
	Class<?> cls;
	
	HashMap<Key, Object> registry;
	
	public IdentityMap(Class<?> cls)
	{
		this.cls = cls;
		this.registry = new HashMap<Key, Object>();
	}
	
	/**
	 * Inserts an object into the identity map
	 * @param key
	 * @param obj
	 * @return false if the object is of the wrong type or if it's already been inserted
	 */
	public boolean put(Key key, Object obj)
	{
		if (obj == null || obj.getClass() == cls)
		{
			registry.put(key, obj);
			return true;
		}
		return false;
	}

	public boolean containsKey(Key key) {
		return registry.containsKey(key);
	}
	
	public Object get(Key key)
	{
		return registry.get(key);
	}
	
	/**
	 * @return the type of object that this identity map follows
	 */
	public Class<?> getType()
	{
		return cls;
	}
}
