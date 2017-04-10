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
        int frameSize = 8 + data.length;
        int paddingLength = findPaddingLength(frameSize);

        return ByteBuffer.allocate(frameSize + paddingLength)
                         .putInt(messageType)
                         .putInt(data.length)
                         .put(data)
                         .put(new byte[paddingLength])
                         .array();
    }

    public int findPaddingLength(int length) {
        int paddingSize = 8 - length % 8;
        return paddingSize == 8 ? 0 : paddingSize;
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
