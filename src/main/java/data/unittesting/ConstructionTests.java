package data.unittesting;

import data.interfaces.Unit;

public class ConstructionTests {
    /**
     * Ensures that the stats of a unit are initialized properly.
     * A Unit unit is taken in and interface methods are tested
     * @return boolean, false if test(s) fail or true if test(s)
     * succeed.
     */
    public static String testUnitStats(Unit unit) {
        // checking for invalid values
        if (unit.getUnitName() == null)
            return "Failure: Unit name uninitialized";

        if (unit.getNumber() <= 0)
            return "Failure: Unit number negative or zero";

        if (unit.getLimit() <= 0)
            return "Failure: Unit limit negative or zero";

        if (unit.getUnitCost() <= 0)
            return "Failure: Unit cost negative or zero";

        if (unit.getArmor() < 0)
            return "Failure: Unit armor negative";

        if (unit.getMovementCost() <= 0)
            return "Failure: Unit movement cost negative or zero";

        if (unit.getRangeFactor() <= 0)
            return "Failure: Unit range factor negative or zero";

        // checks whether initialization ensures that ranged
        // unit is not recognized as melee unit
        if (unit.isRangedUnit()) {
            if (unit.getRangeFactor() == 1) {
                return "Failure: Ranged unit has range factor of 1";
            }
        }

        if (!unit.isRangedUnit()) {
            if (unit.getRangeFactor() != 1) {
                return "Failure: Ranged unit has ranged factor of " +
                        "> 1";
            }
        }

        return "SUCCESS";
    }
}
