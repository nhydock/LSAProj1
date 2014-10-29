package domain.model;

import java.util.ArrayList;

public interface FriendList {
    public void insertFriend(User friend);

    public void removeFriend(User friend);

    public ArrayList<User> getFriends();

    public long getUserID();
}
