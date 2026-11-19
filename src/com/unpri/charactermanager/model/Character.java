
package com.unpri.charactermanager.model;

public class Character {

    private String name;
    private CharacterType type;
    private int level;

    public Character(String name, CharacterType type, int level) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Nama karakter tidak boleh kosong."
            );
        }

        name = name.trim();

        if (name.length() > 20) {
            throw new IllegalArgumentException(
                "Nama maksimal 20 karakter."
            );
        }

        if (type == null) {
            throw new IllegalArgumentException(
                "Role karakter harus dipilih."
            );
        }

        if (level < 1 || level > 99) {
            throw new IllegalArgumentException(
                "Level harus antara 1 sampai 99."
            );
        }

        if (type == CharacterType.MAGE && level > 60) {
            throw new IllegalArgumentException(
                "Karakter MAGE tidak boleh memiliki level di atas 60."
            );
        }

        this.name = name;
        this.type = type;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public CharacterType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }
}
