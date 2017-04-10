package filetransfer.server;

import filetransfer.InputOutputSource;
import filetransfer.InputOutputSourceFactory;
import filetransfer.shared.exceptions.SocketIOException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by George on 2017-04-09.
 */
public class FileServer {
    private static final int DEFAULT_PORT = 16000;

    private ServerSocket serverSocket;
    private InputOutputSourceFactory inputOutputSourceFactory;

    public FileServer(InputOutputSourceFactory inputOutputSourceFactory) {
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
        } catch (IOException e) {
            throw new SocketIOException(e.getMessage());
        }
        this.inputOutputSourceFactory = inputOutputSourceFactory;
    }

    public void run() {
        while(true) {
            try {
                Socket clientSocket = serverSocket.accept();
                InputOutputSource socketIOSource = inputOutputSourceFactory.buildInputOutputSourceFrom(clientSocket);
            } catch (IOException e) {
                continue;
            }
        }
    }
}
