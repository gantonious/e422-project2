package encryptedfiletransfer.transport;

import encryptedfiletransfer.InputOutputSource;

/**
 * Created by George on 2017-04-09.
 */
public class SocketSource implements InputOutputSource {
    @Override
    public byte[] read(int total) {
        return new byte[0];
    }

    @Override
    public void write(byte[] data) {

    }
}