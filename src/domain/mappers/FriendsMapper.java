package domain.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import system.Session;
import data.containers.FriendListData;
import data.gateway.FriendGateway;
import data.keys.FriendKey;
import data.keys.FriendListKey;
import data.keys.Key;
import domain.IdentityMap;
import domain.model.Friend;
import domain.model.RealFriendList;

public class FriendsMapper implements DataMapper<RealFriendList> {

    @Override
    public RealFriendList find(Key key) {
        if (Session.getIdentityMap(RealFriendList.class).containsKey(key))
        {
            return Session.getIdentityMap(RealFriendList.class).get(key);
        }
        
        try {
            ResultSet result = Session.getGateway(FriendGateway.class).find(key);
            FriendListKey link = (FriendListKey)key;
            ArrayList<Friend> friends = new ArrayList<Friend>();

            while (result.next()) {
                Friend friend = new Friend(result.getString("p.name"),
                        result.getString("p.name"));
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
    public void update(RealFriendList obj) {
        ArrayList<Friend> list = obj.getFriends();
        long[] friends = new long[list.size()];
        for (int i = 0; i < friends.length; i++)
        {
            friends[i] = list.get(i).getID();
        }
        
        long id = obj.getUserID();
        FriendListData data = new FriendListData(id, friends);
        FriendGateway gate = Session.getGateway(FriendGateway.class);
        gate.update(data);
    }

    @Override
    public void insert(RealFriendList obj) {
        update(obj);
        
    }

    @Override
    public void delete(RealFriendList obj) {
        long id = obj.getUserID();
        FriendListKey key = new FriendListKey(id);
        FriendGateway gate = Session.getGateway(FriendGateway.class);
        gate.delete(key);
    }

    @Override
    public Class<RealFriendList> getType() {
        return RealFriendList.class;
    }

}
