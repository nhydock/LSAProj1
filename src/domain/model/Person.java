package domain.model;

import java.util.ArrayList;

import domain.model.proxies.*;

public class Person extends DomainModelObject {

    private FriendList friends;
    private PendingFriendsList pendingFriends;

    private long id;
    private String name;
    private long password;

    /**
     * Construct a new person
     * 
     * @param name
     */
    public Person(String name, String password) {
        this.name = name;
        this.id = -1;
        this.password = password.hashCode();
    }

    /**
     * Construct a person from data loaded from a gateway
     * 
     * @param name
     * @param id
     */
    public Person(String name, long password, long id) {
        this.id = id;
        this.name = name;
        this.password = password;
        friends = new FriendListProxy();
        pendingFriends = new PendingFriendsListProxy();
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
    public String getName() {
        return name;
    }

    /**
     * @return the person's hashed password
     */
    public long getPassword() {
        return password;
    }

    /**
     * Update's the user's password
     * 
     * @param newPass
     *            - text version of the new password
     */
    public void setPassword(String newPass) {
        password = newPass.hashCode();
        getUnitOfWork().markChanged();
    }

    /**
     * Changes this user's name
     * 
     * @param newName
     *            - their new identity
     */
    public void setName(String newName) {
        name = newName;
        getUnitOfWork().markChanged();
    }

    /**
     * @return data representation of a friends list
     */
    public ArrayList<Friend> getFriends() {
        return friends.getFriends();
    }

    /**
     * @return data reference of the list of friends
     */
    protected FriendList getFriendList() {
        return friends;
    }

    public void acceptFriendRequest(Friend friend) {

        boolean removed = pendingFriends.remove(friend);
        if (removed) {
            friends.insert(friend);
        }
    }
}
