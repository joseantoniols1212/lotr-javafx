package com.bosonit.project.lotrjfx.model.events;

import com.bosonit.project.lotrjfx.model.characters.Character;

public record CharacterDeath(Character character) implements BattleEvent {}