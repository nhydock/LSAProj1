package domain.model;

import system.Session;
import data.keys.Key;

/**
 * 
 * @author nhydock
 *
 * @param <Proxy>
 */
public class LazyDomainObject<Proxy extends DomainModelObject> extends
        DomainModelObject {

    private Proxy lazyObj;
    private boolean loaded;

    private final Key key;
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
    protected LazyDomainObject(Class<Proxy> cls, Key key) {
        this.cls = cls;
        this.key = key;
    }
    
    /**
     * Get the proper object that is loaded by this proxy. Makes sure value is
     * loaded before trying to grab it
     */
    @SuppressWarnings("unchecked")
    protected final Proxy proxyObject() {
        if (!loaded) {
            lazyObj = (Proxy)Session.getMapper(cls).find(key, true);
            loaded = true;
        }
        return lazyObj;
    }

    @Override
    public void rollbackValues() {
        proxyObject().rollbackValues();
    }

    @Override
    public void saveValues() {
        proxyObject().saveValues();
    }
    
    /**
     * Tells loaded resources to forcibly reload themselves for an exact copy
     * from the database again
     */
    public void unload() {
        loaded = false;
    }
}
