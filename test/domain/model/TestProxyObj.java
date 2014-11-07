package domain.model;

import static org.junit.Assert.*;

import mock.MockDomainModel;
import mock.MockKey;
import mock.MockProxyObject;

import org.junit.Before;
import org.junit.Test;

import system.Session;
import system.TestSession;

public class TestProxyObj {

    @Before
    public void setup() {
        Session.replaceSession(new TestSession());
        
        //prep for loading
        MockDomainModel src = new MockDomainModel();
        src.setDisplayName("Casey");
        Session.getUnitOfWork().commit();
    }

    @Test
    public void testInitialization() {
        MockDomainModel src = (MockDomainModel)Session.getMapper(MockDomainModel.class).find(new MockKey("Casey"));
        MockProxyObject obj = new MockProxyObject(new MockKey("Casey"));
        
        //version in model should be same data fetched as proxy
        assertEquals(src.getDisplayName(), obj.getDisplayName());
    }
    
    @Test
    public void testUnloading() {
        MockProxyObject obj = new MockProxyObject(new MockKey("Casey"));
        
        MockDomainModel loaded = obj.proxyObject();
        assertNotNull(loaded);
        
        //if we unload, the object that will be the proxy object should have the same
        // data, but be a new object as it was forcibly loaded from the database
        obj.unload();
        
        assertNotNull(obj.proxyObject());
        assertFalse(loaded == obj.proxyObject());
    }
    
}
