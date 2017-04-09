package filetransfer.protocol;

import filetransfer.InputOutputSource;
import filetransfer.protocol.messages.Message;

import java.nio.ByteBuffer;

/**
 * Created by George on 2017-04-09.
 */
public class FileTransferService {
    private InputOutputSource inputOutputSource;

    public FileTransferService(InputOutputSource inputOutputSource) {
        this.inputOutputSource = inputOutputSource;
    }

    public Message readMessage() {
        byte[] rawMessageType = inputOutputSource.read(4);
        byte[] rawMessageLength = inputOutputSource.read(4);

        int messageType = ByteBuffer.wrap(rawMessageType).getInt();
        int messageLength = ByteBuffer.wrap(rawMessageLength).getInt();

        byte[] rawMessageData = inputOutputSource.read(messageLength);

        return new Message(messageType, rawMessageData);
    }

    public void sendMessage(Message message) {
        inputOutputSource.write(message.toByteArray());
    }
}
