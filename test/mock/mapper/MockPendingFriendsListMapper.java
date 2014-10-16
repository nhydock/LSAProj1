package mock.mapper;

import data.keys.Key;
import domain.mappers.DataMapper;
import domain.model.PendingFriendsList;

public class MockPendingFriendsListMapper implements DataMapper<PendingFriendsList> {

    @Override
    public PendingFriendsList find(Key key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(PendingFriendsList[] obj) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void insert(PendingFriendsList[] obj) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(PendingFriendsList[] obj) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Class<PendingFriendsList> getType() {
        return PendingFriendsList.class;
    }

}
