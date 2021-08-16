package battle.battlefield;

import battle.interfaces.Unit;
import calculations.GridCalculations;
import testing.unittesting.StateType;

import java.util.HashMap;

/**
 * Two HashMaps and two integers represent the BattleHandler.
 * The class ensures that any interaction between two units
 * is completely valid (i.e. ensuring that a unit attacks
 * within range, etc.).
 */
public class BattleHandler {
    /** HashMap holding ammunition, movement points, and
     *  special ability turns a unit has left.
     */
    private HashMap<Unit, UnitNums> unitMap;

    /**
     * Initializes the hash maps for a given
     * array of units.
     * @param units, an array of Unit objects
     */
    private void initializeMaps(Unit[] units, int maxMovement,
                                int maxAmmo, int maxTurns) {
        // initializes the HashMap
        unitMap = new HashMap<>();

        // adds each unit to both ammo and movement
        // HashMaps
        for (Unit unit : units) {
            // creates new UnitNums object
            UnitNums nums = new UnitNums(0,
                    maxMovement, maxTurns);

            // gives each RANGED unit a certain amount of
            // ammunition
            if (unit.isRangedUnit()) {
                nums.setAmmunition(maxAmmo);
            }

            // creates binding for unit
            unitMap.put(unit, nums);
        }
    }

    /**
     * Creates a BattleHandler object and initializes it.
     * @param attackers, units corresponding to attacking player
     * @param defenders, units corresponding to defending player
     * @param maxAmmo, the max amount of ammo a unit can have
     * @param maxMovement, the max amount of movement points
     *                     a unit can have
     */
    public BattleHandler(Unit[] attackers, Unit[] defenders,
                         int maxAmmo, int maxMovement,
                         int maxTurns) {
        // adds attackers and defenders to movement point and
        // ammunition hash maps
        initializeMaps(attackers, maxMovement, maxAmmo, maxTurns);
        initializeMaps(defenders, maxMovement, maxAmmo, maxTurns);
    }

    /**
     * Sets every unit's movement points back to the maximum
     * amount given at the start of each player's turn.
     * @param units from the current player
     * @param maxMovement that each unit gets.
     * @return StateType
     */
    public StateType resetMovement(Unit[] units, int maxMovement) {
        // gives each unit the maximum amount of movement points
        // again
        for (Unit unit : units) {
            // if no mapping is somehow found, then return failure
            if (!unitMap.containsKey(unit)) {
                return StateType.returnFailure("Error: no mapping " +
                        "found during movement reset");
            }

            // resets movement points for the unit
            unitMap.get(unit).setMovePoints(maxMovement);
        }

        return StateType.returnSuccess(null);
    }

    public StateType fightTwoUnits(int attackID, int defendID,
                                   Battlefield battlefield,
                                   int pathLength) {
        // retrieves attacker and defender units from
        // battlefield
        Unit attacker = battlefield.getUnit(attackID);
        Unit defender = battlefield.getUnit(defendID);

        // retrieves defensive area bonus for defender
        double areaBonus =
                battlefield.getArea(defendID).getDefenseBonus();

        // retrieves number of areas in one row
        int n = battlefield.getRowCount();

        // if any of the units are found to be null, return
        // failure
        if (attacker == null) {
            return StateType.returnFailure("Error: Attacker unit " +
                    "nonexistent");
        }

        if (defender == null) {
            return StateType.returnFailure("Error: Defender unit " +
                    "nonexistent");
        }

        // if a unit binding doesn't exist for a unit, then return
        // failure
        if (!unitMap.containsKey(attacker))
            return StateType.returnFailure("Error: Attacker unit " +
                    "binding nonexistent");

        if (!unitMap.containsKey(defender))
            return StateType.returnFailure("Error: Defender unit " +
                    "binding nonexistent");

        // calculates the euclidean distance between two
        // units to ensure that a unit attacks another
        // unit within a valid range
        double dist = GridCalculations.getEuclidDistance(attackID,
                defendID, n);

        // calculates actual range of a given unit, set automatically
        // to Math.sqrt(2) if it is a ranged unit with no more
        // ammunition, a melee unit, or a unit that directly
        // neighbors the unit
        double range = getRangeOfUnit(attacker);

        // handles ammunition for an attacker unit, if
        // it intends to use ranged attacks
        int ammo = 0;
        boolean isMelee = checkIfMelee(attacker, range);

        // if the unit plans on attacking with range, its ammo
        // must be retrieved for future use
        if (!isMelee) {
            // retrieves the ranged unit's ammunition amount
            // from the HashMap, if the unit exists in
            // the HashMap. If it doesn't, then return
            // failure
            if (hasAmmo(attacker)) {
                ammo = unitMap.get(attacker).getAmmunition();
            }
        }

        // if the defending unit is out of range, then
        // return failure
        if (dist > range) {
            return StateType.returnFailure("Error: defender unit is"
                    + " out of range");
        }

        // retrieves result of attacking method
        // and returns the result if applicable
        StateType result;

        // if the attacker happens to have no ammunition left,
        // is a melee unit, or is in melee range, then the
        // unit should attack with melee
        if (isMelee) {
            result = attacker.attackWithMelee(defender, areaBonus);
        } else {
            result = attacker.attackWithRange(defender, areaBonus);
        }

        // if failure is found, then return failure
        if (StateType.checkIfFailure(result)) {
            return result;
        }

        // inflicts charge damage on the defending unit separately
        // from the main unit's MELEE attack, increasing linearly
        // with the length of the path taken and doubled if
        // the unit is a cavalry unit
        int bonus = 1;
        int cavalryBonus = 2;

        if (attacker.getUnitType() == Unit.UnitType.CAVALRY)
            bonus = cavalryBonus;

        if (isMelee) {
            int bonusDamage =(int) (bonus * pathLength * areaBonus);
            result = defender.damageUnit(bonusDamage);
        }

        // if failure is found, then return failure
        if (StateType.checkIfFailure(result)) {
            return result;
        }

        // decreases ammunition count of attacker unit, if it
        // attacked with range
        if (!isMelee) {
            // replaces ammunition value for given unit
            if (hasAmmo(attacker)) {
                unitMap.get(attacker).setAmmunition(ammo - 1);
            }
        }

        // handles defender unit deletion, if destroyed by attacker
        // unit
        if (StateType.checkIfDestroy(result)) {
            unitMap.remove(defender);
            battlefield.removeUnit(defendID);
        }

        return result;
    }

