package domain.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import system.Session;
import data.keys.PersonKey;
import domain.UnitOfWork;

public class UowTest {

    @After
    public void reset() {
        Session.kill();
    }
    
    @Test
    public void testIntialization() {
        UnitOfWork work = Session.getUnitOfWork();
        //TODO set person mapper to mock person mapper
        assertNotNull(work);
    }

    @Test
    public void testChanging() {
        UnitOfWork work = new UnitOfWork();
        Person test = (Person)Session.getMapper(Person.class).find(new PersonKey(0));
        
        //a person just loaded that hasn't had anything changed about it yet
        //  should not be in the unit of work register 
        assertNull(work.getState(test));
        
        test.setDisplayName("Bobbo");
        
        // should work if it has been marked initially as loaded
        assertEquals(UnitOfWork.State.Changed, work.getState(test));
    }

    @Test
    public void testMarkForDeletion() {
        UnitOfWork work = Session.getUnitOfWork();
        Person test = (Person)Session.getMapper(Person.class).find(new PersonKey(0));
        
        //a person just loaded that hasn't had anything changed about it yet
        //  should not be in the unit of work register 
        assertNull(work.getState(test));
        
        test.setDisplayName("Bobbo");
        
        // should work if it has been marked initially as loaded
        assertEquals(UnitOfWork.State.Changed, work.getState(test));
    }

    @Test
    public void testReset() {
        UnitOfWork work = Session.getUnitOfWork();
        Person test = (Person)Session.getMapper(Person.class).find(new PersonKey(0));
        
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

}
