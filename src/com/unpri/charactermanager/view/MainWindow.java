
package com.unpri.charactermanager.view;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MainWindow extends JFrame {

    private final CharacterManager manager;

    private JPanel cardsPanel;

    private JLabel countLabel;
    private JLabel averageLabel;
    private JLabel statusLabel;

    public MainWindow(CharacterManager manager) {

        this.manager = manager;

        setTitle("Character Manager - TP14");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));

        getContentPane().setBackground(
            AppTheme.BACKGROUND
        );

        createHeader();
        createCharacterList();
        createLegend();
        createStatusBar();

        refreshList();

        setMinimumSize(new Dimension(850, 550));
        pack();
        setLocationRelativeTo(null);
    }

    private void createHeader() {

        JPanel header = new JPanel(
            new BorderLayout(10, 10)
        );

        header.setBackground(AppTheme.BACKGROUND);
        header.setBorder(
            BorderFactory.createEmptyBorder(15, 15, 5, 15)
        );

        JLabel title = new JLabel("CHARACTER MANAGER");
        title.setFont(AppTheme.TITLE_FONT);
        title.setForeground(AppTheme.TEXT);

        JLabel subtitle = new JLabel(
            "Kelola karakter dan level mereka"
        );

        subtitle.setFont(AppTheme.SMALL_FONT);
        subtitle.setForeground(AppTheme.MUTED_TEXT);

        JPanel titlePanel = new JPanel(
            new GridLayout(2, 1)
        );

        titlePanel.setBackground(AppTheme.BACKGROUND);
        titlePanel.add(title);
        titlePanel.add(subtitle);

        CharacterFormPanel form =
            new CharacterFormPanel(
                manager,
                this::refreshList
            );

        header.add(titlePanel, BorderLayout.NORTH);
        header.add(form, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);
    }

    private void createCharacterList() {

        cardsPanel = new JPanel(
            new GridLayout(0, 1, 10, 10)
        );

        cardsPanel.setBackground(
            AppTheme.BACKGROUND
        );

        cardsPanel.setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        JScrollPane scroll = new JScrollPane(cardsPanel);

        scroll.setPreferredSize(
            new Dimension(570, 300)
        );

        scroll.setBorder(null);
        scroll.getViewport().setBackground(
            AppTheme.BACKGROUND
        );

        add(scroll, BorderLayout.CENTER);
    }

    private void createLegend() {

        JPanel legend = new JPanel();

        legend.setLayout(
            new BoxLayout(legend, BoxLayout.Y_AXIS)
        );

        legend.setBackground(AppTheme.SURFACE);

        legend.setBorder(
            BorderFactory.createCompoundBorder(
                AppTheme.CARD_BORDER,
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            )
        );

        JLabel title = new JLabel("ROLE LEGEND");
        title.setFont(AppTheme.SECTION_FONT);
        title.setForeground(AppTheme.TEXT);

        legend.add(title);
        legend.add(Box.createVerticalStrut(20));

        for (CharacterType type : CharacterType.values()) {

            JLabel item = new JLabel("●  " + type);
            item.setForeground(AppTheme.colorFor(type));
            item.setFont(AppTheme.BODY_FONT);

            legend.add(item);
            legend.add(Box.createVerticalStrut(15));
        }

        legend.add(Box.createVerticalGlue());

        JPanel wrapper = new JPanel(
            new FlowLayout(FlowLayout.CENTER)
        );

        wrapper.setBackground(AppTheme.BACKGROUND);
        wrapper.add(legend);

        add(wrapper, BorderLayout.EAST);
    }

    private void createStatusBar() {

        JPanel status = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 20, 8)
        );

        status.setBackground(AppTheme.SURFACE);

        countLabel = new JLabel("Total karakter: 0");
        averageLabel = new JLabel("Rata-rata level: 0");
        statusLabel = new JLabel("Siap digunakan");

        countLabel.setFont(AppTheme.BODY_FONT);
        averageLabel.setFont(AppTheme.BODY_FONT);
        statusLabel.setFont(AppTheme.SMALL_FONT);

        status.add(countLabel);
        status.add(averageLabel);
        status.add(statusLabel);

        add(status, BorderLayout.SOUTH);
    }

    public void refreshList() {

        cardsPanel.removeAll();

        for (Character character : manager.getCharacters()) {

            JPanel card = new JPanel(
                new BorderLayout(10, 5)
            );

            card.setBackground(AppTheme.SURFACE);
            card.setOpaque(true);

            card.setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(
                        0, 5, 0, 0,
                        AppTheme.colorFor(character.getType())
                    ),
                    AppTheme.CARD_BORDER
                )
            );

            JLabel name = new JLabel(
                character.getName()
            );

            name.setFont(AppTheme.SECTION_FONT);
            name.setForeground(AppTheme.TEXT);

            JLabel details = new JLabel(
                character.getType()
                + "  |  Level "
                + character.getLevel()
            );

            details.setFont(AppTheme.BODY_FONT);
            details.setForeground(
                AppTheme.colorFor(character.getType())
            );

            card.add(name, BorderLayout.NORTH);
            card.add(details, BorderLayout.CENTER);

            cardsPanel.add(card);
        }

        countLabel.setText(
            "Total karakter: " + manager.getCharacterCount()
        );

        averageLabel.setText(
            String.format(
                "Rata-rata level: %.2f",
                manager.getAverageLevel()
            )
        );

        statusLabel.setText("Daftar karakter diperbarui");

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }
}
