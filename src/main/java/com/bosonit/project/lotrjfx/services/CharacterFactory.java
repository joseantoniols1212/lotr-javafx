package com.bosonit.project.lotrjfx.services;

import com.bosonit.project.lotrjfx.model.characters.beasts.Beast;
import com.bosonit.project.lotrjfx.model.characters.beasts.BeastClass;
import com.bosonit.project.lotrjfx.model.characters.heroes.*;

import java.util.List;
import java.util.Random;

public class CharacterFactory {

    private static final Random RANDOM = new Random();
    private static final List<BeastClass> BEAST_CLASSES = List.of(BeastClass.values());
    private static final int BEAST_SIZE = BEAST_CLASSES.size();
    private static final List<HeroClass> HERO_CLASSES = List.of(HeroClass.values());
    private static final int HERO_SIZE = HERO_CLASSES.size();


    public static Beast createBeast(BeastClass beastClass, String name, int health, int armor) {
        return new Beast(beastClass, name, health, armor);
    }

    public static Hero createHero(HeroClass heroClass, String name, int health, int armor) {
        return new Hero(heroClass, name, health, armor);
    }

    public static Beast createRandomBeast() {
        BeastClass beastClass = getRandomBeastClass();
        String name = NameGenerator.generateBeastName(beastClass);
        int health = getRandomBeastHealth();
        int armor = getRandomBeastArmor();
        return createBeast(beastClass, name, health, armor);
    }

    private static BeastClass getRandomBeastClass() {
       return BEAST_CLASSES.get(RANDOM.nextInt(BEAST_SIZE));
    }

    private static int getRandomBeastHealth() {
        return RANDOM.nextInt(Beast.MIN_HEALTH_BEAST, Beast.MAX_HEALTH_BEAST);
    }

    private static int getRandomBeastArmor() {
        return RANDOM.nextInt(Beast.MIN_ARMOR_BEAST, Beast.MAX_ARMOR_BEAST);
    }

    public static Hero createRandomHero() {
        HeroClass heroClass = getRandomHeroClass();
        String name = NameGenerator.generateHeroName(heroClass);
        int health = getRandomHeroHealth();
        int armor = getRandomHeroArmor();
        return createHero(heroClass, name, health, armor);
    }

    private static HeroClass getRandomHeroClass() {
        return HERO_CLASSES.get(RANDOM.nextInt(HERO_SIZE));
    }

    private static int getRandomHeroHealth() {
        return RANDOM.nextInt(Hero.MIN_HEALTH_HERO, Hero.MAX_HEALTH_HERO);
    }

    private static int getRandomHeroArmor() {
        return RANDOM.nextInt(Hero.MIN_ARMOR_HERO, Hero.MAX_ARMOR_HERO);
    }
}