package filetransfer.protocol.messages;

import java.nio.ByteBuffer;

/**
 * Created by George on 2017-04-09.
 */
public class Message {
    private int messageType;
    private String data;

    public Message(int messageType, String data) {
        this.messageType = messageType;
        this.data = data;
    }

    public byte[] toByteArray() {
        int dataLength = getDataAsBytes().length;

        return ByteBuffer.allocate(8 + dataLength)
                         .putInt(messageType)
                         .putInt(dataLength)
                         .put(getDataAsBytes())
                         .array();
    }

    public int getMessageType() {
        return messageType;
    }

    public String getData() {
        return data;
    }

    public byte[] getDataAsBytes() {
        return getData().getBytes();
    }
}
