package com.narxoz.rpg.observer;

import com.narxoz.rpg.combatant.Hero;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PartySupport implements GameObserver {
    private static final int HEAL_AMOUNT = 20;
    private final List<Hero> party;
    private final Random random = new Random();

    public PartySupport(List<Hero> party) {
        this.party = party;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() != GameEventType.HERO_LOW_HP) return;

        List<Hero> livingAllies = party.stream()
                .filter(Hero::isAlive)
                .collect(Collectors.toList());

        if (livingAllies.isEmpty()) return;

        Hero target = livingAllies.get(random.nextInt(livingAllies.size()));
        target.heal(HEAL_AMOUNT);
        System.out.println("[SUPPORT] Emergency heal! " + target.getName() + " restored " + HEAL_AMOUNT + " HP (now " + target.getHp() + ").");
    }
}
