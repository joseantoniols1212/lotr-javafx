package com.bosonit.project.lotrjfx.model.services;

import com.bosonit.project.lotrjfx.model.exceptions.InvalidInput;
import com.bosonit.project.lotrjfx.model.characters.beasts.Beast;
import com.bosonit.project.lotrjfx.model.characters.heroes.Hero;

public interface CharacterService {
    Hero createHero(String heroClass, String name, String healthText, String armorText) throws InvalidInput;
    Beast createBeast(String beastClass, String name, String healthText, String armorTexts) throws InvalidInput;
}
