package domain.model;

import data.keys.Key;
import domain.DataMapper;

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

    @Override
    protected void restoreValues() {
        proxyObject().restoreValues();
    }

    @Override
    protected void saveValues() {
        proxyObject().saveValues();
    }
}
