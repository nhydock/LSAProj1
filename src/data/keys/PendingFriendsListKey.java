package data.keys;

public class PendingFriendsListKey implements Key {
    
    public final long id;

    /**
     * @param id
     *            - person we want the list of friends from
     */
    public PendingFriendsListKey(long id) {
        this.id = id;
    }

    public boolean equals(Object obj) {
        // equals identity
        if (obj == this)
            return true;
        // not null
        if (obj == null)
            return false;
        // is a person key
        if (!(obj instanceof PendingFriendsListKey))
            return false;
        PendingFriendsListKey key = (PendingFriendsListKey) obj;

        // value matching
        boolean eq = true;
        eq = eq && (key.id == this.id);
        return eq;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }
}
