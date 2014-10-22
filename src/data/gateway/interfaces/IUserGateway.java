package data.gateway.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

import data.keys.FriendKey;
import data.keys.Key;
import data.keys.LoginKey;
import data.keys.PersonKey;

public abstract class IUserGateway extends Gateway {

    /**
     * Delegating method for finding a person dependent on the type of key given
     */
    @Override
    public final ResultSet find(Key key) {
        ResultSet result = null;
        if (key instanceof PersonKey) {
            result = find((PersonKey) key);
        }
        else if (key instanceof FriendKey) {
            result = find((FriendKey) key);
        }
        else if (key instanceof LoginKey) {
            result = find((LoginKey) key);
        }
        return result;
    }

    abstract protected ResultSet find(PersonKey key);
    abstract protected ResultSet find(FriendKey key);
    abstract protected ResultSet find(LoginKey key);
}
