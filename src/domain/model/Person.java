package domain.model;

import java.util.ArrayList;

import system.Session;
import domain.model.proxies.*;

public class Person extends User {

    private FriendList friends;
    private IPendingFriendsList pendingFriends;

    private String password;

    /**
     * Construct a new person
     * 
     * @param name
     */
    public Person(String name, String password, String displayName) {
        this.userName = name;
        this.id = -1;
        this.password = password;
        this.displayName = displayName;
        friends = new FriendListProxy(id);
        pendingFriends = new PendingFriendsListProxy(id);
        saveValues();
        Session.getUnitOfWork().markNew(this);
    }

    /**
     * Construct a person from data loaded from a gateway
     * 
     * @param name
     * @param id
     */
    public Person(String name, String displayName, String password, long id) {
        this.id = id;
        this.userName = name;
        this.displayName = displayName;
        this.password = password;
        friends = new FriendListProxy(id);
        pendingFriends = new PendingFriendsListProxy(id);
        saveValues();
    }

    /**
     * @return person id
     */
    public long getID() {
        return id;
    }

    /**
     * @return person name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the person's hashed password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return person display name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Update's the user's password
     * 
     * @param newPass
     *            - text version of the new password
     */
    public void setPassword(String newPass) {
        password = newPass;
        Session.getUnitOfWork().markChanged(this);
    }

    /**
     * Changes this user's name
     * 
     * @param newName
     *            - their new identity
     */
    public void setDisplayName(String newName) {
        displayName = newName;
        Session.getUnitOfWork().markChanged(this);
    }

    /**
     * @return data representation of a friends list
     */
    public ArrayList<User> getFriends() {
        return friends.getFriends();
    }

    /**
     * @return data reference of the list of friends
     */
    protected FriendList getFriendList() {
        return friends;
    }

    public void acceptFriendRequest(User friend) {
        boolean removed = pendingFriends.removeRequest(friend);
        if (removed) {
            friends.insertFriend(friend);
        }
    }

	public void removeFriend(Friend friend) {
		friends.removeFriend(friend);
	}
	
    public void declineFriendRequest(Friend friend) {
        pendingFriends.denyFriend(friend);
    }

    public IPendingFriendsList getPendingFriends() {
        return (IPendingFriendsList) pendingFriends;
    }

    public void requestFriend(Friend friend) {
    	pendingFriends.requestFriend(friend);
    }

    @Override
    public void rollbackValues() {
        userName = (String)values.get("name");
        displayName = (String)values.get("displayName");
        password = (String)values.get("password");
    }

    @Override
    public void saveValues() {
        values.put("name", userName);
        values.put("displayName", displayName);
        values.put("password", password);
    }
    
    @Override
    public String toString()
    {
    	return userName + ":" + password + ":" + displayName;
    }
}
