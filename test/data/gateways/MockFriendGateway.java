package data.gateways;

import data.gateway.interfaces.Gateway;
import data.keys.Key;
import domain.model.Friend;

public class MockFriendGateway extends Gateway<Friend> {

    @Override
    public Friend find(Key<Friend> key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(Friend object) {
        // TODO Auto-generated method stub

    }

    @Override
    public Result<Friend> insert(Friend object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Key<Friend> delete(Friend object) {
        // TODO Auto-generated method stub
        return null;
    }

}
