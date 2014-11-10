package domain.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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

public class PendingFriendsMapper extends DataMapper<PendingFriendsList> {

    @Override
    public PendingFriendsList read(Key key) {
    	try {
            ResultSet result = Session.getGateway(PendingFriendsGateway.class).find(key);
            PendingFriendsListKey link = (PendingFriendsListKey) key;

            Set<User> incoming = new HashSet<User>();
            Set<User> outgoing = new HashSet<User>();
            
            IdentityMap<Friend> imap = Session.getIdentityMap(Friend.class);
            while (result.next()) {
                Friend friend;
                if (result.getLong("f.pid") == link.id)
                {
                    friend = new Friend(result.getString("p2.name"), result.getString("p2.display_name"), result.getLong("p2.id"));
                    outgoing.add(friend);
                }
                else
                {
                    friend = new Friend(result.getString("p1.name"), result.getString("p1.display_name"), result.getLong("p1.id"));
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
        	Set<User> friends = p.getOutgoingRequests();
        	long[] ids = new long[friends.size()];
        	Iterator<User> f = friends.iterator();
        	int n = 0;
        	while (f.hasNext())
        	{
        		User friend = f.next();
        		ids[n] = friend.getID();
        		n++;
        	}
        	
        	Set<User> rejected = p.getRejected();
        	long[] remove = new long[rejected.size()];
        	f = rejected.iterator();
        	n = 0;
        	while (f.hasNext())
        	{
        		User friend = f.next();
        		remove[n] = friend.getID();
        		n++;
        	}
        	
        	data[i] = new PendingFriendsListData(p.getUserID(), ids, remove);
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
