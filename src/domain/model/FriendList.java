package domain.model;

import java.util.ArrayList;

public interface FriendList {
    public void insertFriend(Friend friend);

    public void removeFriend(Friend friend);

    public ArrayList<Friend> getFriends();

    public long getUserID();
}
