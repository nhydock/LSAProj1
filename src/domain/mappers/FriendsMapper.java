package domain.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import system.Session;
import data.containers.FriendListData;
import data.gateway.FriendGateway;
import data.keys.FriendKey;
import data.keys.FriendListKey;
import data.keys.Key;
import domain.IdentityMap;
import domain.model.Friend;
import domain.model.RealFriendList;
import domain.model.User;

public class FriendsMapper extends DataMapper<RealFriendList> {

	public RealFriendList read(Key key) {
        try {
            ResultSet result = Session.getGateway(FriendGateway.class).find(key);
            FriendListKey link = (FriendListKey)key;
            Set<User> friends = new HashSet<User>();

            while (result.next()) {
            	Friend friend;
            	if (result.getLong("f.pid") == link.id)
                {
                    friend = new Friend(result.getString("p2.name"), result.getString("p2.name"), result.getLong("p2.id"));
                }
                else
                {
                    friend = new Friend(result.getString("p1.name"), result.getString("p1.name"), result.getLong("p1.id"));
                }
                friends.add(friend);
                
                // insert into the data mapper the loaded friends
                String name = friend.getUserName();
                FriendKey fkey = new FriendKey(name);
                IdentityMap<Friend> imap = Session.getIdentityMap(Friend.class);
                imap.put(fkey, friend);
            }
            RealFriendList list = new RealFriendList(link.id, friends);
            Session.getIdentityMap(RealFriendList.class).put(link, list);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
	}

    @Override
    public void update(RealFriendList[] obj) {
        
        FriendGateway gate = Session.getGateway(FriendGateway.class);
        FriendListData[] data = new FriendListData[obj.length];
        for (int i = 0; i < obj.length; i++)
        {
            RealFriendList list = obj[i];
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
            data[i] = new FriendListData(list.getUserID(), friendIDs, removeIDs);
        }
        gate.update(data);
        for (int i = 0; i < obj.length; i++)
        {
            RealFriendList list = obj[i];
            list.saveValues();
        }
    }

    @Override
    public void insert(RealFriendList[] obj) {
        update(obj);
    }

    @Override
    public void delete(RealFriendList[] obj) {
        
        Key[] keys = new Key[obj.length];
        
        for (int i = 0; i < obj.length; i++)
        {
            RealFriendList list = obj[i];
            FriendListKey key = new FriendListKey(list.getUserID());
            keys[i] = key;
        }
        
        FriendGateway gate = Session.getGateway(FriendGateway.class);
        gate.delete(keys);
    }

    @Override
    public Class<RealFriendList> getType() {
        return RealFriendList.class;
    }

}
