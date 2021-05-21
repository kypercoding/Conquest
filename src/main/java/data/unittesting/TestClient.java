package data.unittesting;

import data.implementations.ancientunits.MeleeCavalry;
import data.implementations.ancientunits.SpearInfantry;

public class TestClient {
    /**
        This testing client ensures that unit
        implementations (ranged or melee) function as
        intended.

        Each implementation will have three tests:
        1. Construction Tests - tests whether the
            object has successfully constructed
            and does not have invalid values
            (or detects invalid values)
        2. Method Tests - tests whether the object's
            methods do not cause any invalid values
            or unexpected behavior
     */
    public static void main(String[] args) {
        // "control group" unit (for target testing)
        SpearInfantry defender = new SpearInfantry("Town Militia",
                160, 300, 15, 0, 200, 0.9);

        System.out.println("Testing MeleeCavalry normal cases...");

        // melee cavalry implementation - normal cases
        MeleeCavalry melCavAttacker = new MeleeCavalry("Cataphracts",
                80, 1000, 20, 10, 300, 1.5);
        System.out.println("Testing unit: " + melCavAttacker.getUnitName());
        String check = ConstructionTests.testUnitStats(melCavAttacker);
        if (!checkIfSuccess(check)) {
            System.out.println(check);
            return;
        }
        check = MethodTests.testMeleeMethods(melCavAttacker, defender, 0.5);
        if (!checkIfSuccess(check)) {
            System.out.println(check);
            return;
        }

        melCavAttacker = new MeleeCavalry("Scout Cavalry", 80, 450,
                20, 1, 100, 1.8);
        System.out.println("Testing unit: " + melCavAttacker.getUnitName());
        check = ConstructionTests.testUnitStats(melCavAttacker);
        if (!checkIfSuccess(check)) {
            System.out.println(check);
            return;
        }
        check = MethodTests.testMeleeMethods(melCavAttacker, defender, 0.5);
        if (!checkIfSuccess(check)) {
            System.out.println(check);
            return;
        }

        melCavAttacker = new MeleeCavalry("Lancers", 80, 600,
                20, 1, 150, 1.8);
        System.out.println("Testing unit: " + melCavAttacker.getUnitName());
        check = ConstructionTests.testUnitStats(melCavAttacker);
        if (!checkIfSuccess(check)) {
            System.out.println(check);
            return;
        }
        check = MethodTests.testMeleeMethods(melCavAttacker, defender, 0.5);
        if (!checkIfSuccess(check)) {
            System.out.println(check);
            return;
        }

        System.out.println("Testing MeleeCavalry construction errors...");
        // melee cavalry implementation - construction errors
        melCavAttacker = new MeleeCavalry(null,
                160, 1000, 20, 10, 600, 1.5);

        check = ConstructionTests.testUnitStats(melCavAttacker);
        if (checkIfSuccess(check)) {
            System.out.println("Undetected Construction Error: null name");
            return;
        }

        melCavAttacker = new MeleeCavalry("Cataphracts",
                0, 1000, 20, 10, 600, 1.5);

        check = ConstructionTests.testUnitStats(melCavAttacker);
        if (checkIfSuccess(check)) {
            System.out.println("Undetected Construction Error: 0 limit");
            return;
        }

        melCavAttacker = new MeleeCavalry("Cataphracts",
                80, 0, 20, 10, 600, 1.5);

        check = ConstructionTests.testUnitStats(melCavAttacker);
        if (checkIfSuccess(check)) {
            System.out.println("Undetected Construction Error: 0 unit cost");
            return;
        }

        melCavAttacker = new MeleeCavalry("Cataphracts",
                80, 1000, 0, 10, 600, 1.5);

        check = ConstructionTests.testUnitStats(melCavAttacker);
        if (checkIfSuccess(check)) {
            System.out.println("Undetected Construction Error: 0 melee damage");
            return;
        }

        melCavAttacker = new MeleeCavalry("Cataphracts",
                80, 1000, 20, -1, 600, 1.5);

        check = ConstructionTests.testUnitStats(melCavAttacker);
        if (checkIfSuccess(check)) {
            System.out.println("Undetected Construction Error: -1 armor");
            return;
        }

        melCavAttacker = new MeleeCavalry("Cataphracts",
                80, 1000, 20, 10, 0, 1.5);

        check = ConstructionTests.testUnitStats(melCavAttacker);
        if (checkIfSuccess(check)) {
            System.out.println("Undetected Construction Error: 0 movement cost");
            return;
        }

        System.out.println("Melee cavalry tests successfully passed");
        System.out.println("---------------------------------------");
    }

    private static boolean checkIfSuccess(String result) {
        return result.equals("SUCCESS");
    }
}
