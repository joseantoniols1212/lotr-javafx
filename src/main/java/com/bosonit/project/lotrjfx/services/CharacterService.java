package com.bosonit.project.lotrjfx.services;

import com.bosonit.project.lotrjfx.exceptions.InvalidInput;
import com.bosonit.project.lotrjfx.model.characters.beasts.Beast;
import com.bosonit.project.lotrjfx.model.characters.beasts.BeastClass;
import com.bosonit.project.lotrjfx.model.characters.heroes.Hero;
import com.bosonit.project.lotrjfx.model.characters.heroes.HeroClass;

public interface CharacterService {
    Hero createHero(String heroClass, String name, String healthText, String armorText) throws InvalidInput;
    Beast createBeast(String beastClass, String name, String healthText, String armorTexts) throws InvalidInput;
}
