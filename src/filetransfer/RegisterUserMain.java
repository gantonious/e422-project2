package filetransfer;

import filetransfer.authentication.AuthenticationService;

/**
 * Created by George on 2017-04-10.
 */
public class RegisterUserMain {
    public static void main(String[] args) {
        String username = getUsername();
        String password = getPassword();

        AuthenticationService authService = new AuthenticationService();

        try {
            authService.registerUser(username, password);
            System.out.println("User registered.");
        } catch (Exception e) {
            System.out.println("Registration failed due to: " + e.getMessage());
        }
    }

    private static String getUsername() {
        System.out.print("Username: ");
        return System.console().readLine();
    }

    private static String getPassword() {
        System.out.print("Password: ");
        String password1 = String.valueOf(System.console().readPassword());

        System.out.print("Retype password: ");
        String password2 = String.valueOf(System.console().readPassword());

        if (password1.equals(password2)) {
            return password1;
        }

        System.out.println("The passwords given do not match. Try again.");
        return getPassword();
    }
}
