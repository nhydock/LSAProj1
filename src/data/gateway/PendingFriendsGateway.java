package data.gateway;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import system.Session;
import data.containers.DataContainer;
import data.containers.PendingFriendsListData;
import data.gateway.interfaces.Gateway;
import data.keys.Key;
import data.keys.PendingFriendsListKey;
import data.keys.SpecificPendingFriendsListKey;

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
        if (key instanceof PendingFriendsListKey) {
            PendingFriendsListKey link = (PendingFriendsListKey) key;
            try {
                String sql = "SELECT * FROM friend_map f JOIN persons p1 on f.pid = p1.id JOIN persons p2 on f.fid = p2.id WHERE f.pid = ? OR f.fid = ? and f.accepted = 0";
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
    	PendingFriendsListData[] pfldata = (PendingFriendsListData[]) data;

        try {
            String sql = "INSERT IGNORE INTO friend_map (pid, fid, accepted) VALUES ";
            
            boolean newRelations = false;
            for (int i = 0; i < pfldata.length; i++) {
                PendingFriendsListData set = pfldata[i];
                newRelations = newRelations || set.outgoingRequests.length > 0;
                
                for (int n = 0; n < set.outgoingRequests.length; n++)
                {
                    sql += String.format(((n > 0) ? "," : "") + "(%d, %d, 0)", set.userID, set.outgoingRequests[n]);
                }
            }
            
            if (newRelations)
            {
	            PreparedStatement stmt = Session.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	            System.out.println(stmt.toString());
	            int affectedRows = stmt.executeUpdate();
	            if (affectedRows == 0) {
	                throw new SQLException("Creating pending friend relation failed, no rows affected.");
	            }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    
    
    }

    @Override
    public ResultSet insert(DataContainer[] data) {
        // pending friend relations are only ever considered "changed"
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
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return false;
    }
}
