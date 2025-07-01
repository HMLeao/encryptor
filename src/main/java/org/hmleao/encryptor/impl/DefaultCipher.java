package org.hmleao.encryptor.impl;

import org.hmleao.encryptor.Cipher;

public class DefaultCipher implements Cipher {
    @Override
    public String encrypt(String message) {
        return message;
    }
}
