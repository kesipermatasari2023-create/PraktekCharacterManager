
package com.unpri.charactermanager.view;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class MainWindow extends JFrame {

    private final CharacterManager manager;

    private JPanel cardsPanel;
    private JPanel statisticsPanel;

    private JLabel countLabel;
    private JLabel averageLabel;
    private JLabel highestLabel;
    private JLabel roleLabel;

    public MainWindow(CharacterManager manager) {

        this.manager = manager;

        setTitle("Character Manager - TP12");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout(15, 15));
        root.setBackground(AppTheme.BACKGROUND);
        root.setBorder(new EmptyBorder(20, 20, 15, 20));
        root.setPreferredSize(new Dimension(1050, 650));

        // NORTH: Header dan statistik
        root.add(createHeader(), BorderLayout.NORTH);

        // CENTER: Daftar karakter
        root.add(createCharacterList(), BorderLayout.CENTER);

        // EAST: Legenda role
        root.add(createLegend(), BorderLayout.EAST);

        // SOUTH: Status bar
        root.add(createStatusBar(), BorderLayout.SOUTH);

        setContentPane(root);

        pack();
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(650, 450));
    }

    // ==============================
    // HEADER
    // ==============================

    private JPanel createHeader() {

        JPanel header = new JPanel(new BorderLayout(10, 15));
        header.setBackground(AppTheme.BACKGROUND);

        JLabel title = new JLabel("CHARACTER MANAGER");
        title.setFont(AppTheme.TITLE_FONT);
        title.setForeground(AppTheme.GOLD);

        JLabel subtitle = new JLabel(
                "Manage your heroes and adventures"
        );
        subtitle.setFont(AppTheme.BODY_FONT);
        subtitle.setForeground(AppTheme.MUTED_TEXT);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(AppTheme.BACKGROUND);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(5));
        titlePanel.add(subtitle);

        header.add(titlePanel, BorderLayout.NORTH);

        // Panel statistik tambahan: GridLayout 2x2
        statisticsPanel = new JPanel(new GridLayout(2, 2, 10, 8));
        statisticsPanel.setBackground(AppTheme.BACKGROUND);

        countLabel = new JLabel();
        averageLabel = new JLabel();
        highestLabel = new JLabel();
        roleLabel = new JLabel();

        statisticsPanel.add(createStatCard("TOTAL CHARACTERS", countLabel));
        statisticsPanel.add(createStatCard("AVERAGE LEVEL", averageLabel));
        statisticsPanel.add(createStatCard("HIGHEST LEVEL", highestLabel));
        statisticsPanel.add(createStatCard("ROLE TYPES", roleLabel));

        header.add(statisticsPanel, BorderLayout.SOUTH);

        updateStatistics();

        return header;
    }

    private JPanel createStatCard(String title, JLabel valueLabel) {

        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(AppTheme.SURFACE);
        card.setBorder(AppTheme.PANEL_BORDER);
        card.setOpaque(true);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(AppTheme.SMALL_FONT);
        titleLabel.setForeground(AppTheme.MUTED_TEXT);

        valueLabel.setFont(AppTheme.SECTION_FONT);
        valueLabel.setForeground(AppTheme.GOLD);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    // ==============================
    // STATISTICS
    // ==============================

    private void updateStatistics() {

        List<Character> characters = manager.getAllCharacters();

        int total = characters.size();
        int totalLevel = 0;
        int highest = 0;

        for (Character character : characters) {
            totalLevel += character.getLevel();

            if (character.getLevel() > highest) {
                highest = character.getLevel();
            }
        }

        double average = total > 0
                ? (double) totalLevel / total
                : 0;

        countLabel.setText(String.valueOf(total));
        averageLabel.setText(String.format("%.1f", average));
        highestLabel.setText(String.valueOf(highest));
        roleLabel.setText(String.valueOf(CharacterType.values().length));
    }

    // ==============================
    // CHARACTER LIST
    // ==============================

    private JScrollPane createCharacterList() {

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(AppTheme.BACKGROUND);

        cardsPanel = new JPanel(new GridLayout(0, 2, 12, 12));
        cardsPanel.setBackground(AppTheme.BACKGROUND);
        cardsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Kartu dimasukkan ke panel grid
        for (Character character : manager.getAllCharacters()) {
            cardsPanel.add(createCharacterCard(character));
        }

        // Grid ditempatkan di NORTH panel pembungkus
        wrapper.add(cardsPanel, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(wrapper);
        scroll.setPreferredSize(new Dimension(680, 400));
        scroll.getViewport().setBackground(AppTheme.BACKGROUND);

        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        return scroll;
    }

    private JPanel createCharacterCard(Character character) {

        JPanel card = new JPanel(new BorderLayout(10, 8));

        card.setBackground(AppTheme.colorFor(character.getType()));
        card.setOpaque(true);
        card.setBorder(AppTheme.PANEL_BORDER);

        JLabel name = new JLabel(character.getName());
        name.setFont(AppTheme.SECTION_FONT);
        name.setForeground(AppTheme.TEXT);

        JLabel level = new JLabel(
                "Level " + character.getLevel()
        );
        level.setFont(AppTheme.BODY_FONT);
        level.setForeground(AppTheme.TEXT);

        JLabel type = new JLabel(
                character.getType().toString()
        );
        type.setFont(AppTheme.SMALL_FONT);
        type.setForeground(AppTheme.TEXT);

        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.setBackground(AppTheme.colorFor(character.getType()));

        details.add(name);
        details.add(Box.createVerticalStrut(7));
        details.add(level);
        details.add(Box.createVerticalStrut(4));
        details.add(type);

        card.add(details, BorderLayout.CENTER);

        return card;
    }

    // ==============================
    // LEGEND
    // ==============================

    private JPanel createLegend() {

        JPanel legendPanel = new JPanel();

        // Target BoxLayout harus legendPanel sendiri
        legendPanel.setLayout(
                new BoxLayout(legendPanel, BoxLayout.Y_AXIS)
        );

        legendPanel.setBackground(AppTheme.SURFACE);
        legendPanel.setBorder(AppTheme.PANEL_BORDER);

        JLabel title = new JLabel("ROLE LEGEND");
        title.setFont(AppTheme.SECTION_FONT);
        title.setForeground(AppTheme.GOLD);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        legendPanel.add(title);
        legendPanel.add(Box.createVerticalStrut(20));

        // Legenda otomatis dari enum
        for (CharacterType type : CharacterType.values()) {

            JLabel role = new JLabel(type.toString());

            role.setFont(AppTheme.BODY_FONT);
            role.setForeground(AppTheme.TEXT);
            role.setAlignmentX(Component.LEFT_ALIGNMENT);

            legendPanel.add(role);

            legendPanel.add(Box.createVerticalStrut(12));
        }

        // Mengisi ruang kosong
        legendPanel.add(Box.createVerticalGlue());

        legendPanel.setPreferredSize(new Dimension(190, 300));

        return legendPanel;
    }

    // ==============================
    // STATUS BAR
    // ==============================

    private JPanel createStatusBar() {

        JPanel statusBar = new JPanel(new BorderLayout());

        statusBar.setBackground(AppTheme.SURFACE);
        statusBar.setBorder(AppTheme.PANEL_BORDER);

        JLabel status = new JLabel(
                " Character Manager | Total: "
                + manager.getAllCharacters().size()
                + " characters"
        );

        status.setFont(AppTheme.SMALL_FONT);
        status.setForeground(AppTheme.MUTED_TEXT);

        JLabel version = new JLabel("v0.5 ");

        version.setFont(AppTheme.SMALL_FONT);
        version.setForeground(AppTheme.GOLD);

        statusBar.add(status, BorderLayout.WEST);
        statusBar.add(version, BorderLayout.EAST);

        return statusBar;
    }
}
