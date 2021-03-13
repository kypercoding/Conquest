package data.interfaces;

public interface Area {
    // retrieves area's defense bonus (by what percent will it reduce an attacker's damage)
    public double getDefenseBonus();

    // returns area's terrain type (normal, hill, mountain, water, wall)
    public String getAreaType();

    // changes whether or not a unit can move into the area
    public void changeStatus(boolean isOpen);

    // checks if a unit can move into the area
    public boolean isOpen();


}
