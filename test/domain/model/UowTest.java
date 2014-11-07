package domain.model;

import static org.junit.Assert.*;
import mock.MockDomainModel;
import mock.MockKey;

import org.junit.Before;
import org.junit.Test;

import system.Session;
import system.TestSession;
import domain.UnitOfWork;

public class UowTest {

    @Before
    public void reset() {
    	Session.replaceSession(new TestSession());
    }
    
    @Test
    public void testIntialization() {
        //unit of work should be created by session objects
        // this is kind of redundant because we need unit of work objects
        UnitOfWork work = new UnitOfWork();
        assertNotNull(work);
    }
    
    /**
     * A unit of work should keep track of an object's state
     */
    @Test
    public void testStateManaging() {
        UnitOfWork work = new UnitOfWork();
        MockDomainModel test = new MockDomainModel("Bob");
        work.markNew(test);
        assertEquals(UnitOfWork.State.Created, work.getState(test));
        
        //test that we can clear an object from the unit of work
        work.clear(test);
        assertNull(work.getState(test));
    }

    @Test
    public void testChanging() {
        UnitOfWork work = new UnitOfWork();
        MockDomainModel test = new MockDomainModel("Bob");
        work.markNew(test);
        
        //objects marked as new should not be able to change states until they're committed
        assertEquals(UnitOfWork.State.Created, work.getState(test));
        
        work.markChanged(test);
        assertEquals(UnitOfWork.State.Created, work.getState(test));
        
        work.clear(test);
        
        work.markChanged(test);
        // should work under normal conditions
        assertEquals(UnitOfWork.State.Changed, work.getState(test));
    }

    @Test
    public void testMarkForDeletion() {
        UnitOfWork work = Session.getUnitOfWork();
        MockDomainModel test = (MockDomainModel)Session.getMapper(MockDomainModel.class).find(new MockKey("Bob"));
        
        work.markDeleted(test);
        
        // should work if it has been marked initially as loaded
        assertEquals(UnitOfWork.State.Deleted, work.getState(test));
        
        //changing should not work if marked for deletion
        work.markChanged(test);
        assertEquals(UnitOfWork.State.Deleted, work.getState(test));
    }

    /**
     * Test rollback functionality of objects managed by the unit of work
     */
    @Test
    public void testReset() {
        System.err.println("Test reset");
        UnitOfWork work = new UnitOfWork();
        MockDomainModel test = (MockDomainModel)Session.getMapper(MockDomainModel.class).find(new MockKey("Bob"));
        
        //a person just loaded that hasn't had anything changed about it yet
        //  should not be in the unit of work register 
        assertNotNull(test);
        assertEquals("Bob", test.getDisplayName());
        assertNull(work.getState(test));

        String name = test.getDisplayName();
        test.setDisplayName("Bobbo");
        assertEquals("Bobbo", test.getDisplayName());
        
        // objects should naturally tell the unit of work of the session about their change
        // but since we're testing here, we need to explicitly tell our UoW of the change
        work.markChanged(test);
        assertEquals(UnitOfWork.State.Changed, work.getState(test));
        
        //now we need to rollback changes
        work.rollback();
        
        assertEquals(name, test.getDisplayName());
        assertNull(work.getState(test));
    }

}
