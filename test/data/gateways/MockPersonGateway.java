package data.gateways;

import java.util.HashMap;

import data.gateway.interfaces.IPersonGateway;
import data.keys.Key;
import data.keys.PersonKey;
import domain.model.Person;

public class MockPersonGateway implements IPersonGateway {

	private static HashMap<Key, Person> mockList = new HashMap<Key, Person>();
	
	static
	{
		mockList.put(new PersonKey(0), new MockPerson());
		mockList.put(new PersonKey(1), new MockPerson());
		mockList.put(new PersonKey(2), new MockPerson());
		mockList.put(new PersonKey(3), new MockPerson());
	}
	
	@Override
	public Person find(Key key) {
		if (key instanceof PersonKey)
		{
			if (mockList.containsKey(key))
			{
				return mockList.get(key);
			}
		}
		return null;
	}

	@Override
	public void update(Person object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Person object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void persist(Person object) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Mock class for representing a person in testing
	 * @author nhydock
	 *
	 */
	private static class MockPerson extends Person{
		private MockPerson(){super(1, "Walter O'Brien", 1);}
	}
}
