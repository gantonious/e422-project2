package filetransfer.protocol.messages;

/**
 * Created by George on 2017-04-09.
 */
public class FileRequest extends Message {
    public FileRequest(String fileName) {
        super(MessageTypes.FILE_REQUEST, fileName);
    }

    public String getFileName() {
        return getDataAsString();
    }

    public static FileRequest from(String data) {
        return new FileRequest(data);
    }
}
