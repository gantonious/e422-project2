package filetransfer.encryption;

/**
 * Created by George on 2017-04-10.
 */
public class TEAEncryption {
    native public void encrypt(byte[] data, byte[] key);
    native public void decrypt(byte[] data, byte[] keyg);
}
