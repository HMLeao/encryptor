package org.hmleao.encryptor.impl;

import org.hmleao.encryptor.AbstractCipher;
import org.hmleao.encryptor.Cipher;

public class CesarCipher extends AbstractCipher {

    private final int siftValue;

    public CesarCipher(Cipher cipher, int shiftValue) {
        super(cipher);
        this.siftValue = shiftValue;
    }

    @Override
    public String doEncryption(String message) {
        StringBuilder result = new StringBuilder();
        var charArray = message.toCharArray();
        for (char c : charArray) {
            switch (c) {
                case ' ', ',', '.', '-' -> result.append(c);
                default -> {
                    int originalPosition = c - 'a';
                    int newPosition = (originalPosition + siftValue) % 26;
                    char newCharacter = (char) ('a' + newPosition);
                    result.append(newCharacter);
                }
            }
        }
        return result.toString();
    }
}
