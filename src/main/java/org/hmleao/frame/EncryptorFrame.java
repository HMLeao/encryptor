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

    JTextArea textAreaInput = new JTextArea(5, 30);
    JTextArea textAreaOutput = new JTextArea(5, 30);
    JTextField transpositionKeyField = new JTextField(10);

    JSpinner cesarSpinner;
    JSpinner railFenceSpinner;

    private boolean hasCesarCipher = false;
    private boolean hasRailFenceCipher = false;
    private boolean hasTranspositionCipher = false;

    public EncryptorFrame() {
        super("Criptografar mensagem");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        final var itemListener = new CheckboxHandler();

        cesarSpinner = new JSpinner(new SpinnerListModel(new String[]{"1", "2", "3", "4", "5"}));
        railFenceSpinner = new JSpinner(new SpinnerListModel(new String[]{"3", "4", "5", "6"}));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Mensagem"));
        textAreaInput.setLineWrap(true);
        textAreaInput.setWrapStyleWord(true);
        inputPanel.add(new JScrollPane(textAreaInput), BorderLayout.CENTER);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(3, 2, 5, 5));
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Parâmetros de criptografia"));

        cesarCheckBox.addItemListener(itemListener);
        railFenceCheckBox.addItemListener(itemListener);
        transpositionCheckBox.addItemListener(itemListener);

        optionsPanel.add(cesarCheckBox);
        optionsPanel.add(cesarSpinner);
        optionsPanel.add(railFenceCheckBox);
        optionsPanel.add(railFenceSpinner);
        optionsPanel.add(transpositionCheckBox);
        optionsPanel.add(transpositionKeyField);


        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Mensagem criptografada"));
        textAreaOutput.setLineWrap(true);
        textAreaOutput.setWrapStyleWord(true);
        textAreaOutput.setEditable(false);
        outputPanel.add(new JScrollPane(textAreaOutput), BorderLayout.CENTER);

        // Button
        JButton encryptButton = new JButton("Criptografar");
        encryptButton.addActionListener(itemListener);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(encryptButton);

        // Add all panels to main layout
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(inputPanel);
        centerPanel.add(optionsPanel);
        centerPanel.add(outputPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private class CheckboxHandler implements ItemListener, ActionListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getItem() instanceof JCheckBox checkBox) {
                String text = checkBox.getText();
                boolean isSelected = checkBox.isSelected();
                switch (text) {
                    case CESAR_TEXT -> hasCesarCipher = isSelected;
                    case RAIL_FENCE_TEXT -> hasRailFenceCipher = isSelected;
                    case TRANSPOSITION_TEXT -> hasTranspositionCipher = isSelected;
                }
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
                int rails = Integer.parseInt((String) railFenceSpinner.getValue());
                cipher = new RailFenceCipher(cipher, rails);
            }
            if (hasTranspositionCipher) {
                String key = transpositionKeyField.getText().trim();
                if (!key.matches("\\d+")) {
                    JOptionPane.showMessageDialog(null,
                            "A chave de transposição deve conter apenas dígitos (ex: 31425)",
                            "Chave inválida",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    cipher = new ColumnTranspositionCipher(cipher, key);
                }
            }
            return cipher;
        }
    }
}