    /**
     * Subtracts the cost of moving a unit a certain distance
     * or prevents the user from moving if they don't have
     * enough movement points.
     * @param pathCost, corresponding to cost of moving
     *                  a unit along a certain path.
     * @param unit, corresponding to unit that is moving.
     * @return StateType
     */
    public StateType handleMovement(Unit unit, int pathCost) {
        if (unitMap.containsKey(unit)) {
            // gets movement points left for unit
            int movesLeft = unitMap.get(unit).getMovePoints();

            // if the unit doesn't have enough movement
            // points, return failure and prevent user from moving
            // unit
            if (movesLeft < pathCost) {
                return StateType.returnFailure("Error: Not enough " +
                        "movement points!");
            }

            // subtracts unit movement cost
            movesLeft -= pathCost;
            unitMap.get(unit).setMovePoints(movesLeft);
        } else {
            return StateType.returnFailure("Error: unit binding" +
                    " in movement table not found!");
        }

        return StateType.returnSuccess(null);
    }

    /**
     * Checks if two units are within melee attacking
     * distance, intended for ranged units that move
     * too close to (directly neighboring) another unit.
     * @param euclidDistance, corresponding to the distance between
     *                        a ranged unit and another unit.
     * @return boolean
     */
    private boolean isMeleeRange(double euclidDistance) {
        return euclidDistance <= Math.sqrt(2);
    }

    /**
     * Checks if a given unit has ammunition left.
     * @param unit, corresponding to the ranged unit.
     * @return boolean
     */
    private boolean hasAmmo(Unit unit) {
        if (!unitMap.containsKey(unit)) {
            return false;
        }

        return unitMap.get(unit).getAmmunition() > 0;
    }

    /**
     * Returns ammunition a unit has left.
     * @param unit, corresponding to the ranged unit.
     * @return int
     */
    public int unitAmmunition(Unit unit) {
        if (!unit.isRangedUnit()) {
            return 0;
        }

        return unitMap.get(unit).getAmmunition();
    }

    /**
     * Returns amount of movement points a unit has left.
     * @param unit, corresponding to said unit.
     * @return int
     */
    public int unitMovement(Unit unit) {
        return unitMap.get(unit).getMovePoints();
    }

    /**
     * Returns number of turns special ability has
     * left.
     * @param unit, corresponding to selected unit.
     * @return int
     */
    public int unitTurns(Unit unit) {
        return unitMap.get(unit).getSpecialTurns();
    }

    /**
     * Checks if a unit should attack with melee or
     * ranged attacks.
     * @param attacker, corresponding to attacking unit.
     * @param range, corresponding to distance between two units
     * @return boolean
     */
    private boolean checkIfMelee(Unit attacker, double range) {
        if (!attacker.isRangedUnit())
            return true;

        if (isMeleeRange(range))
            return true;

        return !hasAmmo(attacker);
    }

    /**
     * Gets the actual range of a unit.
     * @param attacker, corresponding to attacking unit.
     * @return double
     */
    private double getRangeOfUnit(Unit attacker) {
        // if the unit is a melee unit, simply return
        // its range factor * sqrt(2) (the range factor
        // will never change)
        if (!attacker.isRangedUnit())
            return attacker.getRangeFactor() * Math.sqrt(2);

        // if the ranged unit has no ammunition left,
        // it should only have as much range as a melee
        // unit
        if (!hasAmmo(attacker)) {
            return Math.sqrt(2);
        }

        // return ranged unit's full range
        return attacker.getRangeFactor() * Math.sqrt(2);
    }
}
