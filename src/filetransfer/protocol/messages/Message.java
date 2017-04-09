package filetransfer.protocol.messages;

import java.nio.ByteBuffer;

/**
 * Created by George on 2017-04-09.
 */
public class Message {
    private int messageType;
    private byte[] data;

    public Message(int messageType) {
        this(messageType, "");
    }

    public Message(int messageType, String data) {
        this(messageType, data.getBytes());
    }

    public Message(int messageType, byte[] data) {
        this.messageType = messageType;
        this.data = data;
    }

    public byte[] toByteArray() {
        return ByteBuffer.allocate(8 + data.length)
                         .putInt(messageType)
                         .putInt(data.length)
                         .put(data)
                         .array();
    }

    public int getMessageType() {
        return messageType;
    }

    public byte[] getData() {
        return data;
    }

    public String getDataAsString() {
        return new String(getData());
    }
}
