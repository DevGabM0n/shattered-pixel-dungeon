
package com.shatteredpixel.shatteredpixeldungeon.bot;

public final class AutoBot {

    public enum Action {
        HEAL,
        ATTACK,
        PICKUP,
        EXPLORE,
        RETREAT,
        WAIT
    }

    public static class State {
        public int hp;
        public int maxHp;
        public boolean hasHealingPotion;
        public boolean enemyVisible;
        public boolean enemyAdjacent;
        public boolean lootVisible;
        public boolean safePathAvailable;
    }

    public static Action decide(State s) {
        if (s.hp <= 0 || s.maxHp <= 0) {
            return Action.WAIT;
        }

        double health = (double) s.hp / s.maxHp;

        if (health < 0.35 && s.hasHealingPotion) {
            return Action.HEAL;
        }

        if (health < 0.25 && s.enemyVisible) {
            return s.safePathAvailable
                ? Action.RETREAT : Action.ATTACK;
        }

        if (s.enemyAdjacent) {
            return Action.ATTACK;
        }

        if (s.enemyVisible && s.safePathAvailable) {
            return Action.ATTACK;
        }

        if (s.lootVisible) {
            return Action.PICKUP;
        }

        return Action.EXPLORE;
    }

    private AutoBot() {}
}
