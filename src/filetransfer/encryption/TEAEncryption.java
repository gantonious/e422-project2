package filetransfer.encryption;

/**
 * Created by George on 2017-04-10.
 */
public class TEAEncryption {
    public TEAEncryption() {
        System.loadLibrary("TeaEncryption");
    }

    native public void encrypt(byte[] data, byte[] key);
    native public void decrypt(byte[] data, byte[] key);
}
