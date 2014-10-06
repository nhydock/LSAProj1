package domain;

import java.util.HashMap;

import data.gateway.interfaces.Gateway;
import data.gateway.interfaces.Gateway.Result;
import data.keys.Key;
import domain.model.DomainModelObject;
import domain.model.Uow;

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
    DataMapper() {
    }

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
    public <T extends DomainModelObject> boolean register(Class<T> cls, Gateway<T> gate) {
        if (gatewayMap.containsKey(cls)) {
            return false;
        }
        gatewayMap.put(cls, gate);
        identityRegistry.put(cls, new IdentityMap(cls));
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
    public <T extends DomainModelObject> T get(Class<T> cls, Key<T> key) {
        if (identityRegistry.containsKey(cls)) {
            IdentityMap identityMap = identityRegistry.get(cls);
            if (identityMap.containsKey(key)) {
                return cls.cast(identityMap.get(key));
            } else {
                @SuppressWarnings("unchecked")
                Gateway<T> gate = (Gateway<T>) gatewayMap.get(cls);
                T obj = gate.find(key);
                identityMap.put(key, obj);
                if (obj != null) {
                    obj.loaded();
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
     * Persists changes of an object to the database
     * 
     * @param obj
     */
    public <T extends DomainModelObject> void persist(T object) {
        @SuppressWarnings("unchecked")
        Gateway<T> gate = (Gateway<T>) gatewayMap.get(object.getClass());
        if (object.getUnitOfWork().getState() == Uow.State.Changed) {
            gate.update(object);
            object.loaded();
        } else if (object.getUnitOfWork().getState() == Uow.State.Created) {
            Result<T> result = gate.insert(object);
            result.object.loaded();
            identityRegistry.get(object.getClass()).put(result.key, result.object);
        } else if (object.getUnitOfWork().getState() == Uow.State.Deleted) {
            Key<T> key = gate.delete(object);
            identityRegistry.get(object.getClass()).remove(key);
        }
    }
}
