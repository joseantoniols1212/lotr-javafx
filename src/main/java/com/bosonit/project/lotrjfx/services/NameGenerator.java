package com.bosonit.project.lotrjfx.services;

import com.bosonit.project.lotrjfx.exceptions.NotImplementedBeastException;
import com.bosonit.project.lotrjfx.exceptions.NotImplementedHeroException;
import com.bosonit.project.lotrjfx.model.characters.beasts.BeastClass;
import com.bosonit.project.lotrjfx.model.characters.heroes.HeroClass;

import java.util.Random;

public class NameGenerator {

    private static final String[] elfPrefixes = {
            "Ae", "Eli", "Fa", "Lae", "My", "Syl", "Thae", "Uri", "Val", "Zy"
    };
    private static final String[] elfSuffixes = {
            "riel", "wyn", "lith", "nora", "thiel", "mir", "lanna", "driel", "shae", "vanna"
    };

    private static final String[] orcPrefixes = {
            "Gor", "Ug", "Thok", "Rag", "Mug", "Zug", "Grish", "Snag", "Brak", "Lug"
    };
    private static final String[] orcSuffixes = {
            "nak", "gash", "dush", "mog", "zug", "krag", "nuk", "thrak", "zog", "rok"
    };

    private static final String[] humanPrefixes = {
            "Al", "Ber", "Ced", "Dar", "Ed", "Fer", "Gal", "Har", "Jon", "Kel"
    };
    private static final String[] humanSuffixes = {
            "ric", "win", "mond", "bert", "son", "dan", "lin", "ward", "thas", "don"
    };

    private static final String[] hobbitPrefixes = {
            "Bil", "Sam", "Mar", "Pip", "Ros", "Fro", "Hob", "Tom", "Wil", "Ben"
    };
    private static final String[] hobbitSuffixes = {
            "bo", "wise", "fast", "foot", "rick", "kin", "lo", "lily", "bert", "o"
    };

    private static final String[] goblinPrefixes = {
            "Skri", "Snar", "Glim", "Drak", "Zik", "Pluk", "Nurk", "Krit", "Gruk", "Yag"
    };
    private static final String[] goblinSuffixes = {
            "zit", "nak", "glek", "snik", "krut", "fak", "zog", "blik", "guk", "rak"
    };

    private static final Random random = new Random();

    public static String generateHeroName(HeroClass heroClass) {
        return switch (heroClass) {
            case ELF -> generateFromParts(elfPrefixes, elfSuffixes);
            case HUMAN -> generateFromParts(humanPrefixes, humanSuffixes);
            case HOBBIT -> generateFromParts(hobbitPrefixes, hobbitSuffixes);
            default ->
                    throw new NotImplementedHeroException("Generate name is not implemented for class: " + heroClass.toString());
        };
    }

    public static String generateBeastName(BeastClass beastClass) {
        return switch (beastClass) {
            case ORC -> generateFromParts(orcPrefixes, orcSuffixes);
            case GOBLIN -> generateFromParts(goblinPrefixes, goblinSuffixes);
            default ->
                    throw new NotImplementedBeastException("Generate name is not implemented for class: " + beastClass.toString());
        };
    }

    private static String generateFromParts(String[] prefixes, String[] suffixes) {
        String prefix = prefixes[random.nextInt(prefixes.length)];
        String suffix = suffixes[random.nextInt(suffixes.length)];
        return prefix + suffix;
    }
}
