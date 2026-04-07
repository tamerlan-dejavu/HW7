package com.narxoz.rpg.observer;

import com.narxoz.rpg.combatant.Hero;

import java.util.List;

public class HeroStatusMonitor implements GameObserver {

    private final List<Hero> party;

    public HeroStatusMonitor(List<Hero> party) {
        this.party = party;
    }

    @Override
    public void onEvent(GameEvent event) {
        switch (event.getType()) {
            case ATTACK_LANDED, HERO_LOW_HP, HERO_DIED, BOSS_PHASE_CHANGED, BOSS_DEFEATED -> printStatus();
        }
    }

    private void printStatus() {
        System.out.println("[STATUS] Party overview:");
        for (Hero hero : party) {
            String state = hero.isAlive() ? hero.getHp() + "/" + hero.getMaxHp() + " HP" : "DEAD";
            System.out.println("  " + hero.getName() + ": " + state);
        }
    }
}
