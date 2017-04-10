import filetransfer.InputOutputSourceFactory;
import filetransfer.authentication.AuthenticationService;
import filetransfer.server.FileServer;
import filetransfer.transport.SocketSource;

/**
 * Created by George on 2017-04-09.
 */
public class ServerMain {
    public static void main(String[] args) {
        AuthenticationService authenticationService = new AuthenticationService();
        InputOutputSourceFactory ioFactory = SocketSource::new;
        FileServer fileServer = new FileServer(authenticationService, ioFactory);

        fileServer.run();
    }
}