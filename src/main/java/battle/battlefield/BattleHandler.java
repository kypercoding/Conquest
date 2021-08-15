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
    /** HashMap holding the amount of movement points a unit has
     *  left. */
    private HashMap<Unit, Integer> movePoints;

    /** HashMap holding the amount of ammunition a ranged unit
     * has left */
    private HashMap<Unit, Integer> ammunition;

    /**

    /**
     * Initializes the hash maps for a given
     * array of units.
     * @param units, an array of Unit objects
     */
    private void initializeMaps(Unit[] units, int maxMovement,
                                int maxAmmo) {
        // initializes the two HashMaps
        movePoints = new HashMap<>();
        ammunition = new HashMap<>();

        // adds each unit to both ammo and movement
        // HashMaps
        for (Unit unit : units) {
            // gives each unit a certain amount of movement
            // points
            movePoints.put(unit, maxMovement);

            // gives each RANGED unit a certain amount of
            // ammunition
            if (unit.isRangedUnit()) {
                ammunition.put(unit, maxAmmo);
            }
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
                         int maxAmmo, int maxMovement) {
        // adds attackers and defenders to movement point and
        // ammunition hash maps
        initializeMaps(attackers, maxMovement, maxAmmo);
        initializeMaps(defenders, maxMovement, maxAmmo);
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
            // ensures that unit map exists
            Object value = movePoints.replace(unit, maxMovement);

            // if no mapping is somehow found, then return failure
            if (value == null) {
                return StateType.returnFailure("Error: no mapping " +
                        "found during movement reset");
            }
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

        // calculates the euclidean distance between two
        // units to ensure that a unit attacks another
        // unit within a valid range
        double dist = GridCalculations.getEuclidDistance(attackID,
                defendID, n);

        // calculates actual range of a given unit, set automatically
        // to Math.sqrt(2) if it is a ranged unit with no more
        // ammunition, a melee unit, or a unit that directly
        // neighbors the unit
        double range = 0;
        int ammo = 0;
        boolean isMelee = checkIfMelee(attacker, range);

        if (!isMelee) {
            // retrieves the ranged unit's ammunition amount
            // from the HashMap, if the unit exists in
            // the HashMap. If it doesn't, then return
            // failure
            if (ammunition.containsKey(attacker) && hasAmmo(attacker)) {
                ammo = ammunition.get(attacker);
            } else {
                // reports a missing key bind when there was
                // supposed to be one
                if (ammunition.containsKey(attacker)) {
                    return StateType.returnFailure("Error: ranged " +
                            "unit lacks proper ammunition binding");
                }
            }
        } else {
            range = Math.sqrt(2);
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

        // decreases ammunition count of attacker unit, if it is a
        // ranged unit
        if (attacker.isRangedUnit()) {
            // replaces ammunition value for given unit
            if (ammunition.containsKey(attacker) && !hasAmmo(attacker)) {
                // prevents unnecessary subtraction when
                // a unit runs out of ammo
                if (ammo > 0) {
                    ammunition.replace(attacker, ammo - 1);
                }
            } else {
                // reports a missing key bind (when it was supposed
                // to be there)
                if (ammunition.containsKey(attacker)) {
                    return StateType.returnFailure("Error: " +
                            "Ammunition binding for unit does" +
                            " not exist");
                }
            }
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
            result = defender.damageUnit(bonus * pathLength);
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
        if (movePoints.containsKey(unit)) {
            // gets movement points left for unit
            int movesLeft = movePoints.get(unit);

            // if the unit doesn't have enough movement
            // points, return failure and prevent user from moving
            // unit
            if (movesLeft < pathCost) {
                return StateType.returnFailure("Error: Not enough " +
                        "movement points!");
            }

            // subtracts unit movement cost
            movePoints.replace(unit, (movesLeft - pathCost));
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
        if (!ammunition.containsKey(unit)) {
            return false;
        }

        return ammunition.get(unit) == 0;
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

        return ammunition.get(unit);
    }

    /**
     * Returns amount of movement points a unit has left.
     * @param unit, corresponding to said unit.
     * @return int
     */
    public int unitMovement(Unit unit) {
        return movePoints.get(unit);
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
}
