package system;

import mock.MockUserMapper;
import domain.mappers.UserMapper;
import system.Session;

public class TestSession extends Session {
	
	public TestSession() {
		super();
	}
	
	@Override
	protected void prepareMappers() {
		mappers.put(UserMapper.class, new MockUserMapper());
        //mappers.put(FriendsMapper.class, new MockFriendsMapper());
        //mappers.put(PendingFriendsMapper.class, new MockPendingFriendsMapper());
	}
	
	@Override
	protected void prepareConnection() {
		//do nothing
	}
	
	@Override
	protected void prepareGateways() {
		//do nothing
	}
}
