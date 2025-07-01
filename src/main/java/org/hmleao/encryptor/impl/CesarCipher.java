package org.hmleao.encryptor.impl;

import org.hmleao.encryptor.AbstractCipher;
import org.hmleao.encryptor.Cipher;

public class CesarCipher extends AbstractCipher {

    public CesarCipher(Cipher cipher) {
        super(cipher);
    }

    @Override
    public String doEncryption(String message) {
        return message + " _cesar_ ";
    }
}
