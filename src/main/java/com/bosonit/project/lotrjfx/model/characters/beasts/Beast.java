package com.bosonit.project.lotrjfx.model.characters.beasts;

import com.bosonit.project.lotrjfx.model.exceptions.InvalidDuel;
import com.bosonit.project.lotrjfx.model.characters.Character;
import com.bosonit.project.lotrjfx.model.characters.CharacterClass;
import com.bosonit.project.lotrjfx.model.characters.heroes.HeroClass;

public class Beast extends Character implements Cloneable {

    public static final int MIN_HEALTH_BEAST = 130;
    public static final int MAX_HEALTH_BEAST = 180;

    public static final int MIN_ARMOR_BEAST = 50;
    public static final int MAX_ARMOR_BEAST = 60;

    private final BeastClass beastClass;

    public Beast(BeastClass beastClass, String name, int health, int armor) {
        super(name, health, armor);
        this.beastClass = beastClass;
    }

    @Override
    public int getAttackPower() {
        return this.throwDice(0, 90);
    }

    @Override
    public int getExtraAttackPower(CharacterClass opponentClass) {
        return switch (this.beastClass) {
            case ORC, GOBLIN -> switch (opponentClass) {
                case HeroClass _ -> 0;
                case BeastClass _ -> throw new InvalidDuel(this.beastClass + " beast class can't fight against " + opponentClass);
            };
        };
    }

    @Override
    public int getExtraArmor(CharacterClass opponentClass) {
        return switch (this.beastClass) {
            case ORC, GOBLIN -> switch (opponentClass) {
                case HeroClass _ -> 0;
                case BeastClass _ -> throw new InvalidDuel(this.beastClass + " beast class can't fight against " + opponentClass);
            };
        };
    }

    public BeastClass getBeastClass() {
        return beastClass;
    }

    @Override
    public String toString() {
        return beastClass+"{" +
                "name=" + getName() + " " +
                "armor=" + getArmor() + " " +
                "health=" + getHealth() + " " +
                '}';
    }

    @Override
    public Beast clone() {
        try {
            Beast clone = (Beast) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
