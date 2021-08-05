package data.battle;

import data.calculations.TwoDCalculations;
import data.interfaces.Area;
import data.interfaces.Unit;
import data.unittesting.StateType;

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
        int n = areas.length;

        // retrieves row number of given area
        int row = TwoDCalculations.getRow(areaID, n);

        // retrieves col number of given area
        int col = TwoDCalculations.getCol(areaID, row, n);

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
}
