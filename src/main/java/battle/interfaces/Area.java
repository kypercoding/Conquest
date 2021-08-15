package battle.interfaces;

public interface Area {
    enum AreaType {
        NORMAL, HILL, MOUNTAIN, WATER, WALL
    }

    /**
     * Retrieves the integer id of a certain area.
     * @return int
     */
    public int getAreaID();

    /**
     * Retrieves an area's defense bonus.
     * @return double
     */
    public double getDefenseBonus();

    /**
     * Returns
     * @return
     */
    // returns area's terrain type (normal, hill, mountain, water, wall)
    public String getAreaType();

    // changes whether a unit can move into the area
    public void changeStatus(boolean isOpen);

    // checks if a unit can move into the area
    public boolean isOpen();
}
