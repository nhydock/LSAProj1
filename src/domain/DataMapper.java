package domain;

import java.util.HashMap;

import data.gateway.interfaces.Gateway;
import data.gateway.interfaces.Gateway.Result;
import data.keys.Key;
import domain.UnitOfWork.State;
import domain.model.DomainModelObject;

/**
 * Maps data objects that have been pulled in from the database
 * 
 * @author nhydock
 */
public class DataMapper {
    /**
     * Handle assigning datamappers per thread
     */
    private static final ThreadLocal<DataMapper> dataMapper = new ThreadLocal<DataMapper>() {
        /**
         * Generate a new datamapper per thread
         */
        public DataMapper initialValue() {
            return new DataMapper();
        }
    };

    /**
     * Get the current datamapper for the thread
     */
    public static DataMapper get() {
        return dataMapper.get();
    }
    
    /**
     * Destroys the current unit of work by replacing it with a new one
     */
    public static void destroy() {
        dataMapper.set(new DataMapper());
    }
    
    /**
     * Knows which gateway to use to fetch an object type
     */
    private HashMap<Class<? extends DomainModelObject>, Gateway<?>> gatewayMap = new HashMap<Class<? extends DomainModelObject>, Gateway<?>>();

    /**
     * Identity map contain mapping of objects that have been loaded by their
     * primary key
     */
    private HashMap<Class<? extends DomainModelObject>, IdentityMap> identityRegistry = new HashMap<Class<? extends DomainModelObject>, IdentityMap>();

    /**
     * Constructor for data mapper should be hidden
     */
    private DataMapper() {}

    /**
     * Links class domain objects with gateways
     * 
     * @param cls
     *            - class object representing the domain object
     * @param gate
     *            - gateway to link to the domain object class type
     * @return boolean - false if class has already been registered with a
     *         gateway
     */
    public <T extends DomainModelObject> boolean register(Class<T> cls, Gateway<?> gate) {
        if (gatewayMap.containsKey(cls)) {
            return false;
        }
        gatewayMap.put(cls, gate);
        if (!identityRegistry.containsKey(gate.getType()))
        {
            identityRegistry.put(gate.getType(), new IdentityMap(gate.getType()));
        }
        return true;
    }

    /**
     * Loads a domain object by type using its primary key
     * 
     * @param cls
     *            - Class type of the domain object we're grabbing
     * @param key
     *            - Primary Key of the relation to search by
     * @return loaded obj
     * @throws NullPointerException
     *             if a gateway for the cls has not be registered
     */
    public <T extends DomainModelObject> T get(Class<T> cls, Key<?> key) {
        Gateway<?> gate = getGateway(cls);
        if (gate != null)
        {
            IdentityMap identityMap = identityRegistry.get(gate.getType());
            if (identityMap.containsKey(key)) {
                return cls.cast(identityMap.get(key));
            } else {
                T obj = cls.cast(gate.find(key));
                identityMap.put(key, obj);
                if (obj != null) {
                    obj.saveValues();
                }
                return obj;
            }
        }
        throw new NullPointerException("Gateway not registered for class type " + cls.getName());
    }

    /**
     * Puts an object that has been loaded/made into its corresponding identity
     * map
     * 
     * @param obj
     * @param key
     */
    public <T extends DomainModelObject> void put(T obj, Key<T> key) {
        @SuppressWarnings("unchecked")
        Class<T> cls = (Class<T>) obj.getClass();
        if (identityRegistry.containsKey(cls)) {
            IdentityMap identityMap = identityRegistry.get(cls);
            identityMap.put(key, obj);
        }
        throw new NullPointerException("Gateway not registered for class type " + cls.getName());
    }
    
    /**
     * Get the gateway for a class type.  If a class is not registered explicitly,
     * but its superclass is, return that
     * @param cls
     * @return
     */
    public Gateway<?> getGateway(Class<? extends DomainModelObject> cls)
    {
        Class<?> c = cls;
        Gateway<?> gate;
        do
        {
            gate = gatewayMap.get(c);
            c = c.getSuperclass();
        } while (gate == null && c != null);
        
        if (gate == null)
        {
            throw (new NullPointerException("Gateway not found for class " + cls.getName()));
        }
        return gate;
    }

    /**
     * Persists changes of an object to the database
     * 
     * @param obj
     */
    protected <T extends DomainModelObject> boolean persist(T object, State state) {
        Gateway<?> gate = getGateway(object.getClass());
        IdentityMap imap = identityRegistry.get(gate.getType());
        if (state == UnitOfWork.State.Changed) {
            gate.update(gate.getType().cast(object));
            object.saveValues();
            return true;
        } else if (state == UnitOfWork.State.Created) {
            Result<?> result = gate.insert(gate.getType().cast(object));
            result.object.saveValues();
            return imap.put(result.key, result.object);
        } else if (state == UnitOfWork.State.Deleted) {
            Key<?> key = gate.delete(object);
            return imap.remove(key);
        }
        return false;
    }
}
