package com.bosonit.project.lotrjfx.model.characters.heroes;

import com.bosonit.project.lotrjfx.exceptions.InvalidDuel;
import com.bosonit.project.lotrjfx.model.characters.Character;
import com.bosonit.project.lotrjfx.model.characters.CharacterClass;
import com.bosonit.project.lotrjfx.model.characters.beasts.BeastClass;
import lombok.Getter;

@Getter
public class Hero extends Character implements Cloneable {

    public static final int MIN_ARMOR_HERO = 35;
    public static final int MAX_ARMOR_HERO = 70;

    public static final int MIN_HEALTH_HERO = 100;
    public static final int MAX_HEALTH_HERO = 150;

    private final HeroClass heroClass;

    public Hero(HeroClass heroClass, String name, int health, int armor) {
        super(name, health, armor);
        this.heroClass = heroClass;
    }

    @Override
    public int getAttackPower() {
        return Math.max(
                this.throwDice(0, 100),
                this.throwDice(0, 100)
        );
    }

    @Override
    public int getExtraAttackPower(CharacterClass opponentClass) {
        return switch (this.heroClass) {
            case ELF -> switch (opponentClass) {
                case BeastClass.ORC -> 10;
                case BeastClass.GOBLIN -> 0;
                case HeroClass _ -> throw new InvalidDuel(this.heroClass + " hero class can't fight against " + opponentClass);
            };
            case HOBBIT -> switch (opponentClass) {
                case BeastClass.ORC -> 0;
                case BeastClass.GOBLIN -> -5;
                case HeroClass _ -> throw new InvalidDuel(this.heroClass + " hero class can't fight against " + opponentClass);
            };
            case HUMAN -> switch (opponentClass) {
                case BeastClass.ORC, BeastClass.GOBLIN -> 0;
                case HeroClass _ -> throw new InvalidDuel(this.heroClass + " hero class can't fight against " + opponentClass);
            };
        };
    }

    @Override
    public int getExtraArmor(CharacterClass opponentClass) {
        return switch (heroClass) {
            case ELF, HUMAN, HOBBIT -> switch (opponentClass) {
                case BeastClass.ORC -> -Math.floorDiv(getArmor(), 10);
                case BeastClass.GOBLIN -> 0;
                case HeroClass _ -> throw new InvalidDuel(this.heroClass + " hero class can't fight against " + opponentClass);
            };
        };
    }

    @Override
    public String toString() {
        return heroClass+"{" +
                "name=" + getName() + " " +
                "armor=" + getArmor() + " " +
                "health=" + getHealth() + " " +
                '}';
    }

    @Override
    public Hero clone() {
        try {
            Hero clone = (Hero) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
