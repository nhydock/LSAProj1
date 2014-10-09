package data.gateway.interfaces;

import java.sql.ResultSet;

import data.keys.FriendKey;
import data.keys.Key;
import data.keys.PersonKey;

public abstract class IUserGateway extends Gateway {

    /**
     * Delegating method for finding a person dependent on the type of key given
     */
    @Override
    public final ResultSet find(Key key) {
        if (key instanceof PersonKey) {
            return find((PersonKey) key);
        }
        if (key instanceof FriendKey) {
            return find((FriendKey) key);
        }
        return null;
    }

    abstract protected ResultSet find(PersonKey key);
    abstract protected ResultSet find(FriendKey key);
}
