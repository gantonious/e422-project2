package filetransfer.protocol.messages;

/**
 * Created by George on 2017-04-09.
 */
public class AuthenticationRequest extends Message {
    private String username;
    private String password;

    public AuthenticationRequest(String username, String password) {
        super(MessageTypes.AUTHENTICATION_REQUEST, encodeCredentials(username, password));
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static String encodeCredentials(String username, String password) {
        return String.format("%s&%s", username, password);
    }

    public static AuthenticationRequest from(String data) {
        String username = data.split("&")[0];
        String password = data.split("&")[1];
        return new AuthenticationRequest(username, password);
    }
}
