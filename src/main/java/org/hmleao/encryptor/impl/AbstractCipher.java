package org.hmleao.encryptor.impl;

import org.hmleao.encryptor.Cipher;

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
