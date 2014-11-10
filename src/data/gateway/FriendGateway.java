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
                String sql = "SELECT * FROM friend_map f JOIN persons p1 on f.pid = p1.id JOIN persons p2 on f.fid = p2.id WHERE (f.pid = ? OR f.fid = ?) and f.accepted = 1";
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
        FriendListData[] objects = (FriendListData[]) data;

        try {
        	for (int i = 0; i < objects.length; i++)
            {
        		FriendListData list = objects[i];
        		for (long friendID : list.friends)
        		{
        			String sql = "UPDATE friend_map SET accepted=1 WHERE (pid=? AND fid=?) OR (pid=? AND fid=?)";
                    PreparedStatement stmt = Session.getConnection().prepareStatement(sql);
                    stmt.setLong(1, list.id);
                    stmt.setLong(2, friendID);
                    stmt.setLong(3, friendID);
                    stmt.setLong(4, list.id);
                    stmt.executeUpdate();
                }
            }

            String sql = "DELETE FROM friend_map WHERE ";
            boolean remove = false;
            for (int i = 0; i < objects.length; i++)
            {
        		FriendListData list = objects[i];
        		remove = remove || list.toRemove.length > 0;
        		for (int n = 0; n < list.toRemove.length; n++)
        		{
        			sql += String.format("(pid=%d AND fid=%d) OR (pid=%d AND fid=%d)", list.id, list.toRemove[n], list.toRemove[n], list.id);
        			if (n + 1 < list.toRemove.length)
        			{
        				sql += " OR ";
        			}
        		}
        		if (i + 1 < objects.length)
        		{
        			sql += " OR ";
        		}
            }
            if (remove) {
	            PreparedStatement stmt = Session.getConnection().prepareStatement(sql);
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
