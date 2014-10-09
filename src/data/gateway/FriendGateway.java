package data.gateway;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import system.Session;
import data.containers.DataContainer;
import data.containers.FriendListData;
import data.gateway.interfaces.Gateway;
import data.keys.FriendListKey;
import data.keys.Key;

public class FriendGateway extends Gateway {

    @Override
    public ResultSet find(Key key) {
        if (key instanceof FriendListKey) {
            FriendListKey link = (FriendListKey) key;
            try {
                String sql = "SELECT * FROM friend_map f JOIN persons p ON f.fid = p.id WHERE f.pid = ?";
                PreparedStatement stmt = Session.getConnection().prepareStatement(sql);
                stmt.setLong(1, link.id);
                ResultSet result = stmt.executeQuery();

                return result;
            } catch (SQLException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        return null;
    }

    @Override
    public void update(DataContainer data) {
        FriendListData object = (FriendListData)data;
        
        try {
            String sql = "DELETE FROM friend_map WHERE pid = ?";

            PreparedStatement stmt = Session.getConnection().prepareStatement(sql);
            stmt.setLong(1, object.id);
            stmt.executeUpdate();

            sql = "INSERT INTO friend_map f (pid, fid) VALUES ";

            long[] friends = object.friends;
            if (friends.length > 0) {
                // sql += "(" + object.getUserID() + ", " + f.getID() + ")";
                sql += "(" + friends[0] + ")";
                for (int i = 1; i < friends.length; i++) {
                    sql += ",(" + friends[i] + ")";
                }
            }

            stmt = Session.getConnection().prepareStatement(sql);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public ResultSet insert(DataContainer data) {
        // can not insert entire maps, updating handles insertion of new
        // relations
        return null;
    }

    @Override
    public boolean delete(Key key) {
        FriendListKey list = (FriendListKey) key;
        // can not delete friend maps, just remove values from map and persist
        // the changes
        try {
            String sql = "DELETE FROM friend_map WHERE pid = ?";

            PreparedStatement stmt = Session.getConnection().prepareStatement(sql);
            stmt.setLong(1, list.id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return false;
    }

}
