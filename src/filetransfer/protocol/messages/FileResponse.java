package filetransfer.protocol.messages;

/**
 * Created by George on 2017-04-09.
 */
public class FileResponse extends Message {
    public FileResponse(byte[] rawFile) {
        super(MessageTypes.FILE_RESPONSE, rawFile);
    }

    public byte[] getFile() {
        return getData();
    }
}
