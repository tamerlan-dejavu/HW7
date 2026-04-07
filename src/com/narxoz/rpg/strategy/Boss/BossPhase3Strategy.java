package com.narxoz.rpg.strategy.Boss;

import com.narxoz.rpg.strategy.CombatStrategy;

public class BossPhase3Strategy implements CombatStrategy {

    @Override
    public int calculateDamage(int basePower) {
        return (int) (basePower * 2.0);
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return (int) (baseDefense * 0.5);
    }

    @Override
    public String getName() {
        return "Boss Phase 3 (Desperate)";
    }
}
