package filetransfer.server;

import filetransfer.authentication.IAuthenticationService;
import filetransfer.protocol.FileTransferService;
import filetransfer.protocol.messages.*;
import filetransfer.server.exceptions.ClientNotAuthenticatedException;
import filetransfer.shared.exceptions.SocketIOException;
import filetransfer.utils.FileUtils;

/**
 * Created by George on 2017-04-09.
 */
public class ClientHandler {
    private IAuthenticationService authenticationService;
    private FileTransferService fileTransferService;

    private boolean isAlive = true;
    private boolean isAuthenticated = false;
    private String fileSource = "./srv/";

    public ClientHandler(IAuthenticationService authenticationService,
                         FileTransferService fileTransferService) {
        this.authenticationService = authenticationService;
        this.fileTransferService = fileTransferService;
    }

    public void serveClient() {
        while(isAlive) {
            try {
                Message message = fileTransferService.readMessage();
                handleMessage(message);
            } catch (SocketIOException e) {
                return;
            }
        }
    }

    public void handleMessage(Message message) {
        switch (message.getMessageType()) {
            case MessageTypes.AUTHENTICATION_REQUEST:
                handleAuthenticationRequest(AuthenticationRequest.from(message));
                break;
            case MessageTypes.FILE_REQUEST:
                checkForAuthentication();
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
            isAuthenticated = true;
        } else {
            fileTransferService.sendMessage(new AccessDenied());
        }
    }

    private void handleFileRequest(FileRequest fileRequest) {
        String requestedFile = fileSource + fileRequest.getFileName();
        if (FileUtils.doesFileExist(requestedFile)) {
            handleFileFound(requestedFile);
        } else {
            handleFileNotFound();
        }
    }

    private void handleFileFound(String requestedFile) {
        fileTransferService.sendMessage(new FileFound());
        byte[] rawFile = FileUtils.readFileAsByteArray(requestedFile);
        fileTransferService.sendMessage(new FileResponse(rawFile));
    }

    private void handleFileNotFound() {
        fileTransferService.sendMessage(new FileNotFound());
    }

    private void handleFinished() {
        isAlive = false;
        fileTransferService.close();
    }

    private void checkForAuthentication() {
        if (!isAuthenticated) {
            throw new ClientNotAuthenticatedException();
        }
    }
}
