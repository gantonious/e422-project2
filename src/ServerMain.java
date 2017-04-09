import filetransfer.InputOutputSourceFactory;
import filetransfer.protocol.server.FileServer;
import filetransfer.transport.SocketSource;

/**
 * Created by George on 2017-04-09.
 */
public class ServerMain {
    public static void main(String[] args) {
        InputOutputSourceFactory ioFactory = SocketSource::new;
        FileServer fileServer = new FileServer(ioFactory);

        fileServer.run();
    }
}