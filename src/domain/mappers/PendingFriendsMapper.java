package domain.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import system.Session;
import data.containers.PendingFriendsListData;
import data.gateway.PendingFriendsGateway;
import data.keys.FriendKey;
import data.keys.Key;
import data.keys.PendingFriendsListKey;
import domain.IdentityMap;
import domain.model.Friend;
import domain.model.PendingFriendsList;
import domain.model.User;

public class PendingFriendsMapper implements DataMapper<PendingFriendsList> {

    @Override
    public PendingFriendsList find(Key key) {
        
        try {
            ResultSet result = Session.getGateway(PendingFriendsGateway.class).find(key);
            PendingFriendsListKey link = (PendingFriendsListKey) key;

            ArrayList<User> incoming = new ArrayList<User>();
            ArrayList<User> outgoing = new ArrayList<User>();
            
            IdentityMap<Friend> imap = Session.getIdentityMap(Friend.class);
            while (result.next()) {
                Friend friend;
                if (result.getLong("f.pid") == link.id)
                {
                    friend = new Friend(result.getString("p2.name"), result.getString("p2.name"), result.getLong("p2.id"));
                    outgoing.add(friend);
                }
                else
                {
                    friend = new Friend(result.getString("p1.name"), result.getString("p1.name"), result.getLong("p1.id"));
                    incoming.add(friend);
                }

                // insert into the data mapper the loaded friends
                String name = friend.getUserName();
                FriendKey fkey = new FriendKey(name);
                imap.put(fkey, friend);
            }
            
            PendingFriendsList list = new PendingFriendsList(link.id, incoming, outgoing);
            Session.getIdentityMap(PendingFriendsList.class).put(link, list);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(PendingFriendsList[] obj) {
        PendingFriendsListData[] data = new PendingFriendsListData[obj.length];
        for (int i = 0; i < obj.length; i++)
        {
        	PendingFriendsList p = obj[i];
        	ArrayList<User> friends = p.getOutgoingRequests();
        	long[] ids = new long[friends.size()];
        	for (int n = 0; n < friends.size(); n++)
        	{
        		User friend = friends.get(n);
        		ids[n] = friend.getID();
        	}
        	data[i] = new PendingFriendsListData(p.getUserID(), ids);
        }
    	Session.getGateway(PendingFriendsGateway.class).update(data);
    	for (int i = 0; i < obj.length; i++)
    	{
    		PendingFriendsList p = obj[i];
        	p.saveValues();
    	}
    }

    @Override
    public void insert(PendingFriendsList[] obj) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(PendingFriendsList[] obj) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Class<PendingFriendsList> getType() {
        return PendingFriendsList.class;
    }

}
