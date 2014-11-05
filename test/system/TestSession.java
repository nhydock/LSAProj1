package system;

import mock.MockDomainModel;
import mock.mapper.MockDataMapper;
import mock.mapper.MockFriendListMapper;
import mock.mapper.MockPendingFriendsListMapper;
import mock.mapper.MockUserMapper;
import domain.mappers.FriendsMapper;
import domain.mappers.PendingFriendsMapper;
import domain.mappers.UserMapper;
import system.Session;

public class TestSession extends Session {
	
	public TestSession() {
		super();
	}
	
	@Override
	protected void prepareMappers() {
		mappers.put(UserMapper.class, new MockUserMapper());
        mappers.put(FriendsMapper.class, new MockFriendListMapper());
        mappers.put(PendingFriendsMapper.class, new MockPendingFriendsListMapper());
        mappers.put(MockDataMapper.class, new MockDataMapper());
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
