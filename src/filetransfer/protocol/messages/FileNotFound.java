package filetransfer.protocol.messages;

/**
 * Created by George on 2017-04-09.
 */
public class FileNotFound extends Message {
    public FileNotFound() {
        super(MessageTypes.FILE_NOT_FOUND);
    }
}
