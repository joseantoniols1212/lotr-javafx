package com.bosonit.project.lotrjfx.model.events;

public record RoundStart(int roundNumber) implements BattleEvent {
    @Override
    public String toString() {
        return "Round " + this.roundNumber;
    }
}
