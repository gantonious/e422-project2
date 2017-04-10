package filetransfer.server;

import filetransfer.authentication.AuthenticationService;
import filetransfer.protocol.FileTransferService;
import filetransfer.protocol.messages.*;

/**
 * Created by George on 2017-04-09.
 */
public class ClientHandler {
    private String fileSource = ".";
    private AuthenticationService authenticationService;
    private FileTransferService fileTransferService;

    public ClientHandler(AuthenticationService authenticationService,
                         FileTransferService fileTransferService) {
        this.authenticationService = authenticationService;
        this.fileTransferService = fileTransferService;
    }

    public void serveClient() {
        while(true) {
            Message message = fileTransferService.readMessage();
            handleMessage(message);
        }
    }

    public void handleMessage(Message message) {
        switch (message.getMessageType()) {
            case MessageTypes.AUTHENTICATION_REQUEST:
                handleAuthenticationRequest(AuthenticationRequest.from(message));
                break;
            case MessageTypes.FILE_REQUEST:
                handleFileRequest(FileRequest.from(message));
                break;
            case MessageTypes.FINISHED:
                handleFinished();
                break;
            default:
                break;
        }
    }

    private void handleAuthenticationRequest(AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();
        String password = authenticationRequest.getPassword();

        if (authenticationService.isUserAuthenticated(username, password)) {
            fileTransferService.sendMessage(new AccessGranted());
        } else {
            fileTransferService.sendMessage(new AccessDenied());
        }
    }

    private void handleFileRequest(FileRequest fileRequest) {

    }

    private void handleFinished() {

    }
}
