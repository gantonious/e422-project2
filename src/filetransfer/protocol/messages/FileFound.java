package filetransfer.protocol.messages;

/**
 * Created by George on 2017-04-09.
 */
public class FileFound extends Message {
    public FileFound() {
        super(MessageTypes.FILE_FOUND);
    }
}
