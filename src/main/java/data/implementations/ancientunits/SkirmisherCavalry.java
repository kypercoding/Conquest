package data.implementations.ancientunits;

import data.interfaces.Unit;

import java.util.Random;

public class SkirmisherCavalry implements Unit {
    /** name of the skirmisher cavalry unit (i.e. skirmisher cavalry, horse archers). */
    private final String name;
    /** number of soldiers in skirmisher cavalry unit. */
    private int number;
    /** maximum possible number of soldiers in the unit. */
    private final int limit;
    /** cost of buying this new unit. */
    private final int unitCost;

    /** amount of melee damage the unit deals */
    private final int meleeDamage;
    /** maximum amount of ranged damage the unit deals. */
    private final int rangedDamage;
    /** amount of armor the unit has (lowers damage inflicted by a constant
     *  amount). */
    private final int armor;
    /** movement cost for moving one space on the battle board. */
    private final int movementCost;

    /** number of diagonal squares that make up range of unit. */
    private final int rangeFactor;
    /** ammunition that unit has left (how many attacks you can
     * do as a ranged unit before you must use melee attack). */
    private int ammunition;
    /** how much defense the special ability provides, as
     *  a percentage of how much attack damage is reduced. */
    private final double movementBonus;
    /** boolean checking whether or not the unit's special ability
     * is activated. */
    private boolean isActivated;

    public SkirmisherCavalry(final String name, final int limit, final int unitCost,
                 final int meleeDamage, final int rangedDamage, final int armor,
                 final int movementCost, final int rangeFactor,
                 final double movementBonus) {
        this.name = name;
        this.limit = limit;
        this.number = limit;
        this.unitCost = unitCost;
        this.meleeDamage = meleeDamage;
        this.rangedDamage = rangedDamage;
        this.armor = armor;
        this.movementCost = movementCost;
        this.rangeFactor = rangeFactor;
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

    public int getMovementCost() {
        // reduces movement cost to certain percentage (movementBonus),
        // allowing skirmisher cavalry unit to travel more distance
        if (this.isActivated) {
            return (int) this.movementBonus * this.movementCost;
        }

        return this.movementCost;
    }

    public int getRangeFactor() {
        return this.rangeFactor;
    }

    public String getUnitSummary() {
        return null;
    }

    public void damageUnit(int damage) {
        int finalDamage = damage - this.armor;
        this.number -= finalDamage;
    }

    public boolean attackWithRange(Unit unit, double areaBonus) {
        // calculates damage inflicted on enemy
        // based on range of possible damages and Java's Random type
        Random random = new Random();
        double damage = random.nextInt(this.rangedDamage);

        // applies area bonus of unit to reduce damage
        damage *= areaBonus;

        // inflicts damage onto enemy unit
        unit.damageUnit((int) damage);

        return true;
    }

    public boolean attackWithMelee(Unit unit, double areaBonus) {
        double damage = this.meleeDamage * areaBonus;
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
        return true;
    }

    public void refill() {
        this.number = this.limit;
    }
}
