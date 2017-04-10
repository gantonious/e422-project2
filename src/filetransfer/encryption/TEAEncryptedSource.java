package filetransfer.encryption;

import filetransfer.InputOutputSource;

/**
 * Created by George on 2017-04-10.
 */
public class TEAEncryptedSource implements InputOutputSource {
    private InputOutputSource wrapedInputOutputSource;

    public TEAEncryptedSource(InputOutputSource wrapedInputOutputSource) {
        this.wrapedInputOutputSource = wrapedInputOutputSource;
    }

    @Override
    public byte[] read(int total) {
        return new byte[0];
    }

    @Override
    public void write(byte[] data) {

    }

    @Override
    public void close() {
        wrapedInputOutputSource.close();
    }
}
