package org.hmleao.encryptor.impl;

import org.hmleao.encryptor.AbstractCipher;
import org.hmleao.encryptor.Cipher;

import java.util.Arrays;

public class ColumnTranspositionCipher extends AbstractCipher {

    private final String key;

    public ColumnTranspositionCipher(Cipher cipher, String key) {
        super(cipher);
        this.key = key;
    }

    @Override
    protected String doEncryption(String message) {
        int numCols = key.length();
        int textLength = message.length();
        int numRows = (int) Math.ceil((double) textLength / numCols);
        String upperCasePlainText = message.toUpperCase();
        char[][] grid = new char[numRows][numCols];
        int index = 0;
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (index < textLength) {
                    grid[row][col] = upperCasePlainText.charAt(index++);
                } else {
                    grid[row][col] = 'X';
                }
            }
        }
        Integer[] keyOrder = getKeyOrder(key);
        StringBuilder cipherText = new StringBuilder();
        for (int colIndex : keyOrder) {
            for (int row = 0; row < numRows; row++) {
                cipherText.append(grid[row][colIndex]);
            }
        }

        return cipherText.toString();
    }


    private Integer[] getKeyOrder(String key) {
        int length = key.length();
        Integer[] order = new Integer[length];
        Character[] keyChars = new Character[length];

        for (int i = 0; i < length; i++) {
            keyChars[i] = key.charAt(i);
        }

        Character[] sortedKey = keyChars.clone();
        Arrays.sort(sortedKey);
        boolean[] used = new boolean[length];

        for (int i = 0; i < length; i++) {
            char current = sortedKey[i];
            for (int j = 0; j < length; j++) {
                if (!used[j] && keyChars[j] == current) {
                    order[i] = j;
                    used[j] = true;
                    break;
                }
            }
        }

        return order;
    }
}
