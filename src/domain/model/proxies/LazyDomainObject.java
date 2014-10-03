package domain.model.proxies;

import data.keys.Key;
import domain.DataMapper;
import domain.model.DomainModelObject;

public class LazyDomainObject<Proxy extends DomainModelObject> extends
        DomainModelObject {

    private Proxy lazyObj;
    private boolean loaded;

    private final Key<Proxy> key;
    private final Class<Proxy> cls;

    /**
     * Define the requirements of the lazy loaded domain object
     * 
     * @param cls
     *            - specific class object of the proxied type, we need this to
     *            identify the lazy loading in the datamapper
     * @param key
     *            - key identifying the relational identity of the lazy object
     */
    protected LazyDomainObject(Class<Proxy> cls, Key<Proxy> key) {
        this.cls = cls;
        this.key = key;
    }

    /**
     * Flags the domain object to be deleted from the system
     */
    public void delete() {
        getUnitOfWork().markDeleted();
    }

    /**
     * Flags the domain object that is has been loaded from the database
     */
    public void loaded() {
        getUnitOfWork().markLoaded();
    }

    /**
     * Get the proper object that is loaded by this proxy. Makes sure value is
     * loaded before trying to grab it
     */
    protected final Proxy proxyObject() {
        if (!loaded) {
            lazyObj = DataMapper.get().get(cls, key);
            loaded = true;
        }
        return lazyObj;
    }
}
