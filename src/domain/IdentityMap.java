package domain;

import java.util.HashMap;

import data.keys.Key;
import domain.model.DomainModelObject;

/**
 * Wrapped hashmap for mapping keys to the domain model objects loaded by the
 * data mapper
 * 
 * @author nhydock
 *
 */
public class IdentityMap<T extends DomainModelObject> {

    /**
     * Class this identity map is registered for
     */
    Class<T> cls;

    HashMap<Key, T> registry;

    public IdentityMap(Class<T> cls) {
        this.cls = cls;
        this.registry = new HashMap<Key, T>();
    }

    /**
     * Inserts an object into the identity map
     * 
     * @param key
     * @param obj
     * @return false if the object is of the wrong type or if it's already been
     *         inserted
     */
    public boolean put(Key key, Object obj) {
        if (obj == null || cls.isAssignableFrom(obj.getClass())) {
            registry.put(key, cls.cast(obj));
            return true;
        }
        return false;
    }

    public boolean containsKey(Key key) {
        return registry.containsKey(key);
    }

    public T get(Key key) {
        return registry.get(key);
    }

    /**
     * Removes the loaded objected associated with this key
     * 
     * @param key
     */
    public boolean remove(Key key) {
        return registry.remove(key) != null;
    }

    /**
     * @return the type of object that this identity map follows
     */
    public Class<T> getType() {
        return cls;
    }
}
