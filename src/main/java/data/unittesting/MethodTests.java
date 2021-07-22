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
    public static StateType testMeleeMethods(Unit attackerUnit,
                  Unit defenderUnit, double areaBonus) {
        // temp variable holding original unit number
        int defenderNumber = defenderUnit.getNumber();

        // damages defender unit without area bonus
        StateType result = attackerUnit.attackWithRange(defenderUnit,
                0);

        // melee unit attacking with range should not return
        // anything other than success
        if (result.getResult() != Result.SUCCESS) {
            return result;
        }

        // if damage was inflicted, this means the melee
        // unit somehow inflicted ranged damage, failing
        // the test
        if (defenderNumber != defenderUnit.getNumber()) {
            return StateType.returnFailure("Error: melee unit inflicts ranged damage");
        }

        // "resets" defender unit
        defenderUnit.refill();

        // saves the amount of damage inflicted by the attacker unit
        // (used to ensure attack bonus for some units inflicts
        // at least as much damage as the the normal melee attack
        int damageInflicted;

        // inflicts melee damage on defender without area bonus
        result = attackerUnit.attackWithMelee(defenderUnit, 0);

        if (result.getResult() == Result.FAILURE) {
            return result;
        }

        damageInflicted = defenderNumber - defenderUnit.getNumber();

        // ensures that no integer invariants occur
        if (damageInflicted < 0) {
            return StateType.returnFailure("Error: damage inflicted is negative:" +
                    " no area bonus");
        }

        // inflicts melee damage on defender with area bonus
        defenderUnit.refill();
        result = attackerUnit.attackWithMelee(defenderUnit, areaBonus);

        if (result.getResult() == Result.FAILURE) {
            return result;
        }

        damageInflicted = defenderNumber - defenderUnit.getNumber();

        if (damageInflicted < 0) {
            return StateType.returnFailure("Error: damage inflicted is negative:" +
                    " area bonus");
        }

        // inflicts melee damage on defender with special ability
        // and no area bonus
        defenderUnit.refill();
        attackerUnit.activateSpecialAbility();

        result = attackerUnit.attackWithMelee(defenderUnit, 0);

        if (result.getResult() == Result.FAILURE) {
            return result;
        }

        damageInflicted = defenderNumber - defenderUnit.getNumber();

        if (damageInflicted < 0) {
            return StateType.returnFailure("Error: damage inflicted is negative:" +
                    " special ability, no area bonus");
        }

        // inflicts melee damage on defender with special ability
        // and given area bonus
        defenderUnit.refill();
        result = attackerUnit.attackWithMelee(defenderUnit, areaBonus);

        if (result.getResult() == Result.FAILURE) {
            return result;
        }

        damageInflicted = defenderNumber - defenderUnit.getNumber();

        if (damageInflicted < 0) {
            return StateType.returnFailure("Error: damage inflicted is negative:" +
                    " special ability, area bonus");
        }

        // final refill before future tests
        defenderUnit.refill();

        return StateType.returnSuccess(null);
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
    public static StateType testRangedMethods(Unit attackerUnit,
                                            Unit defenderUnit,
                                            double areaBonus) {
        // temp variable holding original unit number
        int defenderNumber = defenderUnit.getNumber();

        // damages defender unit without area bonus
        StateType check = attackerUnit.attackWithMelee(defenderUnit,
                0);

        // the melee attack should not return failure
        if (check.getResult() == Result.FAILURE) {
            return check;
        }

        // saves the amount of damage inflicted by the attacker unit
        // (used to ensure attack bonus for some units inflicts
        // at least as much damage as the the normal melee attack
        int damageInflicted = defenderNumber - defenderUnit.getNumber();

        // if the method increased the number of soldiers in the defender unit,
        // then the implementation must have some kind of bug or integer overflow
        // has occurred
        if (damageInflicted < 0) {
            return StateType.returnFailure("Error: damage inflicted is negative:" +
                    " no area bonus");
        }

        // "resets" defender unit
        defenderUnit.refill();

        // inflicts ranged damage on defender without area bonus
        check = attackerUnit.attackWithRange(defenderUnit, 0);

        if (check.getResult() == Result.FAILURE) {
            return check;
        }

        damageInflicted = defenderNumber - defenderUnit.getNumber();

        if (damageInflicted < 0) {
            return StateType.returnFailure("Error: melee damage inflicted is negative:" +
                    " no area bonus");
        }

        // inflicts ranged damage on defender with area bonus
        defenderUnit.refill();
        check = attackerUnit.attackWithRange(defenderUnit, areaBonus);

        if (check.getResult() == Result.FAILURE) {
            return StateType.returnFailure("Error: ranged damage inflicted is negative:" +
                    " area bonus");
        }

        damageInflicted = defenderNumber - defenderUnit.getNumber();

        if (damageInflicted < 0) {
            return StateType.returnFailure("Error: ranged damage inflicted is negative:" +
                    " special ability, no area bonus");
        }

        defenderUnit.refill();

        // inflicts ranged damage on defender with special ability
        // and no area bonus
        attackerUnit.activateSpecialAbility();
        check = attackerUnit.attackWithRange(defenderUnit, 0);

        if (check.getResult() == Result.FAILURE) {
            return check;
        }

        damageInflicted = defenderNumber - defenderUnit.getNumber();

        if (damageInflicted < 0) {
            return StateType.returnFailure("Error: damage inflicted is negative:" +
                    " special ability, no area bonus");
        }

        defenderUnit.refill();

        // inflicts ranged damage on defender with special ability
        // and given area bonus
        check = attackerUnit.attackWithRange(defenderUnit, areaBonus);

        if (check.getResult() == Result.FAILURE) {
            return check;
        }

        damageInflicted = defenderNumber - defenderUnit.getNumber();

        if (damageInflicted < 0) {
            return StateType.returnFailure("Error: damage inflicted is negative:" +
                    " special ability, area bonus");
        }

        defenderUnit.refill();

        return StateType.returnSuccess(null);
    }
}
