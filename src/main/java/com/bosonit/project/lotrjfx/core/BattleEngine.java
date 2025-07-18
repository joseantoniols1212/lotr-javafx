package com.bosonit.project.lotrjfx.core;

import com.bosonit.project.lotrjfx.model.characters.Character;
import com.bosonit.project.lotrjfx.model.characters.beasts.Beast;
import com.bosonit.project.lotrjfx.model.characters.heroes.Hero;
import com.bosonit.project.lotrjfx.model.events.*;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BattleEngine {
    final int MAX_ROUNDS = 500;
    private final List<Beast> beastArmy;
    private final List<Hero> heroArmy;
    private final PublishSubject<BattleEvent> eventStream = PublishSubject.create();

    public BattleEngine(List<Beast> beastArmy, List<Hero> heroArmy) {
        this.beastArmy = beastArmy.stream().map(Beast::clone).collect(Collectors.toList());
        this.heroArmy = heroArmy.stream().map(Hero::clone).collect(Collectors.toList());
    }

    public Observable<BattleEvent> getEventStream() {
        return eventStream;
    }

    public void start() {
        for (int i = 0; i < MAX_ROUNDS && !beastArmy.isEmpty() && !heroArmy.isEmpty(); i++) {
            System.out.println("b");
            eventStream.onNext(new RoundStart(i));
            fightRound();
        }

        if (beastArmy.isEmpty()) {
            eventStream.onNext(new GameOver("Heroes win!"));
        } else if (heroArmy.isEmpty()) {
            eventStream.onNext(new GameOver("Beasts win!"));
        } else {
            eventStream.onNext(new GameOver("Nobody wins!"));
        }

        eventStream.onComplete();
    }

    private void fightRound() {
        IntStream.range(0, Math.max(heroArmy.size(), beastArmy.size()))
                .mapToObj(i -> fight(heroArmy.get(i % heroArmy.size()), beastArmy.get(i % beastArmy.size())))
                .forEach(eventStream::onNext);

        Predicate<Character> isDeadAndNotify = c -> {
                boolean result = c.getHealth() <= 0;
                if (result)
                    eventStream.onNext(new CharacterDeath(c));
                return result;
        };

        beastArmy.removeIf(isDeadAndNotify);
        heroArmy.removeIf(isDeadAndNotify);
    }

    private FightResult fight(Hero hero, Beast beast) {
        int heroAttackPower = hero.getAttackPower();
        int beastAttackPower = beast.getAttackPower();

        int heroExtraAttackPower = hero.getExtraAttackPower(beast.getBeastClass());
        int beastExtraAttackPower = beast.getExtraAttackPower(hero.getHeroClass());

        int heroExtraArmor = hero.getExtraArmor(beast.getBeastClass());
        int beastExtraArmor = beast.getExtraArmor(hero.getHeroClass());

        int heroReceivedDamage = hero.getDamage(beastAttackPower, beastExtraAttackPower, heroExtraArmor);
        int beastReceivedDamage = beast.getDamage(heroAttackPower, heroExtraAttackPower, beastExtraArmor);

        return new FightResult(
                hero.clone(),
                beast.clone(),
                heroAttackPower,
                beastAttackPower,
                heroExtraArmor,
                beastExtraArmor,
                heroExtraAttackPower,
                beastExtraAttackPower,
                heroReceivedDamage,
                beastReceivedDamage
        );
    }
}
