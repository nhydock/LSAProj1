package domain.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class UowTest {

	@Test
	public void testIntialization() {
		Uow work = new Uow();
		assertEquals(work.getState(), Uow.State.Loaded);
	}

	@Test
	public void testChanging() {
		Uow work = new Uow();
		assertEquals(work.getState(), Uow.State.Loaded);
	
		work.markChanged();
		assertEquals(work.getState(), Uow.State.Changed);
	}
	
	@Test
	public void testMarkForDeletion() {
		Uow work = new Uow();
		assertEquals(work.getState(), Uow.State.Loaded);
	
		work.markDeleted();
		assertEquals(work.getState(), Uow.State.Deleted);
		
		//uow marked as deleted should not be able to be 
		// marked changed after being marked for deletion
		work.markChanged();
		assertEquals(work.getState(), Uow.State.Deleted);
	}
}
