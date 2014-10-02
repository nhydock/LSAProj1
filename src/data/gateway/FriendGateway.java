package data.gateway;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.gateway.interfaces.Gateway;
import data.keys.FriendKey;
import data.keys.FriendListKey;
import data.keys.Key;
import domain.DataMapper;
import domain.model.Friend;
import domain.model.RealFriendList;

public class FriendGateway extends Gateway<RealFriendList> {

    @Override
    public RealFriendList find(Key<RealFriendList> key) {
        if (key instanceof FriendListKey)
        {
            FriendListKey link = (FriendListKey)key;
            try {
                String sql = "SELECT * FROM friend_map f JOIN persons p ON f.fid = p.id WHERE f.pid = ?";
                PreparedStatement stmt = getConnection().prepareStatement(sql);
                stmt.setLong(1, link.id);
                ResultSet result = stmt.executeQuery();

                ArrayList<Friend> friends = new ArrayList<Friend>();
                
                while (result.next())
                {
                    Friend friend = new Friend(result.getString("p.name"), result.getString("p.name"), result.getLong("f.fid"));
                    friends.add(friend);
                    
                    //insert into the data mapper the loaded friends
                    FriendKey fkey = new FriendKey(friend.getUserName());
                    DataMapper.get().put(friend, fkey);
                }
                
                RealFriendList list = new RealFriendList(link.id, friends);
                
                return list;
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        return null;
    }

    @Override
    public void update(RealFriendList object) {
        try {
            String sql = "DELETE FROM friend_map WHERE pid = ?";
            
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setLong(1, object.getUserID());
            stmt.executeUpdate();
            
            sql = "INSERT INTO friend_map f (pid, fid) VALUES ";
            
            ArrayList<Friend> friends = object.getFriends();
            if (friends.size() > 0)
            {
                Friend f = friends.get(0);
                sql += "(" + object.getUserID() + ", " + f.getID() + ")";
                for (int i = 1; i < friends.size(); i++)
                {
                    f = friends.get(i);
                    sql += ",(" + object.getUserID() + ", " + f.getID() + ")";
                }
            }
            
            stmt = getConnection().prepareStatement(sql);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public Result<RealFriendList> insert(RealFriendList object) {
        // can not insert entire maps, updating handles insertion of new relations
        return null;
    }

    @Override
    public Key<RealFriendList> delete(RealFriendList object) {
        // can not delete friend maps, just remove values from map and persist the changes
        try {
            String sql = "DELETE FROM friend_map WHERE pid = ?";
            
            PreparedStatement stmt = getConnection().prepareStatement(sql);
            stmt.setLong(1, object.getUserID());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        return null;
    }

}
