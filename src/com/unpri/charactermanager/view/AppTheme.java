
package com.unpri.charactermanager.view;

import com.unpri.charactermanager.model.CharacterType;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Font;

public class AppTheme {

    // COLORS
    public static final Color BACKGROUND =
            new Color(25, 29, 45);

    public static final Color SURFACE =
            new Color(39, 45, 65);

    public static final Color TEXT =
            new Color(245, 245, 245);

    public static final Color MUTED_TEXT =
            new Color(170, 180, 195);

    public static final Color GOLD =
            new Color(255, 195, 75);

    public static final Color ACCENT =
            new Color(80, 150, 220);

    // ROLE COLORS
    public static final Color WARRIOR =
            new Color(130, 65, 65);

    public static final Color MAGE =
            new Color(85, 70, 145);

    public static final Color ARCHER =
            new Color(55, 120, 85);

    public static final Color ASSASSIN =
            new Color(115, 75, 125);

    public static final Color HEALER =
            new Color(55, 115, 130);

    // FONTS
    public static final Font TITLE_FONT =
            new Font("SansSerif", Font.BOLD, 25);

    public static final Font SECTION_FONT =
            new Font("SansSerif", Font.BOLD, 16);

    public static final Font BODY_FONT =
            new Font("SansSerif", Font.PLAIN, 14);

    public static final Font SMALL_FONT =
            new Font("SansSerif", Font.PLAIN, 12);

    // BORDER
    public static final Border PANEL_BORDER =
            BorderFactory.createLineBorder(
                    new Color(75, 85, 110), 1
            );

    // COLOR BASED ON CHARACTER ROLE
    public static Color colorFor(CharacterType type) {

        switch (type) {

            case WARRIOR:
                return WARRIOR;

            case MAGE:
                return MAGE;

            case ARCHER:
                return ARCHER;

            case ASSASSIN:
                return ASSASSIN;

            case HEALER:
                return HEALER;

            default:
                return SURFACE;
        }
    }
}
