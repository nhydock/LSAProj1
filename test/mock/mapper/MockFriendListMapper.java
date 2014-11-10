package mock.mapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import system.Session;
import data.containers.FriendListData;
import data.keys.FriendListKey;
import data.keys.Key;
import data.keys.PersonKey;
import domain.mappers.DataMapper;
import domain.model.RealFriendList;
import domain.model.User;

public class MockFriendListMapper extends DataMapper<RealFriendList> {
	
	private HashMap<Key, FriendListData> mockData = new HashMap<Key, FriendListData>();

    @Override
    public RealFriendList read(Key key) {
        FriendListKey link = (FriendListKey)key;
    	FriendListData data = mockData.get(key);
    	if (data == null)
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
    public void update(RealFriendList[] obj) {
    	
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
            mockData.put(key, data);
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
            mockData.remove(key);
        }
    }

    @Override
    public Class<RealFriendList> getType() {
        return RealFriendList.class;
    }
}
