package domain.model;

import java.util.ArrayList;

public interface IPendingFriendsList {

    public void insert(Friend friend);
    public ArrayList<Friend> getAsArrayList();
    public boolean remove(Friend friend);
    public void rollbackValues();
    public ArrayList<Friend> getFriends();
    public long getUserID();
}
