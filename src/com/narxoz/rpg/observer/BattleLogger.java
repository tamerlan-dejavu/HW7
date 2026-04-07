package com.narxoz.rpg.observer;

public class BattleLogger implements GameObserver {
    @Override
    public void onEvent(GameEvent event) {
        String message = switch (event.getType()) {
            case ATTACK_LANDED -> "[LOG] " + event.getSourceName() + " landed an attack for " + event.getValue() + " damage.";
            case HERO_LOW_HP -> "[LOG] " + event.getSourceName() + " is critically low on HP (" + event.getValue() + " HP remaining).";
            case HERO_DIED -> "[LOG] " + event.getSourceName() + " has fallen in battle.";
            case BOSS_PHASE_CHANGED -> "[LOG] Boss entered phase " + event.getValue() + " — strategy changed.";
            case BOSS_DEFEATED-> "[LOG] Boss has been defeated after " + event.getValue() + " rounds!";
        };
        
        System.out.println(message);
    }
}
