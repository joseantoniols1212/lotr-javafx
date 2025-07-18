package com.bosonit.project.lotrjfx.model.services;

import com.bosonit.project.lotrjfx.model.exceptions.InvalidInput;
import com.bosonit.project.lotrjfx.model.characters.beasts.Beast;
import com.bosonit.project.lotrjfx.model.characters.beasts.BeastClass;
import com.bosonit.project.lotrjfx.model.characters.heroes.*;

import java.util.Arrays;

public class CharacterServiceImpl implements CharacterService {
    public Beast createBeast(String className, String name, String healthText, String armorText) throws InvalidInput {
        validateNonEmptyInput("All fields must be non empty", className, name, healthText, armorText);

        int health = parsePositiveInt("Health must be a positive integer", healthText);
        int armor = parsePositiveInt("Armor must be a positive integer", armorText);
        BeastClass beastClass = BeastClass.valueOf(className);

        return CharacterFactory.createBeast(beastClass, name, health, armor);
    }

    public Hero createHero(String className, String name, String healthText, String armorText) throws InvalidInput {
        validateNonEmptyInput("All fields must be non empty", className, name, healthText, armorText);

        int health = parsePositiveInt("Health must be a positive integer", healthText);
        int armor = parsePositiveInt("Armor must be a positive integer", armorText);
        HeroClass heroClass = HeroClass.valueOf(className);

        return CharacterFactory.createHero(heroClass, name, health, armor);
    }

    private void validateNonEmptyInput(String message, String... values) throws InvalidInput {
        if (Arrays.stream(values).anyMatch(e -> e == null || e.isBlank())) {
            throw new InvalidInput(message);
        }
    }

    private int parsePositiveInt(String message, String value) throws InvalidInput {
        try {
            int parsedValue = Integer.parseInt(value);
            if (parsedValue <= 0) throw new InvalidInput(message);
            return parsedValue;
        } catch (NumberFormatException e) {
            throw new InvalidInput(message);
        }
    }
}