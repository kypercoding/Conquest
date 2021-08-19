package battle.interfaces;

import testing.unittesting.StateType;

public interface Unit {
    public enum UnitType {INFANTRY, CAVALRY, ARTILLERY};

    /**
     * Returns name of the unit.
     * @return String
     */
    public String getUnitName();

    /**
     * Returns number of soldiers in unit.
     * @return int
     */
    public int getNumber();

    /**
     * Sets number of soldiers in unit.
     * @param number
     */
    public void setNumber(int number);

    /** Returns the maximum amount of soldiers you can have in the
     * unit.
     * @return int
     */
    public int getLimit();

    /**
     * Returns cost of buying unit.
     * @return int
     */
    public int getUnitCost();

    /**
     * Returns armor defense points of unit (the amount of
     * damage an attack will decrease by a constant amount).
     * @return int
     */
    public int getArmor();

    /**
     * Returns the amount of melee damage a unit will inflict.
     * @return int
     */
    public int getMeleeDamage();

    /**
     * Returns the amount of ranged damage a unit will inflict.
     * @return int
     */
    public int getRangedDamage();

    /**
     * Returns the amount of points a unit will require to move
     * one spot on a battefield map.
     * @return int
     */
    public int getMovementCost();

    /**
     * Returns the number of diagonal squares used in making
     * up a unit's projectile range (if the unit is a melee
     * unit, the method should simply return 1).
     * @return int
     */
    public int getRangeFactor();

    /**
     * Returns a summary of a unit contained in String form.
     * @return String
     */
    public String getUnitSummary();

    /**
     * Damages a unit by a certain amount of damage. Damage
     * may be decreased depending on unit's armor points and
     * unit-specific defensive bonus.
     * Returns SUCCESS if no specific errors are encountered,
     * Returns DESTROY if the unit number goes below 0,
     * Returns FAILURE if error is encountered.
     * @param damage
     * @return StateType
     */
    public StateType damageUnit(int damage);

    /**
     * Attacks an enemy unit with the current unit object.
     * Returns SUCCESS if no specific errors are encountered,
     * Returns DESTROY if the unit number goes below 0,
     * Returns FAILURE if error is encountered.
     * @param unit
     * @param areaBonus
     * @return StateType
     */
    public StateType attackWithRange(Unit unit, double areaBonus);

    /**
     * Attacks an enemy unit with the current unit object
     * through its melee damage.
     * Returns SUCCESS if no specific errors are encountered,
     * Returns DESTROY if the unit number goes below 0,
     * Returns Failure if error is encountered.
     * @param unit
     * @param areaBonus
     * @return StateType
     */
    public StateType attackWithMelee(Unit unit, double areaBonus);

    /**
     * Activate's a certain unit class's special ability, whether
     * it be a decrease in movement cost, an increase in attack
     * damage, or an increase in defensive bonus.
     */
    public void activateSpecialAbility();

    /**
     * Deactivates a certain unit class's special ability, usually
     * after a certain amount of time has passed.
     */
    public void deactivateSpecialAbility();

    /**
     * Checks if a unit's special ability is activated.
     * @return boolean
     */
    public boolean specialIsActivated();

    /**
     * Checks if unit is a ranged or melee unit.
     * @return boolean
     */
    public boolean isRangedUnit();

    /**
     * Sets number of soldiers in unit back to
     * full capacity (limit).
     */
    public void refill();

    /**
     * Checks the UnitType of a unit.
     * @return UnitType
     */
    public UnitType getUnitType();

    /**
     * Makes a copy of the given unit
     * @return Unit
     */
    public Unit copyUnit();
}