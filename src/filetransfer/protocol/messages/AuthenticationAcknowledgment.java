package filetransfer.protocol.messages;

/**
 * Created by George on 2017-04-09.
 */
public class AuthenticationAcknowledgment extends Message {
    public AuthenticationAcknowledgment() {
        super(MessageTypes.AUTHENTICATION_ACKNOWLEDGMENT);
    }
}
