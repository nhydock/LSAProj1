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

/**
 * Gateway for fetching result sets from the pending_friends table in our
 * database
 * 
 * person_id | friend_id
 * 
 * @author nhydock
 *
 */
public class PendingFriendsGateway extends Gateway {

    @Override
    public ResultSet find(Key key) {
        if (key instanceof PendingFriendListKey) {
            PendingFriendListKey link = (PendingFriendListKey) key;
            try {
                String sql = "SELECT * FROM pending_friends f WHERE f.pid = ? OR f.fid = ?";
                PreparedStatement stmt = Session.getConnection().prepareStatement(sql);
                stmt.setLong(1, link.id);
                stmt.setLong(2, link.id);
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
        // pending friends are not "updated", we only ever insert or delete them
    }

    @Override
    public ResultSet insert(DataContainer[] data) {
        // insert new pending friends into the list
        PendingFriendListData pfldata = (PendingFriendListData) data;

        try {
            String sql = "INSERT INTO pending_friends f (pid, fid) VALUES ";

            for (int i = 0; i < pfldata.length; i++) {
                sql += String.format(((i > 0) ? "," : "") + "(%d, %d)", pfldata.userID, pfldata.friendID);
            }

            PreparedStatement stmt = Session.getConnection().prepareStatement(sql);

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating pending friend relation failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys;
                } else {
                    throw new SQLException("Creating pending friend relation failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return null;
    }

    @Override
    public boolean delete(Key[] key) {
        SpecificPendingFriendsListKey[] list = (SpecificPendingFriendsListKey[]) key;
        // can not delete friend maps, just remove values from map and persist
        // the changes
        try {
            String sql = "DELETE FROM pending_friends WHERE ";

            for (int i = 0; i < key.length; i++) {
                SpecificPendingFriendsListKey friendKey = list[i];
                sql += String.format("(pid = %d AND fid = %d)" + ((i > 0) ? "OR" : ""), friendKey.userID,
                        friendKey.friendID);
            }

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
