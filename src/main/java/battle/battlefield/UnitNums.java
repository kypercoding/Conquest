package battle.battlefield;

/**
 * Class instantiating three integers into one object.
 * User has access to a unit's ammunition, movement points,
 * and special ability turns it has left.
 */
public class UnitNums {
    /** Holds the amount of ammunition a unit has left,
     * if applicable. */
    private int ammunition;
    /** Holds amount of movement points a unit has left. */
    private int movePoints;
    /** Holds amount of time a special ability is active for. */
    private int specialTurns;

    /** Instantiates a new UnitNums object. */
    public UnitNums(int ammunition, int movePoints, int specialTurns) {
        this.ammunition = ammunition;
        this.movePoints = movePoints;
        this.specialTurns = specialTurns;
    }

    /**
     * Returns ammunition left.
     * @return int
     */
    public int getAmmunition() {
        return this.ammunition;
    }

    /**
     * Returns movement points left.
     * @return int
     */
    public int getMovePoints() {
        return this.movePoints;
    }

    /**
     * Returns number of turns a special ability is on for left.
     * @return int
     */
    public int getSpecialTurns() {
        return this.specialTurns;
    }

    /**
     * Sets ammunition left.
     * @param ammunition, or ammo the unit has left.
     */
    public void setAmmunition(int ammunition) {
        this.ammunition = ammunition;
    }

    /**
     * Sets movement points left.
     * @param movePoints, the amount of movement points left.
     */
    public void setMovePoints(int movePoints) {
        this.movePoints = movePoints;
    }

    /**
     * Sets special ability turns left.
     * @param specialTurns, the amount of turns left for special
     *                      ability.
     */
    public void setSpecialTurns(int specialTurns) {
        this.specialTurns = specialTurns;
    }


}
