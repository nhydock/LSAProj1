package domain.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import data.gateways.MockUserGateway;
import data.keys.PersonKey;
import domain.DataMapper;
import domain.UnitOfWork;

public class UowTest {

    @After
    public void reset() {
        UnitOfWork.destroy();
    }
    
    @Test
    public void testIntialization() {
        UnitOfWork work = UnitOfWork.get();
        assertNotNull(work);
    }

    @Test
    public void testChanging() {
        UnitOfWork work = UnitOfWork.get();
        DataMapper dm = DataMapper.get();
        dm.register(Person.class, new MockUserGateway());
        Person test = dm.get(Person.class, new PersonKey(0));
        
        //a person just loaded that hasn't had anything changed about it yet
        //  should not be in the unit of work register 
        assertNull(work.getState(test));
        
        test.setDisplayName("Bobbo");
        
        // should work if it has been marked initially as loaded
        assertEquals(UnitOfWork.State.Changed, work.getState(test));
    }

    @Test
    public void testMarkForDeletion() {
        UnitOfWork work = UnitOfWork.get();
        DataMapper dm = DataMapper.get();
        dm.register(Person.class, new MockUserGateway());
        Person test = dm.get(Person.class, new PersonKey(0));
        
        //a person just loaded that hasn't had anything changed about it yet
        //  should not be in the unit of work register 
        assertNull(work.getState(test));
        
        test.setDisplayName("Bobbo");
        
        // should work if it has been marked initially as loaded
        assertEquals(UnitOfWork.State.Changed, work.getState(test));
    }

    @Test
    public void testReset() {
        UnitOfWork work = UnitOfWork.get();
        DataMapper dm = DataMapper.get();
        dm.register(Person.class, new MockUserGateway());
        Person test = dm.get(Person.class, new PersonKey(0));
        
        String name = test.getDisplayName();
        
        //a person just loaded that hasn't had anything changed about it yet
        //  should not be in the unit of work register 
        assertNull(work.getState(test));
        
        test.setDisplayName("Bobbo");
        
        // should work if it has been marked initially as loaded
        assertEquals(UnitOfWork.State.Changed, work.getState(test));
        assertEquals("Bobbo", test.getDisplayName());
        
        //now we need to rollback changes
        work.rollback();
        
        assertEquals(name, test.getDisplayName());
        assertNull(work.getState(test));
    }

    @Test
    public void testPackage() throws ClassNotFoundException {
        // Class c = Class.forName("Uow.java");
        assertEquals("domain.UnitOfWork", UnitOfWork.class.getName());
    }
}
