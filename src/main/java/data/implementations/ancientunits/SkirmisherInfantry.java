package data.implementations.ancientunits;

import data.interfaces.Unit;

import java.util.Random;

public class SkirmisherInfantry implements Unit {
    /** name of the ranged infantry unit (i.e. archers,
     *  javelins, etc). */
    private final String name;
    /** number of soldiers in ranged infantry unit. */
    private int number;
    /** maximum possible number of soldiers in the unit. */
    private final int limit;
    /** cost of buying this new unit. */
    private final int unitCost;

    /** amount of melee damage the unit deals. */
    private final int meleeDamage;
    /** maximum amount of ranged damage the unit deals. */
    private final int rangedDamage;
    /** amount of armor the unit has. */
    private final int armor;
    /** movement cost for moving one space on the battle board. */
    private final int movementCost;

    /** number of diagonal squares that make up range of unit. */
    private final int rangeFactor;
    /** how much extra attack the special ability provides, as
     *  a percent increase. */
    private final double attackBonus;
    /** boolean checking whether or not the unit's special ability
     * is activated. */
    private boolean isActivated;

    /**
     * Constructs a new MeleeInfantry object.
     * @param name is the name of the unit.
     * @param limit is the maximum possible number of soldiers.
     * @param unitCost is the cost of the unit.
     * @param meleeDamage is the melee damage inflicted on enemy
     *                    units.
     * @param rangedDamage is the maximum possible ranged damage
     *                     inflicted on enemy units.
     * @param armor is the natural defense of the unit.
     * @param movementCost is how many movement points are
     *                     required to move a unit from one space to
     *                     another.
     * @param rangeFactor is how many diagonal squares make up the
     *                    total range of the archers.
     * @param attackBonus is the MeleeInfantry unit's special defense
     *                     bonus (its value).
     */
    public SkirmisherInfantry(final String name, final int limit,
                              final int unitCost, final int meleeDamage,
                              final int rangedDamage,
                              final int armor,
                              final int movementCost,
                              final int rangeFactor,
                              final double attackBonus) {
        this.name = name;
        this.number = limit;
        this.limit = limit;
        this.unitCost = unitCost;
        this.meleeDamage = meleeDamage;
        this.rangedDamage = rangedDamage;
        this.armor = armor;
        this.movementCost = movementCost;
        this.rangeFactor = rangeFactor;
        this.attackBonus = attackBonus;
        this.isActivated = false;
    }

    /**
     * Retrieves the RangedInfantry unit's name in String form.
     * No parameters are taken in, returns a String of the unit
     * name.
     * @return name of the unit in string form.
     */
    public String getUnitName() {
        return this.name;
    }

    /**
     * Retrieves the RangedInfantry unit's number as an int.
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
     * @return limit in int form
     */
    public int getLimit() {
        return this.limit;
    }

    /**
     * Returns the cost of buying the unit.
     * @return unitCost in int form
     */
    public int getUnitCost() {
        return this.unitCost;
    }

    /**
     * Returns the cost of moving the unit across the board.
     * @return movementCost in int form
     */
    public int getArmor() {
        return this.armor;
    }

    public int getMeleeDamage() {
        return this.meleeDamage;
    }

    public int getRangedDamage() {
        return this.rangedDamage;
    }

    /**
     * Returns the cost of moving the unit across the board.
     * @return movementCost in int form
     */
    public int getMovementCost() {
        return this.movementCost;
    }

    /**
     * Returns the range factor (how many diagonal squares
     * make up the attack range of the unit).
     * @return range factor in int form
     */
    public int getRangeFactor() {
        return this.rangeFactor;
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
    public void damageUnit(final int damage) {
        int finalDamage = damage - this.armor;
        this.number -= damage;
    }

    /**
     * Damages the given unit with a ranged attack,
     * taking into account any possible defense bonuses.
     * @param unit is enemy unit.
     * @param areaBonus (of enemy unit).
     * @return true if attack runs with no errors
     *         or false if the attack fails
     */
    public boolean attackWithRange(final Unit unit, final double areaBonus) {
        // calculates damage inflicted on enemy
        // based on range of possible damages and Java's Random type
        Random random = new Random();
        double damage = random.nextInt(this.rangedDamage);

        // inflicts more damage onto the enemy unit if the
        // special ability is activated
        if (this.isActivated) {
            damage *= this.attackBonus;
        }

        // applies area bonus of unit to reduce damage
        damage *= areaBonus;

        // inflicts damage onto enemy unit
        unit.damageUnit((int) damage);

        return true;
    }

    /**
     * Damages the given unit with a melee attack,
     * taking into account any possible defense bonuses.
     * @param unit is enemy unit.
     * @param areaBonus (of enemy unit).
     * @return true if attack is executed without error or false
     * if an issue arises
     */
    public boolean attackWithMelee(final Unit unit,
                                   final double areaBonus) {
        // check validation to ensure melee attacks are valid

        // computes the damage after taking into account terrain
        // defenses
        double damage = this.meleeDamage * areaBonus;

        // lowers the enemy unit by a certain amount of damage or
        // less, depending on if other unit has defense bonus
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
     * @return true since the unit is a ranged unit.
     */
    public boolean isRangedUnit() {
        return true;
    }

    /**
     * Replenishes the unit back to full capacity.
     */
    public void refill() {
        this.number = this.limit;
    }
}
