package mock.mapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import system.Session;
import data.containers.PendingFriendsListData;
import data.keys.FriendKey;
import data.keys.Key;
import data.keys.PendingFriendsListKey;
import domain.IdentityMap;
import domain.mappers.DataMapper;
import domain.model.Friend;
import domain.model.PendingFriendsList;
import domain.model.User;

public class MockPendingFriendsListMapper extends DataMapper<PendingFriendsList> {
	
	private HashMap<Long, Set<Long>> requests = new HashMap<Long, Set<Long>>();
	
	public MockPendingFriendsListMapper()
	{
		
	}
	
    @Override
    public PendingFriendsList read(Key key) {
    	PendingFriendsList obj = null;
    	PendingFriendsListKey link = (PendingFriendsListKey) key;
    	
    	PendingFriendsListData data;
    	if(requests.containsKey(link.id))
    	{
    		Set<Long> r = requests.get(link.id);
    		Iterator<Long> iter = r.iterator();
    		long[] ids = new long[r.size()];
    		int n = 0;
    		while (iter.hasNext())
    		{
    			ids[n] = iter.next();
    			n++;
    		}
    		data = new PendingFriendsListData(link.id, ids,new long[0]);
    	}else 
    	{
    		data = new PendingFriendsListData(link.id, new long[0], new long[0]);
    	}
    	
    	Set<User> incoming = new HashSet<User>();
    	Set<User> outgoing = new HashSet<User>();
    	
    	IdentityMap<Friend> imap = Session.getIdentityMap(Friend.class);
    	for (int i = 0; i < data.outgoingRequests.length; i++)
    	{
    		Friend frnd;
    		if(data.outgoingRequests[i] == link.id)
    		{
    			frnd = new Friend("name", "displayName", data.outgoingRequests[i]);
    			outgoing.add(frnd);
    		}
    		else
    		{
    			frnd = new Friend("name", "displayName", data.outgoingRequests[i]);
    			incoming.add(frnd);
    		}
    		String name = frnd.getUserName();
        	FriendKey fkey = new FriendKey(name);
        	imap.put(fkey, data);
    	}
    	
    	obj = new PendingFriendsList(data.userID, incoming, outgoing);
    	Session.getIdentityMap(PendingFriendsList.class).put(key, obj);
    	return obj;
    }

    @Override
    public void update(PendingFriendsList[] pendingFriends) {
    	for (int i = 0; i < pendingFriends.length; i++) 
		{
			PendingFriendsList f = pendingFriends[i];
			Set<Long> frequests = requests.get(f.getUserID());
			if (frequests == null){
			    frequests = new HashSet<Long>();
			    requests.put(f.getUserID(), frequests);
			}
			
			Set<User> outgoingRequests = f.getOutgoingRequests();
			long[] ids = new long[outgoingRequests.size()];
			Iterator<User> friends = outgoingRequests.iterator();
			int n = 0;
			while (friends.hasNext())
			{
				User friend = friends.next();
				ids[n] = friend.getID();
				frequests.add(friend.getID());
				n++;
			
				Set<Long> theirRequests = requests.get(friend.getID());
				if (theirRequests == null)
				{
					theirRequests = new HashSet<Long>();
					requests.put(friend.getID(), theirRequests);
				}
				theirRequests.add(f.getUserID());
			}
			
			Set<User> rejected = f.getRejected();
        	friends = rejected.iterator();
        	while (friends.hasNext())
        	{
        		User friend = friends.next();
        		frequests.remove(friend.getID());
        		Set<Long> theirRequests = requests.get(friend.getID());
        		theirRequests.remove(f.getUserID());
        	}
		}
    }

    @Override
    public void insert(PendingFriendsList[] pendingFriends) {
    	PendingFriendsListData[] data = new PendingFriendsListData[pendingFriends.length];
		for (int i = 0; i < pendingFriends.length; i++) 
		{
			PendingFriendsList f = pendingFriends[i];
			Set<User> requests = f.getOutgoingRequests();
			long[] ids = new long[requests.size()];
			Iterator<User> friends = requests.iterator();
			int n = 0;
			while (friends.hasNext())
			{
				User friend = friends.next();
				ids[n] = friend.getID();
				n++;
			}
			data[i] = new PendingFriendsListData(f.getUserID(), ids, new long[0]);
			
			PendingFriendsListData frnd = data[i];
			PendingFriendsListKey key = new PendingFriendsListKey(data[i].userID);
			Session.getIdentityMap(PendingFriendsList.class).put(key, f);
				
	
		}
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
