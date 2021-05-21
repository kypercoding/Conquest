package data.unittesting;

import data.interfaces.Unit;

public class MethodTests {
    /**
     * Ensures that a melee unit's methods lower an enemy
     * unit's number of soldiers as intended.
     * @param attackerUnit represents the unit attacking
     * @param defenderUnit represents the unit defending
     * @param areaBonus of the defending unit
     * @return boolean, false if test(s) fail or true if test(s)
     * succeed.
     */
    public static String testMeleeMethods(Unit attackerUnit,
                  Unit defenderUnit, double areaBonus) {
        String methodFailure = "Failure: error within unit " +
                "implementation methods.";
        String damageError = "Failure: error within damage" +
                " calculation";

        // temp variable holding original unit number
        int defenderNumber = defenderUnit.getNumber();

        // damages defender unit without area bonus
        boolean check = attackerUnit.attackWithRange(defenderUnit,
                0);

        // validation methods within the attacker unit have failed
        // somehow
        if (!check) {
            return methodFailure;
        }

        // if damage was inflicted, this means the melee
        // unit somehow inflicted ranged damage, failing
        // the test
        if (defenderNumber != defenderUnit.getNumber()) {
            return "Failure: melee unit perceived as ranged unit";
        }

        // "resets" defender unit
        defenderUnit.refill();

        // saves the amount of damage inflicted by the attacker unit
        // (used to ensure attack bonus for some units inflicts
        // at least as much damage as the the normal melee attack
        int damageInflicted;

        // inflicts melee damage on defender without area bonus
        check = attackerUnit.attackWithMelee(defenderUnit, 0);

        if (!check) {
            return methodFailure;
        }

        damageInflicted = defenderNumber - defenderUnit.getNumber();

        // ensures that no integer overflow errors occur
        if (damageInflicted < 0) {
            return damageError + ": no area bonus";
        }

        // inflicts melee damage on defender with area bonus
        defenderUnit.refill();
        check = attackerUnit.attackWithMelee(defenderUnit, areaBonus);

        if (!check) {
            return methodFailure;
        }

        damageInflicted = defenderNumber - defenderUnit.getNumber();

        if (damageInflicted < 0) {
            return damageError + ": area bonus";
        }

        // inflicts melee damage on defender with special ability
        // and no area bonus
        defenderUnit.refill();
        attackerUnit.activateSpecialAbility();
        check = attackerUnit.attackWithMelee(defenderUnit, 0);

        if (!check) {
            return methodFailure;
        }

        damageInflicted = defenderNumber - defenderUnit.getNumber();

        if (damageInflicted < 0) {
            return damageError + ": special ability, no area bonus";
        }

        // inflicts melee damage on defender with special ability
        // and given area bonus
        defenderUnit.refill();
        check = attackerUnit.attackWithMelee(defenderUnit, areaBonus);

        if (!check) {
            return methodFailure;
        }

        damageInflicted = defenderNumber - defenderUnit.getNumber();

        if (damageInflicted < 0) {
            return damageError + ": special ability, area bonus";
        }

        return "SUCCESS";
    }

    /**
     * Ensures that a ranged unit's methods lower an enemy
     * unit's number of soldiers as intended.
     * @param attackerUnit represents the unit attacking
     * @param defenderUnit represents the unit defending
     * @param areaBonus of the defending unit
     * @return boolean, false if test(s) fail or true if test(s)
     * succeed.
     */
    public static boolean testRangedMethods(Unit attackerUnit,
                                            Unit defenderUnit,
                                            double areaBonus) {
        // temp variable holding original unit number
        int defenderNumber = defenderUnit.getNumber();

        // damages defender unit without area bonus
        boolean check = attackerUnit.attackWithMelee(defenderUnit,
                0);

        // validation methods within the attacker unit have failed
        // somehow
        if (!check) {
            return false;
        }

        // saves the amount of damage inflicted by the attacker unit
        // (used to ensure attack bonus for some units inflicts
        // at least as much damage as the the normal melee attack
        int damageInflicted = defenderNumber - defenderUnit.getNumber();

        // if the method increased the number of soldiers in the defender unit,
        // then the implementation must have some kind of bug or integer overflow
        // has occurred
        if (damageInflicted < 0) {
            return false;
        }

        // "resets" defender unit
        defenderUnit.refill();

        // inflicts ranged damage on defender without area bonus
        check = attackerUnit.attackWithRange(defenderUnit, 0);

        if (!check) {
            return false;
        }

        damageInflicted = defenderNumber - defenderUnit.getNumber();

        if (damageInflicted < 0) {
            return false;
        }

        // inflicts ranged damage on defender with area bonus
        defenderUnit.refill();
        check = attackerUnit.attackWithRange(defenderUnit, areaBonus);

        if (!check) {
            return false;
        }

        damageInflicted = defenderNumber - defenderUnit.getNumber();

        if (damageInflicted < 0) {
            return false;
        }

        defenderUnit.refill();

        // inflicts ranged damage on defender with special ability
        // and no area bonus
        attackerUnit.activateSpecialAbility();
        check = attackerUnit.attackWithRange(defenderUnit, 0);

        if (!check) {
            return false;
        }

        damageInflicted = defenderNumber - defenderUnit.getNumber();

        if (damageInflicted < 0) {
            return false;
        }

        defenderUnit.refill();

        // inflicts ranged damage on defender with special ability
        // and given area bonus
        check = attackerUnit.attackWithRange(defenderUnit, areaBonus);

        if (!check) {
            return false;
        }

        damageInflicted = defenderNumber - defenderUnit.getNumber();

        return damageInflicted >= 0;
    }
}
