
package com.unpri.charactermanager.view;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

public class CharacterFormPanel extends JPanel {

    private final CharacterManager manager;
    private final Runnable onCharacterAdded;

    private JTextField nameField;
    private JComboBox<CharacterType> roleCombo;
    private JSpinner levelSpinner;

    private JLabel errorLabel;

    private Border normalBorder;
    private Border errorBorder;

    public CharacterFormPanel(
        CharacterManager manager,
        Runnable onCharacterAdded
    ) {

        this.manager = manager;
        this.onCharacterAdded = onCharacterAdded;

        setLayout(new BorderLayout(10, 10));
        setBackground(AppTheme.SURFACE);

        setBorder(BorderFactory.createCompoundBorder(
            AppTheme.CARD_BORDER,
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        JLabel title = new JLabel("Tambah Karakter");
        title.setFont(AppTheme.SECTION_FONT);
        title.setForeground(AppTheme.TEXT);

        nameField = new JTextField(15);

        roleCombo = new JComboBox<>(
            CharacterType.values()
        );

        levelSpinner = new JSpinner(
            new SpinnerNumberModel(1, 1, 99, 1)
        );

        normalBorder = nameField.getBorder();

        errorBorder = BorderFactory.createLineBorder(
            AppTheme.ERROR, 2
        );

        JButton addButton = new JButton("Tambah");
        addButton.setFont(AppTheme.BODY_FONT);
        addButton.setBackground(AppTheme.ACCENT);
        addButton.setForeground(AppTheme.SURFACE);
        addButton.setFocusPainted(false);

        errorLabel = new JLabel(" ");
        errorLabel.setForeground(AppTheme.ERROR);
        errorLabel.setFont(AppTheme.SMALL_FONT);

        JPanel fieldsPanel = new JPanel(
            new GridLayout(2, 3, 10, 5)
        );

        fieldsPanel.setBackground(AppTheme.SURFACE);

        fieldsPanel.add(new JLabel("Nama"));
        fieldsPanel.add(new JLabel("Role"));
        fieldsPanel.add(new JLabel("Level (1-99)"));

        fieldsPanel.add(nameField);
        fieldsPanel.add(roleCombo);
        fieldsPanel.add(levelSpinner);

        JPanel topPanel = new JPanel(
            new BorderLayout(5, 10)
        );

        topPanel.setBackground(AppTheme.SURFACE);
        topPanel.add(title, BorderLayout.NORTH);
        topPanel.add(fieldsPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(
            new FlowLayout(FlowLayout.LEFT)
        );

        bottomPanel.setBackground(AppTheme.SURFACE);
        bottomPanel.add(addButton);

        JPanel messagePanel = new JPanel(
            new BorderLayout()
        );

        messagePanel.setBackground(AppTheme.SURFACE);
        messagePanel.add(errorLabel, BorderLayout.CENTER);

        JPanel footer = new JPanel(
            new BorderLayout(10, 0)
        );

        footer.setBackground(AppTheme.SURFACE);
        footer.add(bottomPanel, BorderLayout.WEST);
        footer.add(messagePanel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        addButton.addActionListener(e -> saveCharacter());

        nameField.addActionListener(e -> saveCharacter());
    }

    // Validasi nama: kelengkapan, panjang, format, keunikan
    private String validateName(String name) {

        if (name == null || name.isEmpty()) {
            return "Nama karakter wajib diisi.";
        }

        if (name.length() > 20) {
            return "Nama maksimal 20 karakter.";
        }

        if (!name.matches("[\\p{L}][\\p{L} .'-]*")) {
            return "Nama hanya boleh berisi huruf dan karakter nama.";
        }

        // Aturan tambahan pertama: tidak boleh dua spasi berurutan
        if (name.contains("  ")) {
            return "Nama tidak boleh mengandung dua spasi berurutan.";
        }

        if (manager.isNameTaken(name)) {
            return "Nama karakter sudah digunakan.";
        }

        return null;
    }

    private void saveCharacter() {

        try {

            String name = nameField.getText().trim();

            // Validasi nama
            String message = validateName(name);

            if (message != null) {
                showError(message);
                return;
            }

            // Memastikan nilai spinner sudah diperbarui
            levelSpinner.commitEdit();

            int level = ((Number)
                levelSpinner.getValue()).intValue();

            CharacterType role =
                (CharacterType) roleCombo.getSelectedItem();

            // Model melakukan validasi kembali
            Character character = new Character(
                name, role, level
            );

            manager.addCharacter(character);

            showSuccess("Karakter berhasil ditambahkan!");

            nameField.setText("");
            levelSpinner.setValue(1);
            roleCombo.setSelectedIndex(0);

            if (onCharacterAdded != null) {
                onCharacterAdded.run();
            }

            nameField.requestFocusInWindow();

        } catch (java.text.ParseException ex) {

            showError("Level harus berupa angka yang valid.");
            levelSpinner.requestFocusInWindow();

        } catch (IllegalArgumentException ex) {

            // Pesan berasal langsung dari Model
            showError(ex.getMessage());
            nameField.requestFocusInWindow();
        }
    }

    private void showError(String message) {

        errorLabel.setText(message);

        nameField.setBorder(errorBorder);
        nameField.requestFocusInWindow();
    }

    private void showSuccess(String message) {

        errorLabel.setForeground(
            new java.awt.Color(30, 140, 70)
        );

        errorLabel.setText(message);

        nameField.setBorder(normalBorder);
    }

}
