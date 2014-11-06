package mock.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import system.Session;
import data.containers.FriendListData;
import data.gateway.FriendGateway;
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
        ArrayList<User> friends = new ArrayList<User>();

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
        	ArrayList<User> friends = list.getFriends();
            ArrayList<User> removed = list.getRemovedFriends();
            long[] friendIDs = new long[friends.size()];
            long[] removeIDs = new long[removed.size()];
            for (int n = 0; n < friends.size(); n++)
            {
                friendIDs[n] = friends.get(n).getID();
            }
            for (int n = 0; n < removed.size(); n++)
            {
            	removeIDs[n] = removed.get(n).getID();
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
