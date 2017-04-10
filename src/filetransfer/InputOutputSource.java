package filetransfer;

/**
 * Created by George on 2017-04-09.
 */
public interface InputOutputSource {
    byte[] read(int total);
    void write(byte[] data);
    void close();
}
