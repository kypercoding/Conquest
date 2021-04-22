package data.interfaces;

public interface Unit {
    // getter method: returns the name of the unit
    public String getUnitName();

    // getter method: returns the number of soldiers left in a unit
    public int getNumber();

    // setter method: sets the number of soldiers left in a unit
    public void setNumber(int number);

    // getter method: returns the maximum amount of soldiers you can have in the unit
    public int getLimit();

    // getter method: returns the cost of buying the entire unit
    public int getUnitCost();

    // getter method: returns the armor defense of the unit
    public int getArmor();

    // getter method: returns the movement cost of the unit
    public int getMovementCost();

    // getter method: return the range factor of unit (the range factor is multiplied by sqrt(2) to get a full range)
    public int getRangeFactor();

    // getter method: return a summary of the unit in String form
    public String getUnitSummary();

    // battle method: subtracts soldiers using damage parameter
    public void damageUnit(int damage);

    // battle method: allows unit to attack with ranged options
    public boolean attackWithRange(Unit unit, double areaBonus);

    // battle method: allows unit to attack with melee options
    public boolean attackWithMelee(Unit unit, double areaBonus);

    // battle method: use special ability
    public void activateSpecialAbility();

    // battle method: deactivate special ability
    public void deactivateSpecialAbility();

    // battle method: checks if unit is a ranged unit
    public boolean isRangedUnit();

    // general method: refill the unit
    public void refill();
}