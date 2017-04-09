package filetransfer;

import java.net.Socket;

/**
 * Created by George on 2017-04-09.
 */
public interface InputOutputSourceFactory {
    InputOutputSource buildInputOutputSourceFrom(Socket socket);
}
