package filetransfer.server;

import filetransfer.InputOutputSource;
import filetransfer.InputOutputSourceFactory;
import filetransfer.authentication.AuthenticationService;
import filetransfer.protocol.FileTransferService;
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
    private AuthenticationService authenticationService;
    private InputOutputSourceFactory inputOutputSourceFactory;

    public FileServer(AuthenticationService authenticationService,
                      InputOutputSourceFactory inputOutputSourceFactory) {
        this.authenticationService = authenticationService;
        this.inputOutputSourceFactory = inputOutputSourceFactory;
        this.serverSocket = buildServerSocket();
    }

    private ServerSocket buildServerSocket() {
        try {
            return new ServerSocket(DEFAULT_PORT);
        } catch (IOException e) {
            throw new SocketIOException(e.getMessage());
        }
    }

    public void run() {
        while(true) {
            try {
                Socket clientSocket = serverSocket.accept();
                InputOutputSource socketIOSource = inputOutputSourceFactory.buildInputOutputSourceFrom(clientSocket);
                FileTransferService fileTransferService = new FileTransferService(socketIOSource);
                ClientHandler clientHandler = new ClientHandler(authenticationService, fileTransferService);
                Thread clientHandlerThread = new Thread(clientHandler::serveClient);
                clientHandlerThread.start();
            } catch (IOException e) {
                continue;
            }
        }
    }
}
