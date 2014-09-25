package domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import data.gateways.MockActivityGateway;
import data.gateways.MockPersonGateway;
import data.keys.PersonKey;
import domain.model.Person;

public class DataMapperTest {

	public final int SESSION_ID = -1;
	
	@After
	public void tearDown() {
		//make sure data mapper for test session is cleared after each test
		DataMapper.clearMapper(SESSION_ID);
	}
	
	@Test
	public void testSetup() {
		//Test to make sure that when getting a mapper for a session
		// we'll always get the same mapper when using the same session ID
		
		DataMapper mapper = DataMapper.getMapper(SESSION_ID);
		DataMapper mapper2 = DataMapper.getMapper(SESSION_ID-1);
		
		assertNotEquals(mapper, mapper2);
		assertEquals(mapper, DataMapper.getMapper(SESSION_ID));
		
		DataMapper.clearMapper(SESSION_ID-1);
	}
	
	@Test
	public void testRegister() {
		DataMapper mapper = DataMapper.getMapper(SESSION_ID);
		
		MockPersonGateway pGateway = new MockPersonGateway();
		MockActivityGateway aGateway = new MockActivityGateway();
		
		//test making sure the mapper can not register multiple gateways
		// for a single type
		assertTrue(mapper.register(Person.class, pGateway));
		assertFalse(mapper.register(Person.class, aGateway));
	}

	@Test
	public void testGet() {
		DataMapper mapper = DataMapper.getMapper(SESSION_ID);	
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

}
