package domain.model;

import static org.junit.Assert.*;
import mock.MockDomainModel;
import mock.MockKey;

import org.junit.After;
import org.junit.Test;

import system.Session;
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
        MockDomainModel test = (MockDomainModel)Session.getMapper(MockDomainModel.class).find(new MockKey("Bob"));
        
        //a person just loaded that hasn't had anything changed about it yet
        //  should not be in the unit of work register 
        assertNull(work.getState(test));
        
        work.markChanged(test);
        
        // should work if it has been marked initially as loaded
        assertEquals(UnitOfWork.State.Changed, work.getState(test));
    }

    @Test
    public void testMarkForDeletion() {
        UnitOfWork work = Session.getUnitOfWork();
        MockDomainModel test = (MockDomainModel)Session.getMapper(MockDomainModel.class).find(new MockKey("Bob"));
        
        //a person just loaded that hasn't had anything changed about it yet
        //  should not be in the unit of work register 
        assertNull(work.getState(test));
        
        work.markDeleted(test);
        
        // should work if it has been marked initially as loaded
        assertEquals(UnitOfWork.State.Deleted, work.getState(test));
    }

    @Test
    public void testReset() {
        UnitOfWork work = Session.getUnitOfWork();
        MockDomainModel test = (MockDomainModel)Session.getMapper(MockDomainModel.class).find(new MockKey("Bob"));
        
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
