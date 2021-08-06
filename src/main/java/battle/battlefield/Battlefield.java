package battle.battlefield;

import calculations.GridCalculations;
import battle.interfaces.Area;
import battle.interfaces.Unit;
import testing.unittesting.StateType;

import java.util.HashMap;

/**
 * A 2D array representation of a battlefield in the game of
 * Conquest. Users can access areas on the battlefield and move
 * units from area to area.
 */
public class Battlefield {
    /** 2D array holding areas in battlefield */
    private static Area[][] areas;

    /** HashMap corresponding area ID to unit */
    HashMap<Integer, Unit> units;

    /**
     * Creates an n x n Battlefield object and initializes
     * ID to unit HashMap
     * @param n, or length of one row in the board
     */
    public Battlefield(int n) {
        areas = new Area[n][n];
        units = new HashMap<>();
    }

    /**
     * Places a unit into a certain area on the battlefield.
     * @param areaID where unit is placed
     * @param unit that will be placed
     * @return StateType
     */
    public StateType placeUnit(int areaID, Unit unit) {
        // checks if unit is ever null
        if (unit == null) {
            return StateType.returnFailure("Error: empty unit placement");
        }

        // checks to ensure that the unit is not placed
        // to an area with an existing unit
        Unit temp = units.get(areaID);

        // if it turns out there is already a unit at a given
        // area id, then the unit should not be placed there
        if (temp != null) {
            return StateType.returnFailure("Error: unit exists at " +
                    "selected area");
        }

        // places unit at area
        units.put(areaID, unit);

        return StateType.returnSuccess(null);
    }

    /**
     * Returns a unit given an areaID
     * @param areaID unit is supposedly located in.
     * @return Unit
     */
    public Unit getUnit(int areaID) {
        return units.get(areaID);
    }

    /**
     * Returns an area given an areaID.
     * @param areaID of the area.
     * @return Area
     */
    public Area getArea(int areaID) {
        // retrieves number of elements in single row
        int n = getRowCount();

        // retrieves row number of given area
        int row = GridCalculations.getRow(areaID, n);

        // retrieves col number of given area
        int col = GridCalculations.getCol(areaID, row, n);

        return areas[row][col];
    }

    /**
     * Moves a unit from one area to another, returns SUCCESS
     * if transfer is successful, returns FAILURE if transfer
     * encounters error.
     * @param id1, or the original area a unit is located in.
     * @param id2, or the new area a unit will be moved to.
     * @return StateType
     */
    public StateType moveUnit(int id1, int id2) {
        // retrieves unit at area id1
        Unit unit = units.get(id1);

        // if the unit is null, then no unit
        // was ever initialized at that area,
        // signaling an error
        if (unit == null) {
            return StateType.returnFailure("Error: unit at" +
                    " selected area does not exist");
        }

        // checks to ensure that the unit is not transferred
        // to an area with an existing unit
        Unit temp = units.get(id2);

        // if it turns out there is already a unit at a given
        // area id, then the unit should not be moved
        if (temp != null) {
            return StateType.returnFailure("Error: unit exists at " +
                    "selected area");
        }

        // once the transfer is confirmed to be valid, delete
        // the key-value pair associated with id1 and create
        // a new key-value pair associated with id2
        unit = units.remove(id1);
        temp = units.put(id2, unit);

        // if it turns out the HashMap returns a non-null object,
        // this means some unknown error occurred with replacing
        // the area ids
        if (temp != null) {
            return StateType.returnFailure("Error: unknown unit " +
                    "exists at final area");
        }

        return StateType.returnSuccess(null);
    }

    /**
     * Removes a unit from the battlefield via removing its
     * Integer-Unit Key-Value pair.
     * @param id, representing current space where unit is at.
     * @return StateType
     */
    public StateType removeUnit(int id) {
        // checks if unit ever existed
        Unit temp = units.get(id);

        // if the unit never existed, simply return
        // success
        if (temp == null) {
            return StateType.returnSuccess(null);
        }

        // removes integer-unit pair in HashMap
        units.remove(id);

        return StateType.returnSuccess(null);
    }

    /**
     * Returns number of elements in one row of the board.
     * @return int
     */
    public int getRowCount() {
        return areas.length;
    }
}
