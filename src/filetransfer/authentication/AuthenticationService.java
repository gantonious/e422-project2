package filetransfer.authentication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
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

        return doesUserExist(username) &&
               doesPasswordMatch(getUser(username), password);
    }

    @Override
    public void registerUser(String username, String password) {
        String salt = generateSalt(32);
        String saltedPassword = password + salt;
        String hashedSaltedPassword = hashStringWithSha256(saltedPassword);

        User user = new User(username, salt, hashedSaltedPassword);
        saveUser(user);
    }

    private boolean doesUserExist(String username) {
        System.out.println(users.containsKey(username));
        return users.containsKey(username);
    }

    private User getUser(String username) {
        return users.get(username);
    }

    private String generateSalt(int length) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] rawSalt = new byte[length];
        secureRandom.nextBytes(rawSalt);
        return Base64.getEncoder().encodeToString(rawSalt);
    }

    private String hashStringWithSha256(String string) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] rawHashedString = md.digest(string.getBytes());
            return Base64.getEncoder().encodeToString(rawHashedString);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException();
        }
    }

    private boolean doesPasswordMatch(User user, String password) {
        String saltedPassword = password + user.getSalt();
        String hashedSaltedPassword = hashStringWithSha256(saltedPassword);

        return hashedSaltedPassword.equals(user.getPassword());
    }

    private void ensureUsers() {
        try {
            loadUsersFrom(USER_ACCOUNTS_FILE);
        } catch (Exception e) {

        }
    }

    private void loadUsersFrom(String path) throws IOException {
        Files.lines(new File(path).toPath())
             .map(this::buildUserFromLine)
             .forEach(u -> users.put(u.getUsername(), u));
    }

    private void saveUser(User user) {
        try {
            String serializedUser = buildLineFromUser(user);
            System.out.println(serializedUser);
            FileWriter fw = new FileWriter(USER_ACCOUNTS_FILE, true);
            fw.write(serializedUser);
            fw.write("\n");
            fw.close();
        } catch (IOException e) {

        }
    }

    private User buildUserFromLine(String line) {
        String[] lineParts = line.split("\\|");
        return new User(lineParts[0], lineParts[1], lineParts[2]);
    }

    private String buildLineFromUser(User user) {
        return String.format("%s|%s|%s", user.getUsername(), user.getSalt(), user.getPassword());
    }
}
