package filetransfer;

import filetransfer.InputOutputSourceFactory;
import filetransfer.authentication.AuthenticationService;
import filetransfer.encryption.TEAEncryptedSource;
import filetransfer.server.FileServer;
import filetransfer.transport.SocketSource;

/**
 * Created by George on 2017-04-09.
 */
public class ServerMain {
    public static void main(String[] args) {
        AuthenticationService authenticationService = new AuthenticationService();
        InputOutputSourceFactory ioFactory = s -> {
            SocketSource socketSource = new SocketSource(s);
            return new TEAEncryptedSource(socketSource);
        };

        FileServer fileServer = new FileServer(authenticationService, ioFactory);

        fileServer.run();
    }
}