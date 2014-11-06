package mock.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import system.Session;
import data.containers.DataContainer;
import data.containers.FriendListData;
import data.containers.PendingFriendsListData;
import data.keys.FriendKey;
import data.keys.FriendListKey;
import data.keys.Key;
import data.keys.PendingFriendsListKey;
import data.keys.PersonKey;
import domain.IdentityMap;
import domain.mappers.DataMapper;
import domain.model.Friend;
import domain.model.FriendList;
import domain.model.PendingFriendsList;
import domain.model.Person;
import domain.model.User;

public class MockPendingFriendsListMapper implements DataMapper<PendingFriendsList> {
	
	private HashMap<Key, PendingFriendsListData> mockData = new HashMap<Key, PendingFriendsListData>();

	public MockPendingFriendsListMapper()
	{
		
	}
	
    @Override
    public PendingFriendsList find(Key key) {
    	PendingFriendsList obj = null;
        
    	PendingFriendsListData friend = mockData.get(key);
    	PendingFriendsListKey link = (PendingFriendsListKey) key;
    	
    	ArrayList<User> incoming = new ArrayList<User>();
    	ArrayList<User> outgoing = new ArrayList<User>();
    	
    	IdentityMap<Friend> imap = Session.getIdentityMap(Friend.class);
    	for (int i = 0; i < friend.outgoingRequests.length; i++)
    	{
    		Friend frnd;
    		if(friend.outgoingRequests[i] == link.id)
    		{
    			frnd = new Friend("name", "displayName", friend.outgoingRequests[i]);
    			outgoing.add(frnd);
    		}
    		else
    		{
    			frnd = new Friend("name", "displayName", friend.outgoingRequests[i]);
    			incoming.add(frnd);
    		}
    		String name = frnd.getUserName();
        	FriendKey fkey = new FriendKey(name);
        	imap.put(fkey, friend);
    	}
    	
    	
    	PendingFriendsListData data = (PendingFriendsListData) friend;
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
				
			mockData.put(key, frnd);
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
