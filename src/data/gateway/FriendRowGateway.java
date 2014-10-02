package data.gateway;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import data.gateway.interfaces.Gateway;
import data.keys.FriendKey;
import data.keys.Key;
import domain.model.Friend;

public class FriendRowGateway extends Gateway<Friend> {

    @Override
    public Friend find(Key<Friend> key) {
        if (key instanceof FriendKey)
        {
            FriendKey link = (FriendKey)key;
            try {
                String sql = "SELECT * FROM friend_map f JOIN persons p ON f.fid = p.id WHERE p.name = ?";
                PreparedStatement stmt = getConnection().prepareStatement(sql);
                stmt.setString(1, link.name);
                ResultSet result = stmt.executeQuery();
                result.next();

                Friend friend = new Friend(result.getString("p.name"), result.getString("p.name"), result.getLong("f.fid"));                    
                return friend;
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        return null;
    }

    @Override
    public void update(Friend object) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public data.gateway.interfaces.Gateway.Result<Friend> insert(Friend object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Key<Friend> delete(Friend object) {
        // TODO Auto-generated method stub
        return null;
    }

}
