package filetransfer;


import filetransfer.client.FileClient;
import filetransfer.encryption.TEAEncryptedSource;
import filetransfer.protocol.FileTransferService;
import filetransfer.protocol.exceptions.AuthenticationFailedException;
import filetransfer.protocol.exceptions.FileNotFoundException;
import filetransfer.transport.SocketSource;

import java.net.Socket;

/**
 * Created by George on 2017-04-09.
 */
public class ClientMain {
    public static final String exitCommand = "/q";

    public static void main(String[] args) throws Exception {
        FileClient fileClient = buildFileClient();
        hookIntoClientShutdown(fileClient);

        try {
            authenticate(fileClient);
        } catch (AuthenticationFailedException e) {
            System.out.println("Authentication failed, exiting.");
            return;
        }

        listenToFileRequests(fileClient);
    }

    private static void hookIntoClientShutdown(FileClient fileClient) {
        Runnable shutdownTask = () -> endSession(fileClient);
        Runtime.getRuntime().addShutdownHook(new Thread(shutdownTask));
    }

    private static FileClient buildFileClient() throws Exception {
        Socket socket = new Socket("localhost", 16000);
        InputOutputSource socketSource = new SocketSource(socket);
        System.out.println("Establishing encrypted connection...");
        InputOutputSource encryptedSource = new TEAEncryptedSource(socketSource);
        FileTransferService fileTransferService = new FileTransferService(encryptedSource);
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

            download(fileClient, userInput);
        }
    }

    private static void download(FileClient fileClient, String filename) {
        try {
            fileClient.downloadFile(filename);
            System.out.println("Successfully downloaded " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("'" + filename + "' could not be found.");
        }
    }

    private static void endSession(FileClient fileClient) {
        fileClient.close();
        System.out.println("Goodbye.");
    }
}
