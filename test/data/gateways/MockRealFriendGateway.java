package data.gateways;

import data.gateway.interfaces.Gateway;
import data.keys.Key;
import domain.model.DomainModelObject;
import domain.model.RealFriendList;

public class MockRealFriendGateway extends Gateway<RealFriendList> {

    @Override
    public DomainModelObject find(Key<?> key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(DomainModelObject object) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public data.gateway.interfaces.Gateway.Result<?> insert(DomainModelObject object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Key<?> delete(DomainModelObject object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Class<RealFriendList> getType() {
        // TODO Auto-generated method stub
        return null;
    }


}
