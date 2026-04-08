package com.narxoz.rpg;

import com.narxoz.rpg.combatant.DungeonBoss;
import com.narxoz.rpg.combatant.Hero;
import com.narxoz.rpg.engine.DungeonEngine;
import com.narxoz.rpg.engine.EncounterResult;
import com.narxoz.rpg.observer.*;
import com.narxoz.rpg.strategy.Hero.AggressiveStrategy;
import com.narxoz.rpg.strategy.Hero.DefensiveStrategy;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Hero aria = new Hero("Aria",  100, 40, 15);   // starts Balanced (default)
        Hero bryn = new Hero("Bryn",  130, 25, 25);
        Hero cole = new Hero("Cole",  110, 30, 20);

        aria.setStrategy(new AggressiveStrategy());
        bryn.setStrategy(new DefensiveStrategy());

        List<Hero> party = List.of(aria, bryn, cole);

        EventPublisher publisher = new EventPublisher();

        DungeonBoss boss = new DungeonBoss("Shadow Tyrant", 450, 35, 8, publisher);

        boss.registerObserver(boss);

        BattleLogger logger = new BattleLogger();
        AchievementTracker tracker = new AchievementTracker();
        PartySupport support = new PartySupport(party);
        HeroStatusMonitor monitor= new HeroStatusMonitor(party);
        LootDropper loot = new LootDropper();

        boss.registerObserver(logger);
        boss.registerObserver(tracker);
        boss.registerObserver(support);
        boss.registerObserver(monitor);
        boss.registerObserver(loot);

        boss.registerObserver(event -> {
            if (event.getType() == GameEventType.BOSS_PHASE_CHANGED && event.getValue() == 2) {
                cole.setStrategy(new AggressiveStrategy());
                System.out.println("[SWITCH] Cole switches to Aggressive strategy!"); }
        });
        DungeonEngine engine = new DungeonEngine(party, boss, publisher);
        EncounterResult result = engine.run();

        System.out.println("\n========== ENCOUNTER RESULT ==========");
        System.out.println("Outcome:          " + (result.isHeroesWon() ? "Victory!" : "Defeat..."));
        System.out.println("Rounds played:    " + result.getRoundsPlayed());
        System.out.println("Surviving heroes: " + result.getSurvivingHeroes());
        System.out.println("======================================");
    }
}
