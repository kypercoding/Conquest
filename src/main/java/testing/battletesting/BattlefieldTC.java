package testing.battletesting;

import battle.interfaces.Unit;
import battle.units.ancientunits.SpearInfantry;

/**
 * Testing client that tests Battlefield methods.
 */
public class BattlefieldTC {
    public static void main(String[] args) {
        // sample units to place on board
        Unit unit1 = new SpearInfantry("Militia Infantry", 160, 100,
                15, 1, 100, 1.1);
        Unit unit2 = new SpearInfantry("Hoplites", 160, 400, 35, 5,
                400, 0.5);

        // "normal cases" : ensures that standard unit placement,
        // movement, and removal work without issue

    }
}
