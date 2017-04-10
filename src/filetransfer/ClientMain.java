package filetransfer;


import filetransfer.client.FileClient;
import filetransfer.protocol.FileTransferService;
import filetransfer.transport.SocketSource;

import java.net.Socket;

/**
 * Created by George on 2017-04-09.
 */
public class ClientMain {
    public static final String exitCommand = "/q";

    public static void main(String[] args) throws Exception {
        FileClient fileClient = buildFileClient();
        authenticate(fileClient);
        listenToFileRequests(fileClient);
    }

    private static FileClient buildFileClient() throws Exception {
        Socket socket = new Socket("localhost", 16000);
        InputOutputSource socketSource = new SocketSource(socket);
        FileTransferService fileTransferService = new FileTransferService(socketSource);
        return new FileClient(fileTransferService);
    }

    private static void authenticate(FileClient fileClient) {
        System.out.print("Username: ");
        String username = System.console().readLine();

        System.out.print("Password: ");
        String password = String.valueOf(System.console().readPassword());

        fileClient.authenticate(username, password);
    }

    private static void listenToFileRequests(FileClient fileClient) {
        while (true) {
            System.out.print("Filename: ");
            String userInput = System.console().readLine();
            if (userInput.equals(exitCommand)) {
                break;
            }

            fileClient.downloadFile(userInput);
        }
    }
}
