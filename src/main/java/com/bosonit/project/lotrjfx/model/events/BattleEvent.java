package com.bosonit.project.lotrjfx.model.events;

public sealed interface BattleEvent permits CharacterDeath, FightResult, GameOver, RoundStart {}

