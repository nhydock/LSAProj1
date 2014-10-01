package domain;

import static org.junit.Assert.*;

import org.junit.Test;

import data.gateways.MockPersonGateway;
import data.keys.PersonKey;
import domain.model.Person;
import domain.model.Uow;

public class DataMapperTest {
	
	/**
	 * Test registering gateways to the datamapper by class type
	 */
	@Test
	public void testRegister() {
		DataMapper mapper = new DataMapper();
		
		MockPersonGateway pGateway = new MockPersonGateway();
		
		assertTrue(mapper.register(Person.class, pGateway));
	}

	/**
	 * Test to make sure data can be fetched from a gateway through a data mapper
	 */
	@Test
	public void testGet() {
		DataMapper mapper = new DataMapper();	
		MockPersonGateway pGateway = new MockPersonGateway();
	
		mapper.register(Person.class, pGateway);
		
		Person loaded = mapper.get(Person.class, new PersonKey(1));
		
		//check that identity of object is equal when loaded again when using
		// a key that is equal
		assertNotNull(loaded);
		assertTrue(loaded == mapper.get(Person.class, new PersonKey(1)));
		
		//make sure an object that's not in the database isn't in the identity map
		assertNull(mapper.get(Person.class, new PersonKey(-1)));
	}

	/**
	 * make sure items that are persisted will be created if they don't exist already
	 */
	@Test
	public void testPersistingNewObject() {
		DataMapper mapper = new DataMapper();	
		MockPersonGateway pGateway = new MockPersonGateway();
		mapper.register(Person.class, pGateway);
		
		assertNull(mapper.get(Person.class, new PersonKey(MockPersonGateway.getNextID())));

		Person p = new Person("Jennifer", "hockeysticks");
		long id = p.getID();
		assertEquals(Uow.State.Created, p.getUnitOfWork().getState());
		
		mapper.persist(p);
		
		Person loaded = mapper.get(Person.class, new PersonKey(MockPersonGateway.getNextID()-1));
		assertNotNull(loaded);
		assertEquals(Uow.State.Loaded, loaded.getUnitOfWork().getState());
		
		//objects should not be equal because a new one is generated
		assertNotEquals(p, loaded);
		
		assertNotEquals(id, loaded.getID());
		assertEquals(MockPersonGateway.getNextID()-1, loaded.getID());
		
	}
	
	/**
	 * if an item is already in the identity map and changes have occured
	 * then it should be updated and remarked to as being loaded
	 */
	@Test
	public void testPersistingChanges() {
		DataMapper mapper = new DataMapper();	
		MockPersonGateway pGateway = new MockPersonGateway();
		mapper.register(Person.class, pGateway);
		
		Person test = mapper.get(Person.class, new PersonKey(0));
		assertNotNull(test);
		assertEquals(Uow.State.Loaded, test.getUnitOfWork().getState());
		
		test.setName("Corey");
		assertEquals(Uow.State.Changed, test.getUnitOfWork().getState());
		
		mapper.persist(test);
		
		assertEquals(Uow.State.Loaded, test.getUnitOfWork().getState());
		assertEquals(test, mapper.get(Person.class, new PersonKey(0)));
	}
}
