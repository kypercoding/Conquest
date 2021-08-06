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
     * Initializes the hash maps for a given
     * array of units.
     * @param units, an array of Unit objects
     */
    private void initializeMaps(Unit[] units, int maxMovement,
                                int maxAmmo) {
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

        // inflicts damage onto defender unit
        int damage;

        // calculates the euclidean distance between two
        // units to ensure that a unit attacks another
        // unit within a valid range
        double dist = GridCalculations.getEuclidDistance(attackID,
                defendID, n);

        // calculates actual range of a given unit
        double range = attacker.getRangeFactor() * Math.sqrt(2);

        // if the defending unit is out of range, then
        // return failure
        if (dist > range) {
            return StateType.returnFailure("Error: defender unit is"
                    + " out of range");
        }
        
        // sets initial damage to either ranged or melee damage,
        // depending on unit
        if (attacker.isRangedUnit()) {
            // damage inflicted by a ranged unit is automatically
            // set to melee damage if the ranged unit is too close
            if (isMeleeRange(dist)) {
                damage = attacker.getMeleeDamage();
            } else {
                damage = attacker.getRangedDamage();
            }
        } else {
            damage = attacker.getMeleeDamage();
        }

        // if the unit is a cavalry unit that attacks via melee
        // damage, then the cavalry unit will receive an extra
        // melee bonus, increasing linearly with the distance
        // it traversed
        if (attacker.getUnitType() == Unit.UnitType.CAVALRY) {
            if (!attacker.isRangedUnit() || isMeleeRange(dist)) {
                damage += pathLength;
            }
        }

        // retrieves area defense bonus from defending unit's area
        // and decreases damage accordingly
        double defBonus = battlefield.getArea(defendID)
                .getDefenseBonus();
        damage *= defBonus;

        // if damage happens to drop into a negative number,
        // then return failure
        if (damage < 0) {
            return StateType.returnFailure("Error: negative damage dealt");
        }

        // returns result of the calculation to be handled later
        return defender.damageUnit(damage);
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
}
