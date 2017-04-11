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
import java.util.Arrays;

/**
 * Created by George on 2017-04-10.
 */
public class TEAEncryptedSource implements InputOutputSource {
    private InputOutputSource wrappedInputOutputSource;
    private TEAEncryption teaEncryption;

    private BigInteger prime = new BigInteger("127244977682723949038442288383458812328116211364277879182445017375597033349853321341570665615304629040651065575774115632200923685184503100150837369400505801591918639510479151078433700137006780959646459461731785391194143831233779335307758972188096747365128835167533091994529701290267946017563976549120567354619");
    private BigInteger generator = new BigInteger("102481413954800864366127289708892158120592981843836207743638447810134782978131002432698306999542491076256460442577828420233726454847502196218483949556518929338842587906865051040036648462338468584929304792856884310316782073514136445510320405073592379215309409685483718420057963205893681857336023689144342100686");

    private byte[] sharedSecretKey;

    public TEAEncryptedSource(InputOutputSource wrappedInputOutputSource) {
        this.wrappedInputOutputSource = wrappedInputOutputSource;
        this.teaEncryption = new TEAEncryption();

        System.loadLibrary("TeaEncryption");
        executeHandShake();
    }

    @Override
    public byte[] read(int total) {
        byte[] data = wrappedInputOutputSource.read(total);
        System.out.println(Arrays.toString(data));
        teaEncryption.decrypt(data, sharedSecretKey);
        System.out.println(Arrays.toString(data));
        return data;
    }

    @Override
    public void write(byte[] data) {
        System.out.println(Arrays.toString(data));
        teaEncryption.encrypt(data, sharedSecretKey);
        System.out.println(Arrays.toString(data));
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
