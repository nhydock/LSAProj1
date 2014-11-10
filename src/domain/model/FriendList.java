package domain.model;

import java.util.Set;

public interface FriendList {
    public void insertFriend(User friend);

    public void removeFriend(User friend);

    public Set<User> getFriends();

    public long getUserID();
}
