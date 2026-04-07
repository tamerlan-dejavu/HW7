package com.narxoz.rpg.observer;

import java.util.*;

public class AchievementTracker implements GameObserver {

    private final Set<String> unlocked = new HashSet<>();
    private int heroDeaths = 0;

    @Override
    public void onEvent(GameEvent event) {
        switch (event.getType()) {
            case ATTACK_LANDED -> {
                unlock("First Blood", "The first blow was struck in battle.");

                if (event.getValue() >= 50) {
                    unlock("Devastating Blow", "A single attack dealt " + event.getValue() + " damage!");
                }
            }
            case HERO_DIED -> {
                heroDeaths++;
            }
            case BOSS_PHASE_CHANGED -> {
                if (event.getValue() >= 3) {
                    unlock("Phase Buster", "The boss was pushed into its final enraged phase!");
                }
            }
            case BOSS_DEFEATED -> {
                if (heroDeaths == 0) {
                    unlock("No One Left Behind", "Defeated the boss without losing a single hero!");
                }
            }
            default -> {}
        }
    }

    private void unlock(String name, String description) {
        if (unlocked.add(name)) {
            System.out.println("[ACHIEVEMENT UNLOCKED] " + name + " — " + description);
        }
    }

    public Set<String> getUnlocked() {
        return unlocked;
    }
}
