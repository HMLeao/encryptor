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

    JTextArea textAreaInput = new JTextArea(10, 30);
    JTextArea textAreaOutput = new JTextArea(10, 30);

    JSpinner cesarSpinner;
    JSpinner railFenceSpinner;

    private boolean hasCesarCipher = false;
    private boolean hasRailFenceCipher = false;
    private boolean hasTranspositionCipher = false;

    public EncryptorFrame() {
        super("Criptografar uma mensagem");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350,500);
        setLayout( new FlowLayout() );

        final var itemListenner = new CheckboxHandler();

        cesarCheckBox.addItemListener(itemListenner);
        railFenceCheckBox.addItemListener(itemListenner);
        transpositionCheckBox.addItemListener(itemListenner);

        textAreaInput.setEditable(true);
        textAreaInput.setLineWrap(true);

        textAreaOutput.setEditable(false);
        textAreaOutput.setLineWrap(true);

        String[] cesarShiftValues = new String[] {"1", "2", "3", "4", "5"};
        SpinnerListModel cesarShiftValuesModel = new SpinnerListModel(cesarShiftValues);
        cesarSpinner = new JSpinner(cesarShiftValuesModel);


        String[] numOfRailsValues = new String[] {"3", "4", "5", "6"};
        SpinnerListModel numOfRailsModel = new SpinnerListModel(numOfRailsValues);
        railFenceSpinner = new JSpinner(numOfRailsModel);

        add(textAreaInput);
        add(textAreaOutput);
        add(cesarCheckBox);
        add(cesarSpinner);
        add(transpositionCheckBox);
        add(railFenceCheckBox);
        add(railFenceSpinner);

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
            textAreaOutput.setText(cipher.encrypt(textAreaInput.getText()));
        }

        private Cipher getCipher() {
            Cipher cipher = new DefaultCipher();
            if (hasCesarCipher) {
                int shiftValue = Integer.parseInt((String) cesarSpinner.getValue());
                cipher = new CesarCipher(cipher, shiftValue);
            }
            if (hasRailFenceCipher) {
                int numOfRails = Integer.parseInt((String) railFenceSpinner.getValue());
                cipher = new RailFenceCipher(cipher, numOfRails);
            }
            if (hasTranspositionCipher) {
                cipher = new ColumnTranspositionCipher(cipher, "31425");
            }
            return cipher;
        }
    }
}
