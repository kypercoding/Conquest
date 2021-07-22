package data.unittesting;

import data.interfaces.Unit;

public class ConstructionTests {
    /**
     * Ensures that the stats of a unit are initialized properly.
     * A Unit unit is taken in and interface methods are tested
     * @return boolean, false if test(s) fail or true if test(s)
     * succeed.
     */
    public static StateType testUnitStats(Unit unit) {
        // checking for invalid values
        if (unit.getUnitName() == null)
            return StateType.returnFailure("Error: Unit name empty or null.");

        if (unit.getNumber() <= 0)
            return StateType.returnFailure("Error: Unit number negative or zero");

        if (unit.getLimit() <= 0)
            return StateType.returnFailure("Error: Unit limit negative or zero");

        if (unit.getUnitCost() <= 0)
            return StateType.returnFailure("Error: Unit cost negative or zero");

        if (unit.getArmor() < 0)
            return StateType.returnFailure("Error: Unit armor negative");

        if (unit.getMeleeDamage() <= 0)
            return StateType.returnFailure("Error: Unit melee damage negative or zero");

        if (unit.getRangedDamage() < 0)
            return StateType.returnFailure("Error: Unit ranged damage negative");

        if (unit.getMovementCost() <= 0)
            return StateType.returnFailure("Error: Unit movement cost negative or zero");

        if (unit.getRangeFactor() <= 0)
            return StateType.returnFailure("Error: Unit range factor negative or zero");

        // checks whether initialization ensures that ranged
        // unit is not recognized as melee unit
        if (unit.isRangedUnit()) {
            if (unit.getRangeFactor() == 1) {
                return StateType.returnFailure("Failure: Ranged unit has range factor of 1");
            }
        }

        if (!unit.isRangedUnit()) {
            if (unit.getRangeFactor() != 1) {
                return StateType.returnFailure("Failure: Ranged unit has ranged factor of " +
                        "> 1");
            }
        }

        return StateType.returnSuccess(null);
    }
}
