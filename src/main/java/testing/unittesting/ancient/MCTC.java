package testing.unittesting.ancient;

import battle.units.ancientunits.MeleeCavalry;
import battle.units.ancientunits.SpearInfantry;
import testing.methods.ConstructionTests;
import testing.methods.MethodTests;
import testing.unittesting.StateType;

public class MCTC {
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
    public static void main(String[] args) throws Exception{
        // "control group" unit (for target testing)
        SpearInfantry defender = new SpearInfantry("Town Militia",
                160, 300, 15, 0, 200, 0.9);

        System.out.println("Testing MeleeCavalry normal cases...");

        // melee cavalry implementation - normal cases

        // unit 1: cataphracts
        MeleeCavalry melCavAttacker = new MeleeCavalry("Cataphracts",
                80, 1000, 20, 10, 300, 1.5);
        System.out.println("Testing unit: " + melCavAttacker.getUnitName());

        StateType check = ConstructionTests.testUnitStats(melCavAttacker);
        if (!StateType.checkIfSuccess(check)) {
            throw new Exception(check.getMessage());
        }
        check = MethodTests.testMeleeMethods(melCavAttacker, defender, 0.5);
        if (!StateType.checkIfSuccess(check)) {
            throw new Exception(check.getMessage());
        }

        // unit 2: scout cavalry
        melCavAttacker = new MeleeCavalry("Scout Cavalry", 80, 450,
                20, 1, 100, 1.8);
        System.out.println("Testing unit: " + melCavAttacker.getUnitName());

        check = ConstructionTests.testUnitStats(melCavAttacker);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }
        check = MethodTests.testMeleeMethods(melCavAttacker, defender, 0.5);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }

        // unit 3: lancers
        melCavAttacker = new MeleeCavalry("Lancers", 80, 600,
                20, 1, 150, 1.8);
        System.out.println("Testing unit: " + melCavAttacker.getUnitName());

        check = ConstructionTests.testUnitStats(melCavAttacker);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }
        check = MethodTests.testMeleeMethods(melCavAttacker, defender, 0.5);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }

        System.out.println("Testing MeleeCavalry construction errors...");
        // melee cavalry implementation - construction errors
        melCavAttacker = new MeleeCavalry(null,
                160, 1000, 20, 10, 600, 1.5);

        check = ConstructionTests.testUnitStats(melCavAttacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: null name");
        }

        melCavAttacker = new MeleeCavalry("Cataphracts",
                0, 1000, 20, 10, 600, 1.5);

        check = ConstructionTests.testUnitStats(melCavAttacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 limit");
        }

        melCavAttacker = new MeleeCavalry("Cataphracts",
                80, 0, 20, 10, 600, 1.5);

        check = ConstructionTests.testUnitStats(melCavAttacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 unit cost");
        }

        melCavAttacker = new MeleeCavalry("Cataphracts",
                80, 1000, 0, 10, 600, 1.5);

        check = ConstructionTests.testUnitStats(melCavAttacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 melee damage");
        }

        melCavAttacker = new MeleeCavalry("Cataphracts",
                80, 1000, 20, -1, 600, 1.5);

        check = ConstructionTests.testUnitStats(melCavAttacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: -1 armor");
        }

        melCavAttacker = new MeleeCavalry("Cataphracts",
                80, 1000, 20, 10, 0, 1.5);

        check = ConstructionTests.testUnitStats(melCavAttacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 movement cost");
        }

        System.out.println("Melee cavalry tests successfully passed");
        System.out.println("---------------------------------------");
    }
}
