package mock.mapper;

import data.keys.Key;
import domain.mappers.DataMapper;
import domain.model.RealFriendList;

public class MockFriendListMapper implements DataMapper<RealFriendList> {

	@Override
	public RealFriendList find(Key key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(RealFriendList[] obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert(RealFriendList[] obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(RealFriendList[] obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<RealFriendList> getType() {
		return RealFriendList.class;
	}

}
