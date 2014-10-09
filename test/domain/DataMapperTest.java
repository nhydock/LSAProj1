package domain;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import data.gateways.MockUserGateway;
import data.keys.FriendKey;
import data.keys.PersonKey;
import domain.model.Friend;
import domain.model.Person;
import domain.model.User;

public class DataMapperTest {

    @After
    public void reset() {
        DataMapper.destroy();
        UnitOfWork.destroy();
    }

    /**
     * Test registering gateways to the datamapper by class type
     */
    @Test
    public void testRegister() {
        DataMapper mapper = DataMapper.get();

        MockUserGateway pGateway = new MockUserGateway();

        assertTrue(mapper.register(Person.class, pGateway));
    }

    /**
     * Test to make sure data can be fetched from a gateway through a data
     * mapper
     */
    @Test
    public void testGet() {
        DataMapper mapper = DataMapper.get();
        MockUserGateway pGateway = new MockUserGateway();

        mapper.register(User.class, pGateway);

        Person loaded = mapper.get(Person.class, new PersonKey(1));

        // check that identity of object is equal when loaded again when using
        // a key that is equal
        assertNotNull(loaded);
        assertTrue(loaded == mapper.get(Person.class, new PersonKey(1)));

        // make sure an object that's not in the database isn't in the identity
        // map
        assertNull(mapper.get(Person.class, new PersonKey(-1)));

        // test fetching for something else that has the same super class
        Friend loaded2 = mapper.get(Friend.class, new FriendKey("Bob"));
        assertNotNull(loaded2);
    }

    /**
     * make sure items that are persisted will be created if they don't exist
     * already
     */
    @Test
    public void testPersistingNewObject() {
        DataMapper mapper = DataMapper.get();
        UnitOfWork unitOfWork = UnitOfWork.get();
        MockUserGateway pGateway = new MockUserGateway();
        mapper.register(User.class, pGateway);

        assertNull(mapper.get(Person.class, new PersonKey(MockUserGateway.getNextID())));

        Person p = new Person("Jennifer", "hockeysticks", "lol");
        long id = p.getID();
        assertEquals(UnitOfWork.State.Created, unitOfWork.getState(p));

        assertTrue(mapper.persist(p, UnitOfWork.State.Created));

        Person loaded = mapper.get(Person.class, new PersonKey(MockUserGateway.getNextID() - 1));
        assertNotNull(loaded);
        assertNull(unitOfWork.getState(loaded));

        // objects should not be equal because a new one is generated
        assertNotEquals(p, loaded);

        assertNotEquals(id, loaded.getID());
        assertEquals(MockUserGateway.getNextID() - 1, loaded.getID());
    }

    /**
     * if an item is already in the identity map and changes have occured then
     * it should be updated and remarked to as being loaded
     */
    @Test
    public void testPersistingChanges() {
        DataMapper mapper = DataMapper.get();
        UnitOfWork unitOfWork = UnitOfWork.get();
        MockUserGateway pGateway = new MockUserGateway();
        mapper.register(User.class, pGateway);

        Person test = mapper.get(Person.class, new PersonKey(0));
        assertNotNull(test);

        assertNull(unitOfWork.getState(test));

        test.setDisplayName("Corey");
        assertEquals(UnitOfWork.State.Changed, unitOfWork.getState(test));
        assertEquals("Corey", test.getDisplayName());

        mapper.persist(test, unitOfWork.getState(test));

        // mapper itself will not clear unit of work, unit of work clears when
        // using its own persist method

        assertEquals(test, mapper.get(Person.class, new PersonKey(0)));
        assertEquals("Corey", mapper.get(Person.class, new PersonKey(0)).getDisplayName());
    }
}
