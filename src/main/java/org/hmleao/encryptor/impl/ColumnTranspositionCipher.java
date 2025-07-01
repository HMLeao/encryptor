package org.hmleao.encryptor.impl;

import org.hmleao.encryptor.Cipher;

public class ColumnTranspositionCipher extends AbstractCipher {

    public ColumnTranspositionCipher(Cipher cipher) {
        super(cipher);
    }

    @Override
    protected String doEncryption(String message) {
        return message + " _column_transposition_ ";
    }
}
