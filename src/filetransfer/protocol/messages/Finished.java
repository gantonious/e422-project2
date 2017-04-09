package filetransfer.protocol.messages;

/**
 * Created by George on 2017-04-09.
 */
public class Finished extends Message {
    public Finished() {
        super(MessageTypes.FINISHED);
    }
}
