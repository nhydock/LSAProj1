package mock;

import data.keys.Key;
import domain.model.LazyDomainObject;

public class MockProxyObject extends LazyDomainObject<MockDomainModel> implements MockObj {

    public MockProxyObject(Key key) {
        super(MockDomainModel.class, key);
        
    }
    
    @Override
    public String getDisplayName() {
        return proxyObject().getDisplayName();
    }

}
