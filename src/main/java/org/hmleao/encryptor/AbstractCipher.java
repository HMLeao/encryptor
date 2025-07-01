package org.hmleao.encryptor;

public abstract class AbstractCipher implements Cipher {

    private final Cipher cipher;

    protected AbstractCipher(Cipher cipher) {
        this.cipher = cipher;
    }

    protected abstract String doEncryption(String message);

    public String encrypt(String message) {
        var encryptedMessage = doEncryption(message);
        if (cipher == null) {
            return encryptedMessage;
        }
        return cipher.encrypt(encryptedMessage);
    }
}
