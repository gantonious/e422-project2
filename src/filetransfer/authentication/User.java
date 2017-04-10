package filetransfer.authentication;

/**
 * Created by George on 2017-04-10.
 */
public class User {
    private String username;
    private String salt;
    private String password;

    public User(String username, String salt, String password) {
        this.username = username;
        this.salt = salt;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
