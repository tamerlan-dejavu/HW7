package com.narxoz.rpg.observer;

import com.narxoz.rpg.combatant.Hero;

import java.util.List;

public class PartySupport implements GameObserver {
    private static final int HEAL_AMOUNT = 20;
    private List<Hero> party;

    public PartySupport(List<Hero> party) {
        this.party = party;
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() != GameEventType.HERO_LOW_HP) return;

        String heroName = event.getSourceName();
        for (Hero hero : party) {
            if (hero.getName().equals(heroName) && hero.isAlive()) {
                hero.heal(HEAL_AMOUNT);
                System.out.println("Emergency heal! " + heroName + " restored " + HEAL_AMOUNT + " HP (now " + hero.getHp() + ").");
                return;
            }
        }
    }
}
