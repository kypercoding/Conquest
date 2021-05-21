package data.implementations.ancientunits;

import data.interfaces.Unit;

public class MeleeCavalry implements Unit {
    /** name of the melee cavalry unit (i.e. lancers, cataphracts). */
    private final String name;
    /** number of soldiers in melee cavalry unit. */
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
    private final double movementBonus;
    /** boolean checking whether or not the unit's special ability
     * is activated. */
    private boolean isActivated;

    public MeleeCavalry(final String name, final int limit, final int unitCost,
                        final int meleeDamage, final int armor,
                        final int movementCost, final double movementBonus) {
        this.name = name;
        this.limit = limit;
        this.number = limit;
        this.unitCost = unitCost;
        this.meleeDamage = meleeDamage;
        this.armor = armor;
        this.movementCost = movementCost;
        this.movementBonus = movementBonus;
    }

    public String getUnitName() {
        return this.name;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getLimit() {
        return this.limit;
    }

    public int getUnitCost() {
        return this.unitCost;
    }

    public int getArmor() {
        return this.armor;
    }

    public int getMeleeDamage() {
        return this.meleeDamage;
    }
    
    public int getRangedDamage() {
        return 0;
    }

    public int getMovementCost() {
        return this.movementCost;
    }

    public int getRangeFactor() {
        return 1;
    }

    public String getUnitSummary() {
        return null;
    }

    public void damageUnit(int damage) {
        int finalDamage = damage - this.armor;
        this.number -= finalDamage;
    }

    public boolean attackWithRange(Unit unit, double areaBonus) {
        return true;
    }

    public boolean attackWithMelee(Unit unit, double areaBonus) {
        // add possible validations to ensure melee attacks work or not

        // computes the damage after taking into account terrain
        // defenses
        double damage = this.meleeDamage * areaBonus;

        // lowers the enemy unit by a certain amount of damage or
        // more, depending on if other unit has defense bonus
        unit.damageUnit((int) damage);

        return true;
    }

    public void activateSpecialAbility() {
        this.isActivated = true;
    }

    public void deactivateSpecialAbility() {
        this.isActivated = false;
    }

    public boolean isRangedUnit() {
        return false;
    }

    public void refill() {
        this.number = this.limit;
    }
}
