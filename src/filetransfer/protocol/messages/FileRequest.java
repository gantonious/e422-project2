package filetransfer.protocol.messages;

/**
 * Created by George on 2017-04-09.
 */
public class FileRequest extends Message {
    public FileRequest(String fileName) {
        super(MessageTypes.FILE_REQUEST, fileName);
    }

    public String getFilePath() {
        return getDataAsString();
    }

    public static FileRequest from(Message message) {
        return new FileRequest(message.getDataAsString());
    }
}
