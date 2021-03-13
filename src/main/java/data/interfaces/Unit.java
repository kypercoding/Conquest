package data.interfaces;

public interface Unit {
    // getter method: returns the name of the unit
    public String getUnitName();

    // getter method: returns the number of soldiers left in a unit
    public int getNumber();

    // getter method: returns the maximum amount of soldiers you can have in the unit
    public int getLimit();

    // getter method: returns the cost of buying the entire unit
    public int getUnitCost();

    // getter method: returns the cost of refilling the unit
    public int getRefillCost();

    // getter method: returns the movement cost of the unit
    public int getMovementCost();

    // getter method: returns experience level of unit
    public int getExperienceLevel();

    // getter method: return the range factor of unit (the range factor is multiplied by sqrt(2) to get a full range)
    public int getRangeFactor();

    // getter method: return a summary of the unit in String form
    public String getUnitSummary();

    // battle method: subtracts soldiers using damage parameter
    public void damageUnit(int damage);

    // battle method: allows unit to attack with ranged options
    public void attackWithRange(Unit unit, int areaBonus);

    // battle method: allows unit to attack with melee options
    public void attackWithMelee(Unit unit, int areaBonus);

    // battle method: use special ability
    public void activateSpecialAbility();

    // battle method: checks if unit is a ranged unit
    public boolean isRangedUnit();

    // battle method: increments the experience level of a unit by 1
    public void raiseExperienceLevel();

    // general method: refill the unit
    public void refill();
}