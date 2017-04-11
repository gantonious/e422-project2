package filetransfer.protocol;

import filetransfer.InputOutputSource;
import filetransfer.protocol.messages.Message;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Created by George on 2017-04-09.
 */
public class FileTransferService {
    private InputOutputSource inputOutputSource;

    public FileTransferService(InputOutputSource inputOutputSource) {
        this.inputOutputSource = inputOutputSource;
    }

    public Message readMessage() {
        byte[] rawMessageHeader = inputOutputSource.read(8);
        byte[] rawMessageType = { rawMessageHeader[0], rawMessageHeader[1], rawMessageHeader[2], rawMessageHeader[3] };
        byte[] rawMessageLength = { rawMessageHeader[4], rawMessageHeader[5], rawMessageHeader[6], rawMessageHeader[7] };

        int messageType = ByteBuffer.wrap(rawMessageType).getInt();
        int messageLength = ByteBuffer.wrap(rawMessageLength).getInt();
        int paddedMessageLength = roundToNextMultipleOf8(messageLength);

        byte[] paddedRawMessageData = inputOutputSource.read(paddedMessageLength);
        byte[] rawMessageData = new byte[messageLength];

        System.arraycopy(paddedRawMessageData, 0, rawMessageData, 0, messageLength);

        return new Message(messageType, rawMessageData);
    }

    private int roundToNextMultipleOf8(int length) {
        int paddingSize = 8 - length % 8;
        return paddingSize == 8 ? length : length + paddingSize;
    }

    public void sendMessage(Message message) {
        System.out.println(Arrays.toString(message.getData()));
        inputOutputSource.write(message.toByteArray());
    }

    public void close() {
        inputOutputSource.close();
    }
}
