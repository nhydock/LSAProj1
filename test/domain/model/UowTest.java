package domain.model;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.model.Uow.State;

public class UowTest {

    @Test
    public void testIntialization() {
        Uow work = new Uow();
        assertEquals(work.getState(), Uow.State.Created);
    }

    @Test
    public void testChanging() {
        Uow work = new Uow();
        assertEquals(work.getState(), Uow.State.Created);

        //should not effect if the unit of work is marked as to be created
        work.markChanged();
        assertNotEquals(work.getState(), Uow.State.Changed);
        
        //should work if it has been marked initially as loaded
        work.markLoaded();
        work.markChanged();
        assertEquals(work.getState(), Uow.State.Changed);
        
    }

    @Test
    public void testMarkForDeletion() {
        Uow work = new Uow();
        work.markLoaded();
        assertEquals(work.getState(), Uow.State.Loaded);

        work.markDeleted();
        assertEquals(work.getState(), Uow.State.Deleted);

        // uow marked as deleted should not be able to be
        // marked changed after being marked for deletion
        work.markChanged();
        assertEquals(work.getState(), Uow.State.Deleted);
    }

    @Test
    public void testReset() {
        Uow work = new Uow();
        work.markLoaded();
        work.markChanged();
        assertEquals(work.getState(), State.Changed);
        work.markLoaded();
        assertEquals(work.getState(), State.Loaded);
    }
}
