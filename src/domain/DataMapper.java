package domain;

import java.util.HashMap;

import data.gateway.interfaces.Gateway;
import data.keys.Key;

/**
 * Maps data objects that have been pulled in from the database
 * 
 * @author nhydock
 */
public class DataMapper 
{
	/**
	 * Registry of DataMappers assigned per Session
	 */
	private static HashMap<Long, DataMapper> dmRegistry = new HashMap<Long, DataMapper>();
	
	/**
	 * Fetches a data mapper assigned to a session.  If none exists a new one is made
	 * @param sessionID
	 * @return a data mapper
	 */
	public static DataMapper getMapper(long sessionID)
	{
		if (!dmRegistry.containsKey(sessionID))
		{
			DataMapper mapper = new DataMapper();
			dmRegistry.put(sessionID, mapper);
			return mapper;
		}
		return dmRegistry.get(sessionID);
	}
	
	/**
	 * Deletes a datamapper from memory when a session is closed
	 * @param sessionID
	 */
	public static void clearMapper(int sessionID)
	{
		dmRegistry.remove(sessionID);
	}
	
	/**
	 * Knows which gateway to use to fetch an object type
	 */
	private HashMap<Class<?>, Gateway<?>> gatewayMap = new HashMap<Class<?>, Gateway<?>>();
	
	/**
	 * Identity map contain mapping of objects that have been loaded by their primary key
	 */
	private HashMap<Class<?>, IdentityMap> identityRegistry = new HashMap<Class<?>, IdentityMap>();
	
	/**
	 * Constructor for data mapper should be hidden
	 */
	private DataMapper(){}
	
	/**
	 * Links class domain objects with gateways
	 * @param cls - class object representing the domain object 
	 * @param gate - gateway to link to the domain object class type
	 * @return boolean - false if class has already been registered with a gateway
	 */
	public boolean register(Class<?> cls, Gateway<?> gate)
	{
		if (gatewayMap.containsKey(cls))
		{
			return false;
		}
		gatewayMap.put(cls, gate);
		identityRegistry.put(cls, new IdentityMap(cls));
		return true;
	}
	
	/**
	 * Loads a domain object by type using its primary key
	 * @param cls - Class type of the domain object we're grabbing
	 * @param key - Primary Key of the relation to search by
	 * @return loaded obj
	 * @throws NullPointerException if a gateway for the cls has not be registered
	 */
	public <T> T get(Class<T> cls, Key key)
	{
		if (identityRegistry.containsKey(cls))
		{
			IdentityMap identityMap = identityRegistry.get(cls);
			if (identityMap.containsKey(key))
			{
				return cls.cast(identityMap.get(key));		
			}
			else
			{
				Gateway<?> gate = gatewayMap.get(cls);
				Object obj = gate.find(key);
				identityMap.put(key, obj);
				return cls.cast(obj);
			}
		}
		throw new NullPointerException("Gateway not registered for class type " + cls.getName());
	}
	
	/**
	 * Persists changes of an object to the database
	 * @param obj
	 */
	public void persist(Object obj)
	{
		//TODO
	}
}
