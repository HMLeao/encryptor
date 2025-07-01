package org.hmleao.encryptor.impl;

import org.hmleao.encryptor.AbstractCipher;
import org.hmleao.encryptor.Cipher;

public class RailFenceCipher extends AbstractCipher {

    public RailFenceCipher(Cipher cipher) {
        super(cipher);
    }

    @Override
    protected String doEncryption(String message) {
        return message + " _rail_fence_ ";
    }
}
