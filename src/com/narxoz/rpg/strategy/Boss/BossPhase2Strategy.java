package com.narxoz.rpg.strategy.Boss;

import com.narxoz.rpg.strategy.CombatStrategy;

public class BossPhase2Strategy implements CombatStrategy {

    @Override
    public int calculateDamage(int basePower) {
        return (int) (basePower * 1.4);
    }

    @Override
    public int calculateDefense(int baseDefense) {
        return (int) (baseDefense * 0.9);
    }

    @Override
    public String getName() {
        return "Boss Phase 2 (Enraged)";
    }
}
