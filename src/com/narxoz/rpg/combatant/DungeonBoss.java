package com.narxoz.rpg.combatant;

import com.narxoz.rpg.observer.EventPublisher;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import com.narxoz.rpg.observer.GameObserver;
import com.narxoz.rpg.strategy.CombatStrategy;
import com.narxoz.rpg.strategy.Boss.BossPhase1Strategy;
import com.narxoz.rpg.strategy.Boss.BossPhase2Strategy;
import com.narxoz.rpg.strategy.Boss.BossPhase3Strategy;

public class DungeonBoss implements GameObserver {
    String name;
    int hp;
    int maxHp;
    int attackPower;
    int defense;
    int currentPhase;
    CombatStrategy strategy;
    EventPublisher publisher;

    public DungeonBoss(String name, int hp, int attackPower, int defense, EventPublisher publisher) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.attackPower = attackPower;
        this.defense = defense;
        this.currentPhase = 1;
        this.strategy = new BossPhase1Strategy();
        this.publisher = publisher;
    }

    public void registerObserver(GameObserver observer) {
        publisher.register(observer);
    }

    private void fireEvent(GameEventType type, int value) {
        publisher.publish(new GameEvent(type, name, value));
    }

    @Override
    public void onEvent(GameEvent event) {
        if (event.getType() != GameEventType.BOSS_PHASE_CHANGED) return;
        
        switch (event.getValue()) {
            case 2 -> strategy = new BossPhase2Strategy();
            case 3 -> strategy = new BossPhase3Strategy();
            default -> {}
        }
    }

    public void takeDamage(int amount) {
        hp = Math.max(0, hp - amount);
        checkPhaseTransition();
    }

    private void checkPhaseTransition() {
        int newPhase = currentPhase;

        if (hp <= maxHp / 3 && currentPhase < 3) {
            newPhase = 3;
        } else if (hp <= maxHp * 2 / 3 && currentPhase < 2) {
            newPhase = 2;
        }

        if (newPhase != currentPhase) {
            currentPhase = newPhase;
            fireEvent(GameEventType.BOSS_PHASE_CHANGED, currentPhase);
        }
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }
    public int getCurrentPhase() { return currentPhase; }
    public CombatStrategy getStrategy() { return strategy; }
    public boolean isAlive() { return hp > 0; }
}
