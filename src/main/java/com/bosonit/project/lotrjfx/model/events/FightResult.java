package com.bosonit.project.lotrjfx.model.events;

import com.bosonit.project.lotrjfx.model.characters.beasts.Beast;
import com.bosonit.project.lotrjfx.model.characters.heroes.Hero;

public record FightResult(
        Hero hero,
        Beast beast,
        int heroAttackPower,
        int beastAttackPower,
        int heroExtraArmor,
        int beastExtraArmor,
        int heroExtraAttackPower,
        int beastExtraAttackPower,
        int heroReceivedDamage,
        int beastReceivedDamage
) implements BattleEvent {
    @Override
    public String toString() {
        return  "   -> Fight between " + hero.toString() + " and " + beast.toString() + "\n" +
                "       >> " + hero.getName() + " obtains " + heroAttackPower + "("+ heroExtraAttackPower + ")" + " attack power and attacks " + beast.getName() +
                " who has " + beast.getArmor() + "("+ beastExtraArmor + ")"  + " armor points, dealing " + beastReceivedDamage + " damage points. \n" +
                "       >> " + beast.getName() + " obtains " + beastAttackPower + "("+ beastExtraAttackPower + ")" + " attack power and attacks " + hero.getName() +
                " who has " + hero.getArmor() +  "("+ heroExtraArmor + ")"  + " armor points, dealing " + heroReceivedDamage + " damage points.";
    }
}