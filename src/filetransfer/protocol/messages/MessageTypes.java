package filetransfer.protocol.messages;

/**
 * Created by George on 2017-04-09.
 */
public class MessageTypes {
    public static int AUTHENTICATION_REQUEST = 0;
    public static int AUTHENTICATION_ACKNOWLEDGMENT = 1;
    public static int FILE_REQUEST = 2;
    public static int FILE_FOUND = 3;
    public static int FILE_NOT_FOUND = 4;
    public static int FILE_RESPONSE = 5;
    public static int FINISHED = 6;
}
