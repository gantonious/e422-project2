package filetransfer.protocol.messages;

/**
 * Created by George on 2017-04-09.
 */
public class AccessDenied extends Message {
    public AccessDenied() {
        super(MessageTypes.ACCESS_DENIED);
    }
}
