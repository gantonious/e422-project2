package filetransfer.protocol.messages;

/**
 * Created by George on 2017-04-09.
 */
public class MessageTypes {
    public static final int AUTHENTICATION_REQUEST = 0;
    public static final int ACCESS_GRANTED = 1;
    public static final int ACCESS_DENIED = 2;
    public static final int FILE_REQUEST = 3;
    public static final int FILE_FOUND = 4;
    public static final int FILE_NOT_FOUND = 5;
    public static final int FILE_RESPONSE = 6;
    public static final int FINISHED = 7;
}
