package filetransfer.authentication;

/**
 * Created by George on 2017-04-10.
 */
public interface IAuthenticationService {
    boolean isUserAuthenticated(String username, String password);
    void registerUser(String username, String password);
}
