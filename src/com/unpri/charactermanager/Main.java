
package com.unpri.charactermanager;

import com.unpri.charactermanager.model.Character;
import com.unpri.charactermanager.model.CharacterManager;
import com.unpri.charactermanager.model.CharacterType;
import com.unpri.charactermanager.view.MainWindow;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            CharacterManager manager =
                new CharacterManager();

            // Data karakter awal
            manager.addCharacter(
                new Character("Alucard", CharacterType.WARRIOR, 10)
            );

            manager.addCharacter(
                new Character("Miya", CharacterType.ARCHER, 8)
            );

            manager.addCharacter(
                new Character("Saber", CharacterType.ASSASSIN, 12)
            );

            manager.addCharacter(
                new Character("Gusion", CharacterType.MAGE, 14)
            );

            manager.addCharacter(
                new Character("Fanny", CharacterType.ASSASSIN, 16)
            );

            MainWindow window =
                new MainWindow(manager);

            window.setVisible(true);
        });
    }
}
