
package com.unpri.charactermanager.view;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class AppTheme {

    public static final Color BACKGROUND =
        new Color(245, 247, 250);

    public static final Color SURFACE =
        Color.WHITE;

    public static final Color TEXT =
        new Color(35, 45, 60);

    public static final Color MUTED_TEXT =
        new Color(120, 130, 145);

    public static final Color GOLD =
        new Color(230, 170, 50);

    public static final Color ACCENT =
        new Color(55, 110, 200);

    public static final Color ERROR =
        new Color(210, 55, 55);

    public static final Font TITLE_FONT =
        new Font("SansSerif", Font.BOLD, 24);

    public static final Font SECTION_FONT =
        new Font("SansSerif", Font.BOLD, 16);

    public static final Font BODY_FONT =
        new Font("SansSerif", Font.PLAIN, 14);

    public static final Font SMALL_FONT =
        new Font("SansSerif", Font.PLAIN, 12);

    public static final Border CARD_BORDER =
        BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(
                new Color(220, 225, 235)
            ),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        );

    public static Color colorFor(
        com.unpri.charactermanager.model.CharacterType type
    ) {

        if (type == null) {
            return MUTED_TEXT;
        }

        switch (type) {
            case WARRIOR:
                return new Color(220, 90, 80);

            case ARCHER:
                return new Color(60, 160, 110);

            case ASSASSIN:
                return new Color(140, 90, 190);

            case MAGE:
                return new Color(65, 125, 210);

            default:
                return MUTED_TEXT;
        }
    }

    private AppTheme() {
    }
}
