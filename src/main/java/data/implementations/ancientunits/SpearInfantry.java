package data.implementations.ancientunits;

import data.interfaces.Unit;

public class SpearInfantry implements Unit {
    /** name of the spear infantry unit (i.e. levy spears, hoplites). */
    private final String name;
    /** number of soldiers in spear infantry unit. */
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

    /** ammunition that unit has left (how many attacks you can
     * do as a ranged unit before you must use melee attack). */
    private int ammunition;
    /** how much defense the special ability provides, as
     *  a percentage of damage remaining (i.e. a defense bonus
     *  of 80% reduces damage TO 80% of its lethality). */
    private final double defenseBonus;
    /** boolean checking whether or not the unit's special ability
     * is activated. */
    private boolean isActivated;

    public SpearInfantry(final String name, final int limit, final int unitCost,
                final int meleeDamage, final int armor,
                final int movementCost, final double defenseBonus) {
        this.name = name;
        this.limit = limit;
        this.number = limit;
        this.unitCost = unitCost;
        this.meleeDamage = meleeDamage;
        this.armor = armor;
        this.movementCost = movementCost;
        this.defenseBonus = defenseBonus;
        this.isActivated = false;
    }

    /**
     * Retrieves the SpearInfantry unit's name in String form.
     * No parameters are taken in, returns a String of the unit
     * name.
     * @return name of the unit.
     */
    public String getUnitName() {
        return this.name;
    }

    /**
     * Retrieves the SpearInfantry unit's number as an int.
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
     * Returns the cost of moving the unit across a single space on the board.
     * @return movementCost
     */
    public int getMovementCost() {
        return this.movementCost;
    }

    /**
     * Returns the range factor (how many diagonal squares
     * make up the attack range of the unit).
     * @return 1 (spear troops do not have ranged attacks)
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
     * taking into account any possible defense bonuses or
     * special abilities.
     * @param damage inflicted onto unit from enemy
     */
    public void damageUnit(final int damage) {
        double finalDamage = damage;

        finalDamage -= this.armor;

        if (this.isActivated) {
            finalDamage *= defenseBonus;
        }

        this.number -= (int) finalDamage;
    }

    /**
     * Damages the given unit with a ranged attack,
     * taking into account any possible defense bonuses.
     * @param unit is enemy unit.
     * @param areaBonus (of enemy unit).
     * @return false if the attack fails, or if the method
     *         doesn't apply (such as here).
     */
    public boolean attackWithRange(final Unit unit, final double areaBonus) {
        return false;
    }

    /**
     * Damages the given unit with a melee attack,
     * taking into account any possible defense bonuses.
     * @param unit is enemy unit.
     * @param areaBonus (of enemy unit).
     * @return true if attack is executed without error or false
     * if an issue arises
     */
    public boolean attackWithMelee(final Unit unit, final double areaBonus) {
        // add possible validations to ensure melee attacks work or not

        // computes the damage after taking into account terrain
        // defenses
        double damage = this.meleeDamage * areaBonus;

        // lowers the enemy unit by a certain amount of damage or
        // more, depending on if other unit has defense bonus
        unit.damageUnit((int) damage);

        return true;
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
