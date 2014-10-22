package domain.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import system.Session;
import data.gateway.PendingFriendsGateway;
import data.keys.FriendKey;
import data.keys.Key;
import data.keys.PendingFriendsListKey;
import domain.IdentityMap;
import domain.model.Friend;
import domain.model.PendingFriendsList;

public class PendingFriendsMapper implements DataMapper<PendingFriendsList> {

    @Override
    public PendingFriendsList find(Key key) {
        
        try {
            ResultSet result = Session.getGateway(PendingFriendsGateway.class).find(key);
            PendingFriendsListKey link = (PendingFriendsListKey) key;

            ArrayList<Friend> incoming = new ArrayList<Friend>();
            ArrayList<Friend> outgoing = new ArrayList<Friend>();
            
            while (result.next()) {
                Friend friend;
                if (result.getLong("f.pid") == link.id)
                {
                    friend = new Friend(result.getString("p2.name"), result.getString("p2.name"));
                    outgoing.add(friend);
                }
                else
                {
                    friend = new Friend(result.getString("p1.name"), result.getString("p1.name"));
                    incoming.add(friend);
                }

                // insert into the data mapper the loaded friends
                String name = friend.getUserName();
                FriendKey fkey = new FriendKey(name);
                IdentityMap<Friend> imap = Session.getIdentityMap(Friend.class);
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
        // TODO Auto-generated method stub
        
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
