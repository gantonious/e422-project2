package filetransfer.protocol.messages;

/**
 * Created by George on 2017-04-09.
 */
public class AccessGranted extends Message {
    public AccessGranted() {
        super(MessageTypes.ACCESS_GRANTED);
    }
}
