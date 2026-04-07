package com.narxoz.rpg.engine;

import com.narxoz.rpg.combatant.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.observer.EventPublisher;
import com.narxoz.rpg.observer.GameEvent;
import com.narxoz.rpg.observer.GameEventType;
import java.util.List;

public class DungeonEngine {
    private int MAX_ROUNDS = 100;
    private int LOW_HP_THRESHOLD = 30;

    private List<Hero> party;
    private DungeonBoss boss;
    private EventPublisher publisher;

    public DungeonEngine(List<Hero> party, DungeonBoss boss, EventPublisher publisher) {
        this.party = party;
        this.boss = boss;
        this.publisher = publisher;
    }

    public EncounterResult run() {
        int round = 0;

        while (round < MAX_ROUNDS) {
            round++;
            System.out.println("=== Round " + round + " ===");

            for (Hero hero : party) {
                if (!hero.isAlive()) continue;
                if (!boss.isAlive()) break;

                int raw = hero.getStrategy().calculateDamage(hero.getAttackPower());
                int def = boss.getStrategy().calculateDefense(boss.getDefense());
                int damage = Math.max(1, raw - def);

                boss.takeDamage(damage);
                fire(GameEventType.ATTACK_LANDED, hero.getName(), damage);
            }

            if (!boss.isAlive()) {
                fire(GameEventType.BOSS_DEFEATED, boss.getName(), round);
                break;
            }

            for (Hero hero : party) {
                if (!hero.isAlive()) continue;

                int raw = boss.getStrategy().calculateDamage(boss.getAttackPower());
                int def = hero.getStrategy().calculateDefense(hero.getDefense());
                int damage = Math.max(1, raw - def);

                hero.takeDamage(damage);
                fire(GameEventType.ATTACK_LANDED, boss.getName(), damage);

                if (!hero.isAlive()) {
                    fire(GameEventType.HERO_DIED, hero.getName(), 0);
                } else if (hero.getHp() <= LOW_HP_THRESHOLD) {
                    fire(GameEventType.HERO_LOW_HP, hero.getName(), hero.getHp());
                }
            }

            if (allDead()) break;
        }

        int survivors = (int) party.stream().filter(Hero::isAlive).count();
        boolean heroesWon = boss != null && !boss.isAlive();
        return new EncounterResult(heroesWon, round, survivors);
    }

    private void fire(GameEventType type, String source, int value) {
        publisher.publish(new GameEvent(type, source, value));
    }

    private boolean allDead() {
        return party.stream().noneMatch(Hero::isAlive);
    }
}
