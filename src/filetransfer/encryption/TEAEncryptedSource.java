package filetransfer.encryption;

import filetransfer.InputOutputSource;
import filetransfer.encryption.exceptions.HandshakeFailedException;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.DHParameterSpec;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by George on 2017-04-10.
 */
public class TEAEncryptedSource implements InputOutputSource {
    private InputOutputSource wrappedInputOutputSource;
    private TEAEncryption teaEncryption;

    private BigInteger prime = new BigInteger("347657326507365037256023752350320465723");
    private BigInteger generator = new BigInteger("2538274682136812");

    private byte[] sharedSecretKey;

    public TEAEncryptedSource(InputOutputSource wrappedInputOutputSource) {
        this.wrappedInputOutputSource = wrappedInputOutputSource;
        this.teaEncryption = new TEAEncryption();
        executeHandShake();
    }

    @Override
    public byte[] read(int total) {
        byte[] data = wrappedInputOutputSource.read(total);
        teaEncryption.decrypt(data, sharedSecretKey);
        return data;
    }

    @Override
    public void write(byte[] data) {
        teaEncryption.encrypt(data, sharedSecretKey);
        wrappedInputOutputSource.write(data);
    }

    @Override
    public void close() {
        wrappedInputOutputSource.close();
    }

    private void executeHandShake() {
        try {
            executeDiffieHellmanKeyExchange();
        } catch (Exception e) {
            throw new HandshakeFailedException();
        }
    }

    // From: http://exampledepot.8waytrips.com/egs/javax.crypto/KeyAgree.html
    private void executeDiffieHellmanKeyExchange() throws Exception {
        KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("DH");
        DHParameterSpec shSpec = new DHParameterSpec(prime, generator);
        keyGenerator.initialize(shSpec);

        KeyPair keyPair = keyGenerator.generateKeyPair();

        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        byte[] rawPublicKey = publicKey.getEncoded();
        sendPublicKey(rawPublicKey);

        byte[] otherRawPublicKey = receivePublicKey();
        PublicKey otherPublicKey = getPublicKeyFrom(otherRawPublicKey);

        KeyAgreement ka = KeyAgreement.getInstance("DH");
        ka.init(privateKey);
        ka.doPhase(otherPublicKey, true);

        SecretKey secretKey = ka.generateSecret("DES");
        sharedSecretKey = secretKey.getEncoded();
    }

    private PublicKey getPublicKeyFrom(byte[] rawPublicKey) throws Exception {
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(rawPublicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("DH");
        return keyFactory.generatePublic(x509KeySpec);
    }

    private void sendPublicKey(byte[] rawPublicKey) {
        byte[] keyExchangeMessage = buildKeyExchangeMessage(rawPublicKey);
        wrappedInputOutputSource.write(keyExchangeMessage);
    }

    private byte[] receivePublicKey() {
        byte[] rawKeyLength = wrappedInputOutputSource.read(4);
        int keyLength = ByteBuffer.wrap(rawKeyLength).getInt();
        return wrappedInputOutputSource.read(keyLength);
    }

    private byte[] buildKeyExchangeMessage(byte[] rawPublicKey) {
        return ByteBuffer.allocate(4 + rawPublicKey.length)
                         .putInt(rawPublicKey.length)
                         .put(rawPublicKey)
                         .array();
    }
}
