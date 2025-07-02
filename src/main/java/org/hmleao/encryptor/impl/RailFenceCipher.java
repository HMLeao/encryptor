package org.hmleao.encryptor.impl;

import org.hmleao.encryptor.AbstractCipher;
import org.hmleao.encryptor.Cipher;

public class RailFenceCipher extends AbstractCipher {

    private final int numOfRails = 3;

    public RailFenceCipher(Cipher cipher) {
        super(cipher);
    }

    @Override
    protected String doEncryption(String message) {
        // rail fence cipher
        StringBuilder result = new StringBuilder();
        char[] messageArray = message.toCharArray();
        final char[][] railFence = new char[numOfRails][messageArray.length];
        int railCount = 0;
        boolean upCount = false; // true for counting upwards, false for downwards
        // filling the rail fence
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
        // reading from the filled rail fence
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
