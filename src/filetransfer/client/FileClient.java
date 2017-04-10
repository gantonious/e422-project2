package filetransfer.client;

import filetransfer.protocol.FileTransferService;
import filetransfer.protocol.exceptions.AuthenticationFailedException;
import filetransfer.protocol.exceptions.FileNotFoundException;
import filetransfer.protocol.exceptions.UnexpectedMessageException;
import filetransfer.protocol.messages.*;

/**
 * Created by George on 2017-04-09.
 */
public class FileClient {
    private FileTransferService fileTransferService;
    private String downloadDirectiory = ".";

    public FileClient(FileTransferService fileTransferService) {
        this.fileTransferService = fileTransferService;
    }

    public void authenticate(String username, String password) {
        AuthenticationRequest authRequest = new AuthenticationRequest(username, password);
        fileTransferService.sendMessage(authRequest);
        confirmAcknowledgement();
    }

    public void downloadFile(String fileName) {
        FileRequest fileRequest = new FileRequest(fileName);
        fileTransferService.sendMessage(fileRequest);
        handleDownloadRequestResponse(fileTransferService.readMessage());
    }

    public void close() {
        fileTransferService.sendMessage(new Finished());
    }

    private void confirmAcknowledgement() {
        Message response = fileTransferService.readMessage();
        if (response.getMessageType() != MessageTypes.ACCESS_GRANTED) {
            throw new AuthenticationFailedException();
        }
    }

    private void handleDownloadRequestResponse(Message response) {
        if (response.getMessageType() == MessageTypes.FILE_NOT_FOUND) {
            throw new FileNotFoundException();
        } else if (response.getMessageType() == MessageTypes.FILE_FOUND) {
            handleFileTransfer();
        }
    }

    private void handleFileTransfer() {
        Message nextMessage = fileTransferService.readMessage();
        guardAgainstWrongMessageType(nextMessage, MessageTypes.FILE_RESPONSE);

        FileResponse fileResponse = FileResponse.from(nextMessage);
        // save file
    }

    private void guardAgainstWrongMessageType(Message message, int expectedType) {
        if (message.getMessageType() != expectedType) {
            throw new UnexpectedMessageException();
        }
    }
}
