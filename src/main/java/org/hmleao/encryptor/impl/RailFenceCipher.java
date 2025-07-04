package org.hmleao.encryptor.impl;

import org.hmleao.encryptor.AbstractCipher;
import org.hmleao.encryptor.Cipher;

public class RailFenceCipher extends AbstractCipher {

    private final int numOfRails;

    public RailFenceCipher(Cipher cipher, int numOfRails) {
        super(cipher);
        this.numOfRails = numOfRails;
    }

    @Override
    protected String doEncryption(String message) {
        StringBuilder result = new StringBuilder();
        char[] messageArray = message.toCharArray();
        final char[][] railFence = new char[numOfRails][messageArray.length];
        int railCount = 0;
        boolean upCount = false;
        for (int i = 0; i < messageArray.length; i++) {
            char currentChar = messageArray[i];
            if (currentChar == ' ' || currentChar == ',' || currentChar == '.') continue;
            railFence[railCount][i] = messageArray[i];

            if (railCount == 0) {
                upCount = false;
            } else if (railCount == numOfRails - 1) {
                upCount = true;
            }

            if (upCount) {
                railCount--;
            } else {
                railCount++;
            }
        }
        for (int i = 0; i < numOfRails; i++) {
            for (int j = 0; j < messageArray.length; j++) {
                if (railFence[i][j] == '\u0000') continue;
                result.append(railFence[i][j]);
            }
            result.append(' ');
        }
        return result.toString();
    }
}
