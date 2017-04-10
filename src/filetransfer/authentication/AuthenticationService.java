package filetransfer.authentication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by George on 2017-04-09.
 */
public class AuthenticationService implements IAuthenticationService {
    private static final String USER_ACCOUNTS_FILE = "./accounts.txt";
    private Map<String, User> users;

    public AuthenticationService() {
        this.users = new HashMap<>();
    }

    public boolean isUserAuthenticated(String username, String password) {
        ensureUsers();
        return true;
    }

    @Override
    public void registerUser(String username, String password) {

    }

    private void ensureUsers() {
        try {
            loadUsersFrom(USER_ACCOUNTS_FILE);
        } catch (Exception e) {

        }
    }

    private void loadUsersFrom(String path) throws IOException {
        Files.lines(new File(USER_ACCOUNTS_FILE).toPath())
             .map(this::buildUserFromLine)
             .forEach(u -> users.put(u.getUsername(), u));
    }

    private User buildUserFromLine(String line) {
        String[] lineParts = line.split("|");
        return new User(lineParts[0], lineParts[1], lineParts[2]);
    }
}
