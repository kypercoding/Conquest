package data.implementations.ancientunits;

import data.interfaces.Unit;
import data.unittesting.StateType;

/**
* MeleeInfantry class
*
* The class represents a ranged infantry unit type that implements the
* Unit interface.
*
* Constructor takes in a String name, int limit, int cost,
* int meleeDamage, and double abilityBonus, usually from
* Scanner class file.
*
* The special ability of the MeleeInfantry is the shield wall.
* Unlike the special ability of SpearInfantry (phalanx), the
* shield wall allows more mobility. However, in return, the
* ability generally provides a lower defense bonus and
* does not provide a specific bonus against cavalry
*
*
* */

public class MeleeInfantry implements Unit {
    /** name of the melee infantry unit (i.e. swords, elite swords). */
    private final String name;
    /** number of soldiers in melee infantry unit. */
    private int number;
    /** maximum possible number of soldiers in the unit. */
    private final int limit;
    /** cost of buying this new unit. */
    private final int unitCost;

    /** amount of melee damage the unit deals
       (no ranged damage for melee unit). */
    private final int meleeDamage;
    /** amount of armor the unit has (lowers damage inflicted by a constant
     *  amount). */
    private final int armor;

    /** movement cost for moving one space on the battle board. */
    private final int movementCost;
    /** how much defense the special ability provides, as
     *  a percentage of how much attack damage is reduced. */
    private final double defenseBonus;
    /** boolean checking whether or not the unit's special ability
     * is activated. */
    private boolean isActivated;

    /**
     * Constructs a new MeleeInfantry object.
     * @param name is the name of the unit
     * @param limit is the maximum possible number of soldiers
     * @param unitCost is the cost of the unit
     * @param meleeDamage is the melee damage inflicted on enemy
     *                    units
     * @param armor is the natural defense of the unit
     * @param movementCost is how many movement points are
     *                     required to move a unit from one space to
     *                     another
     * @param defenseBonus is the MeleeInfantry unit's special defense
     *                     bonus (its value)
     */
    public MeleeInfantry(final String name, final int limit, final int unitCost,
                         final int meleeDamage, final int armor,
                         final int movementCost, final double defenseBonus) {
        this.name = name;
        this.number = limit;
        this.limit = limit;
        this.unitCost = unitCost;
        this.meleeDamage = meleeDamage;
        this.armor = armor;
        this.movementCost = movementCost;
        this.defenseBonus = defenseBonus;
        this.isActivated = false;
    }

    /**
     * Retrieves the MeleeInfantry unit's name in String form.
     * No parameters are taken in, returns a String of the unit
     * name.
     * @return name of the unit.
     */
    public String getUnitName() {
        return this.name;
    }

    /**
     * Retrieves the MeleeInfantry unit's number as an int.
     * @return number of soldiers in unit.
     */
    public int getNumber() {
        return this.number;
    }

    /**
     * Sets the number of soldiers in a unit.
     * @param number is the number of soldiers in the unit
     */
    public void setNumber(final int number) {
        this.number = number;
    }

    /**
     * Returns unit's soldier limit.
     * @return limit
     */
    public int getLimit() {
        return this.limit;
    }

    /**
     * Returns the cost of buying the unit.
     * @return unitCost
     */
    public int getUnitCost() {
        return this.unitCost;
    }

    /**
     * Returns the cost of moving the unit across the board.
     * @return movementCost
     */
    public int getArmor() {
        return this.armor;
    }

    public int getMeleeDamage() {
        return this.meleeDamage;
    }

    public int getRangedDamage() {
        return 0;
    }

    /**
     * Returns the cost of moving the unit across the board.
     * @return movementCost
     */
    public int getMovementCost() {
        return this.movementCost;
    }

    /**
     * Returns the range factor (how many diagonal squares
     * make up the attack range of the unit).
     * @return 1 (melee troops do not have ranged attacks)
     */
    public int getRangeFactor() {
        return 1;
    }

    /**
     * Condenses all relevant unit information (name, number, etc)
     * into a string.
     * @return string containing relevant unit fields, equivalent
     * to Java's toString() method.
     */
    public String getUnitSummary() {
        return null;
    }

    /**
     * Damages the given unit with an attack,
     * taking into account any possible defense bonuses.
     * @param damage inflicted onto unit from enemy
     */
    public StateType damageUnit(final int damage) {
        // checks whether parameter is invalid
        if (damage < 0) {
            return StateType.returnFailure("Error: damage parameter invalid");
        }

        int finalDamage = damage - this.armor;

        // if armor is sufficient enough to block attack
        // completely, then the unit is undamaged
        if (finalDamage <= 0) {
            return StateType.returnSuccess(null);
        }

        // applies defense bonus to reduce damage received
        if (this.isActivated) {
            finalDamage *= defenseBonus;
        }

        if (finalDamage <= 0) {
            return StateType.returnSuccess(null);
        }

        // subtracts unit number by the final damage inflicted
        this.number -= finalDamage;

        if (this.number <= 0) {
            return StateType.returnDestroy("Note: Unit has been destroyed");
        }

        // method success
        return StateType.returnSuccess(null);
    }

    /**
     * Damages the given unit with a ranged attack,
     * taking into account any possible defense bonuses.
     * @param unit is enemy unit.
     * @param areaBonus (of enemy unit).
     * @return false if the attack fails, or if the method
     *         doesn't apply (such as here).
     */
    public StateType attackWithRange(Unit unit, final double areaBonus) {
        return StateType.returnSuccess(null);
    }

    /**
     * Damages the given unit with a melee attack,
     * taking into account any possible defense bonuses.
     * @param unit is enemy unit.
     * @param areaBonus (of enemy unit).
     * @return true if attack is executed without error or false
     * if an issue arises
     */
    public StateType attackWithMelee(Unit unit, final double areaBonus) {
        // validating parameters
        if (unit == null) {
            return StateType.returnFailure("Error: null unit param");
        }

        if (areaBonus < 0 || areaBonus > 1) {
            return StateType.returnFailure("Error: invalid area param");
        }

        // computes the damage after taking into account terrain
        // defenses
        double damage = this.meleeDamage * areaBonus;

        // lowers the enemy unit by a certain amount of damage or
        // more, depending on if other unit has defense bonus
        return unit.damageUnit((int) damage);
    }

    /**
     * Activates the special ability of the unit.
     */
    public void activateSpecialAbility() {
        this.isActivated = true;
    }

    /**
     * Deactivates the special ability of the unit.
     */
    public void deactivateSpecialAbility() {
        this.isActivated = false;
    }

    /**
     * Checks if a given unit is ranged or not.
     * @return false since the unit is not a ranged unit.
     */
    public boolean isRangedUnit() {
        return false;
    }

    /**
     * Replenishes the unit back to full capacity.
     */
    public void refill() {
        this.number = this.limit;
    }
}
