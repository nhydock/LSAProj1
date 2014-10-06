package data.gateways;

import data.gateway.interfaces.Gateway;
import data.keys.Key;
import domain.model.FriendList;
import domain.model.RealFriendList;

public class MockRealFriendGateway extends Gateway<RealFriendList> {

    @Override
    public RealFriendList find(Key<RealFriendList> key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(RealFriendList object) {
        // TODO Auto-generated method stub

    }

    @Override
    public Result<RealFriendList> insert(RealFriendList object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Key<RealFriendList> delete(RealFriendList object) {
        // TODO Auto-generated method stub
        return null;
    }

}
