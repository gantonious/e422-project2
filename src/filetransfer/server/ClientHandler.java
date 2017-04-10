package filetransfer.server;

import filetransfer.protocol.FileTransferService;

/**
 * Created by George on 2017-04-09.
 */
public class ClientHandler {
    private FileTransferService fileTransferService;

    public ClientHandler(FileTransferService fileTransferService) {
        this.fileTransferService = fileTransferService;
    }

    public void serveClient() {

    }
}
