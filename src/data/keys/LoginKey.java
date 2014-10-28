package data.keys;

public class LoginKey implements Key {

    public final String username;
    public final String password;
    
    public LoginKey(String uname, String password)
    {
        this.username = uname;
        this.password = password;
    }
    
    public boolean equals(Object obj) {
        // equals identity
        if (obj == this)
            return true;
        // not null
        if (obj == null)
            return false;
        // is a person key
        if (!(obj instanceof PersonKey))
            return false;
        LoginKey key = (LoginKey) obj;

        // value matching
        boolean eq = true;
        eq = eq && (key.username.equals(this.username));
        eq = eq && (key.password.equals(this.password));
        return eq;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (password.hashCode() ^ (password.hashCode() >>> 32));
        result = prime * result + (int) (username.hashCode() ^ (username.hashCode() >>> 32));
        return result;
    }
}
