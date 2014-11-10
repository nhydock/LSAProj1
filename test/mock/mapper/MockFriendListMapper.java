package mock.mapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import system.Session;
import data.containers.FriendListData;
import data.containers.PendingFriendsListData;
import data.keys.FriendListKey;
import data.keys.Key;
import data.keys.PersonKey;
import domain.mappers.DataMapper;
import domain.model.PendingFriendsList;
import domain.model.RealFriendList;
import domain.model.User;

public class MockFriendListMapper extends DataMapper<RealFriendList> {
	
	private HashMap<Long, Set<Long>> friends = new HashMap<Long, Set<Long>>();

    @Override
    public RealFriendList read(Key key) {
        FriendListKey link = (FriendListKey)key;
        FriendListData data;
    	if(friends.containsKey(link.id))
    	{
    		Set<Long> r = friends.get(link.id);
    		Iterator<Long> iter = r.iterator();
    		long[] ids = new long[r.size()];
    		int n = 0;
    		while (iter.hasNext())
    		{
    			ids[n] = iter.next();
    			n++;
    		}
    		data = new FriendListData(link.id, ids,new long[0]);
    	}else 
    	{
    		data = new FriendListData(link.id, new long[0], new long[0]);
    	}
        Set<User> friends = new HashSet<User>();

        for (long id : data.friends)
        {
        	PersonKey pkey = new PersonKey(id);
        	friends.add((User)Session.getMapper(User.class).find(pkey));
        }
        
	    RealFriendList list = new RealFriendList(link.id, friends);
	    Session.getIdentityMap(RealFriendList.class).put(link, list);
	    return list;
    }

    @Override
    public void update(RealFriendList[] friendsListData) {
    	for (int i = 0; i < friendsListData.length; i++) 
		{
    		RealFriendList friendList = friendsListData[i];
    		Set<Long> myFriends = friends.get(friendList.getUserID());
    		
    		if (myFriends == null){
			    myFriends = new HashSet<Long>();
			    friends.put(friendList.getUserID(), myFriends);
			}
			
			Set<User> acceptedFriends = friendList.getFriends();
			Iterator<User> iter = acceptedFriends.iterator();
			while (iter.hasNext())
			{
				User friend = iter.next();
				myFriends.add(friend.getID());
			
				Set<Long> theirFriends = friends.get(friend.getID());
				if (theirFriends == null)
				{
					theirFriends = new HashSet<Long>();
					friends.put(friend.getID(), theirFriends);
				}
				theirFriends.add(friendList.getUserID());
			}
			
			Set<User> unfriended = friendList.getRemovedFriends();
        	iter = unfriended.iterator();
        	while (iter.hasNext())
        	{
        		User friend = iter.next();
        		myFriends.remove(friend.getID());
        		Set<Long> theirRequests = friends.get(friend.getID());
        		theirRequests.remove(friendList.getUserID());
        	}
		}
    }

    @Override
    public void insert(RealFriendList[] obj) {
    	for (int i = 0; i < obj.length; i++)
        {
        	RealFriendList list = obj[i];
        	FriendListKey key = new FriendListKey(list.getUserID());
        	Set<User> friends = list.getFriends();
            Set<User> removed = list.getRemovedFriends();
            long[] friendIDs = new long[friends.size()];
            long[] removeIDs = new long[removed.size()];
            
            int n = 0;
            Iterator<User> iter = friends.iterator();
            while (iter.hasNext())
            {
            	friendIDs[n] = iter.next().getID();
            	n++;
            }
            n = 0;
            iter = removed.iterator();
            while (iter.hasNext())
            {
            	removeIDs[n] = iter.next().getID();
            	n++;
            }
            FriendListData data = new FriendListData(list.getUserID(), friendIDs, removeIDs);
            
            Session.getIdentityMap(RealFriendList.class).put(key, list);

    	}
    }

    @Override
    public void delete(RealFriendList[] obj) {
        
        Key[] keys = new Key[obj.length];
        
        for (int i = 0; i < obj.length; i++)
        {
            RealFriendList list = obj[i];
            FriendListKey key = new FriendListKey(list.getUserID());
            keys[i] = key;
            Session.getIdentityMap(RealFriendList.class).remove(key);
          
        }
    }

    @Override
    public Class<RealFriendList> getType() {
        return RealFriendList.class;
    }
}
