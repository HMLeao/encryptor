package org.hmleao.frame;

import org.hmleao.encryptor.Cipher;
import org.hmleao.encryptor.impl.CesarCipher;
import org.hmleao.encryptor.impl.ColumnTranspositionCipher;
import org.hmleao.encryptor.impl.DefaultCipher;
import org.hmleao.encryptor.impl.RailFenceCipher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class EncryptorFrame extends JFrame {
    private static final String CESAR_TEXT = "Cesar";
    private static final String RAIL_FENCE_TEXT = "Rail Fence";
    private static final String TRANSPOSITION_TEXT = "Column Transposition";

    JCheckBox cesarCheckBox = new JCheckBox(CESAR_TEXT);
    JCheckBox transpositionCheckBox = new JCheckBox(TRANSPOSITION_TEXT);
    JCheckBox railFenceCheckBox = new JCheckBox(RAIL_FENCE_TEXT);

    private boolean hasCesarCipher = false;
    private boolean hasRailFenceCipher = false;
    private boolean hasTranspositionCipher = false;

    public EncryptorFrame() {
        super("Criptografar uma mensagem");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,300);
        setLayout( new FlowLayout() );

        final var itemListenner = new CheckboxHandler();

        cesarCheckBox.addItemListener(itemListenner);
        railFenceCheckBox.addItemListener(itemListenner);
        transpositionCheckBox.addItemListener(itemListenner);

        final var textField = new JTextField(100);
        textField.setEditable(true);

        add(textField);
        add(cesarCheckBox);
        add(transpositionCheckBox);
        add(railFenceCheckBox);

        var button = new JButton("Criptografar");
        button.addActionListener(itemListenner);

        add(button);

        setVisible(true);
    }

    private class CheckboxHandler implements ItemListener, ActionListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getItem() instanceof JCheckBox checkBox) {
                var checkBoxText = checkBox.getText();
                if (checkBox.isSelected()) {
                    checkItems(checkBoxText);
                } else {
                    uncheckItems(checkBoxText);
                }
            }
        }

        private void checkItems(String checkBoxText) {
            switch (checkBoxText) {
                case CESAR_TEXT -> hasCesarCipher = true;
                case RAIL_FENCE_TEXT -> hasRailFenceCipher = true;
                case TRANSPOSITION_TEXT -> hasTranspositionCipher = true;
            }
        }

        private void uncheckItems(String checkBoxText) {
            switch (checkBoxText) {
                case CESAR_TEXT -> hasCesarCipher = false;
                case RAIL_FENCE_TEXT -> hasRailFenceCipher = false;
                case TRANSPOSITION_TEXT -> hasTranspositionCipher = false;
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Cipher cipher = getCipher();
            System.out.println(cipher.encrypt("mensagem para criptografar com"));
        }

        private Cipher getCipher() {
            Cipher cipher = new DefaultCipher();
            if (hasCesarCipher) {
                cipher = new CesarCipher(cipher);
            }
            if (hasRailFenceCipher) {
                cipher = new RailFenceCipher(cipher);
            }
            if (hasTranspositionCipher) {
                cipher = new ColumnTranspositionCipher(cipher);
            }
            return cipher;
        }
    }
}
