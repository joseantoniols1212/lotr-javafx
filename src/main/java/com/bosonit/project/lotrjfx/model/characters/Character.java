package com.bosonit.project.lotrjfx.model.characters;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLongArray;

@Getter
public abstract class Character {
    private final String name;
    @Setter
    private int health;
    private final int armor;

    public Character(String name, int health, int armor) {
        this.name = name;
        this.health = health;
        this.armor = armor;
    }

    protected int throwDice(int low, int high) {
        if (low >= high) {
            throw new IllegalArgumentException("Higher value of dice throw can't be lower or equal than the lower");
        }
        return (int) (Math.round((Math.random()+low) * (high - low) ));
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getArmor() {
        return armor;
    }

    public int getDamage(int powerAttack, int extraAttackPower, int extraArmor){
        int damage = Math.max(- getArmor() - extraArmor + powerAttack + extraAttackPower, 0);
        setHealth(getHealth() - damage);
        return damage;
    }

    public abstract int getAttackPower();
    public abstract int getExtraAttackPower(CharacterClass opponentClass);
    public abstract int getExtraArmor(CharacterClass opponentClass);

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Character character = (Character) o;
        return Objects.equals(name, character.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
