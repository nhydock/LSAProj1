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
                String sql = "SELECT * FROM friend_map f JOIN persons p ON f.fid = p.id WHERE f.pid = ? AND f.accepted = 1";
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
    public void update(DataContainer[] data) {
        FriendListData[] object = (FriendListData[]) data;

        try {
            String sql = "DELETE FROM friend_map WHERE pid IN (";

            sql += object[0].id;
            for (int i = 1; i < object.length; i++) {
                sql += "," + object[i].id;
            }
            sql += ")";

            PreparedStatement stmt = Session.getConnection().prepareStatement(sql);
            stmt.executeUpdate();

            sql = "INSERT INTO friend_map f (pid, fid) VALUES ";

            for (int i = 0; i < object.length; i++) {
                if (i > 0) {
                    sql += ",";
                }
                sql += "(";
                FriendListData d = object[i];
                long[] friends = d.friends;
                if (friends.length > 0) {
                    // sql += "(" + object.getUserID() + ", " + f.getID() + ")";
                    sql += "(" + d.id + "," + friends[0] + ")";
                    for (int n = 1; n < friends.length; n++) {
                        sql += ",(" + d.id + "," + friends[n] + ")";
                    }
                }

                sql += ")";

                stmt = Session.getConnection().prepareStatement(sql);
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public ResultSet insert(DataContainer[] data) {
        // can not insert entire maps, updating handles insertion of new
        // relations
        return null;
    }

    @Override
    public boolean delete(Key[] key) {
        FriendListKey[] list = (FriendListKey[]) key;
        // can not delete friend maps, just remove values from map and persist
        // the changes
        try {
            String sql = "DELETE FROM friend_map WHERE pid IN (?";

            for (int i = 1; i < key.length; i++) {
                sql += ",?";
            }
            sql += ")";

            PreparedStatement stmt = Session.getConnection().prepareStatement(sql);
            for (int i = 0, n = 1; i < list.length; i++, n++) {
                stmt.setLong(n, list[i].id);
            }
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return false;
    }

}
