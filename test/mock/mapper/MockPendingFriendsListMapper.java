package mock.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
	
	private HashMap<Key, PendingFriendsListData> mockData = new HashMap<Key, PendingFriendsListData>();
	private HashMap<Long, Set<Long>> requests = new HashMap<Long, Set<Long>>();
	
	public MockPendingFriendsListMapper()
	{
		
	}
	
    @Override
    public PendingFriendsList read(Key key) {
    	PendingFriendsList obj = null;
    	PendingFriendsListKey link = (PendingFriendsListKey) key;
    	
    	PendingFriendsListData data = mockData.get(link);
    	if (data == null)
    	{
    		data = new PendingFriendsListData(link.id, new long[0]);
    	}
    	
    	ArrayList<User> incoming = new ArrayList<User>();
    	ArrayList<User> outgoing = new ArrayList<User>();
    	
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
    	PendingFriendsListData[] data = new PendingFriendsListData[pendingFriends.length];
		for (int i = 0; i < pendingFriends.length; i++) 
		{
			PendingFriendsList f = pendingFriends[i];
			Set<Long> frequests = requests.get(f.getUserID());
			if (frequests == null){
			    frequests = new HashSet<Long>();
			    requests.put(f.getUserID(), frequests);
			}
			ArrayList<User> friends = f.getOutgoingRequests();
			long[] ids = new long[friends.size()];
			for (int n = 0; n < friends.size(); n++)
			{
				User friend = friends.get(n);
				ids[n] = friend.getID();
				
				frequests.add(friend.getID());
			}
			data[i] = new PendingFriendsListData(f.getUserID(), ids);
			
			PendingFriendsListData frnd = data[i];
			PendingFriendsListKey key = new PendingFriendsListKey(data[i].userID);
				
			mockData.put(key, frnd);
		}
		
    	
    }

    @Override
    public void insert(PendingFriendsList[] pendingFriends) {
    	PendingFriendsListData[] data = new PendingFriendsListData[pendingFriends.length];
		for (int i = 0; i < pendingFriends.length; i++) 
		{
			PendingFriendsList f = pendingFriends[i];
			ArrayList<User> friends = f.getOutgoingRequests();
			long[] ids = new long[friends.size()];
			for (int n = 0; n < friends.size(); n++)
			{
				User friend = friends.get(n);
				ids[n] = friend.getID();
			}
			data[i] = new PendingFriendsListData(f.getUserID(), ids);
			
			PendingFriendsListData frnd = data[i];
			PendingFriendsListKey key = new PendingFriendsListKey(data[i].userID);
			Session.getIdentityMap(PendingFriendsList.class).put(key, f);
				
			mockData.put(key, frnd);
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
