package com.narxoz.rpg.observer;

public class LootDropper implements GameObserver {

    @Override
    public void onEvent(GameEvent event) {
        switch (event.getType()) {
            case BOSS_PHASE_CHANGED -> dropPhaseLoot(event.getValue());
            case BOSS_DEFEATED -> dropVictoryLoot();
            default -> {}
        }
    }

    private void dropPhaseLoot(int phase) {
        String loot = switch (phase) {
            case 2 -> "Enchanted Shield Fragment";
            case 3 -> "Cursed Ruby Shard";
            default -> "Mysterious Relic";
        };
        System.out.println("Phase " + phase + " triggered a drop: " + loot + "!");
    }

    private void dropVictoryLoot() {
        String[] items = {"Dragon's Heart Gem", "Ancient Armor Plate", "500 Gold"};
        System.out.println("Boss defeated! The party claims:");
        for (String item : items) {
            System.out.println("  - " + item);
        }
    }
}
