package testing.unittesting.ancient;

import battle.units.ancientunits.SpearInfantry;
import testing.methods.ConstructionTests;
import testing.methods.MethodTests;
import testing.unittesting.StateType;

public class SPTC {
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
    public static void main(String[] args) throws Exception {
        // "control group" unit (for target testing)
        SpearInfantry defender = new SpearInfantry("Town Militia",
                160, 300, 15, 0, 200, 0.9);

        System.out.println("Testing Spear Infantry normal cases...");

        // skirmisher infantry implementation - normal cases

        // unit 1: Levy Spearmen
        SpearInfantry attacker = new SpearInfantry("Levy Spearmen",
                160, 200, 20, 2, 150,
                1.2);
        System.out.println("Testing unit: " + attacker.getUnitName());

        StateType check = ConstructionTests.testUnitStats(attacker);
        if (!StateType.checkIfSuccess(check)) {
            throw new Exception(check.getMessage());
        }
        check = MethodTests.testRangedMethods(attacker, defender, 0.5);
        if (!StateType.checkIfSuccess(check)) {
            throw new Exception(check.getMessage());
        }

        // unit 2: phalanx spears
        attacker = new SpearInfantry("Phalanx Spears",
                160, 350, 35, 5, 250,
                1.5);
        System.out.println("Testing unit: " + attacker.getUnitName());

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }
        check = MethodTests.testRangedMethods(attacker, defender, 0.5);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }

        // unit 3: royal pikemen
        attacker = new SpearInfantry("Royal Pikemen", 160, 800,
                50, 10, 200, 1.1);
        System.out.println("Testing unit: " + attacker.getUnitName());

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }
        check = MethodTests.testRangedMethods(attacker, defender, 0.5);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }

        System.out.println("Testing SpearInfantry construction errors...");

        // skirmisher cavalry implementation - construction errors
        attacker = new SpearInfantry(null, 160, 800,
                50, 10, 200, 1.1);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: null name");
        }

        attacker = new SpearInfantry("Royal Pikemen", 0, 800,
                50, 10, 200, 1.1);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 limit");
        }

        attacker = new SpearInfantry("Royal Pikemen", 160, 0,
                50, 10, 200, 1.1);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 unit cost");
        }

        attacker = new SpearInfantry("Royal Pikemen", 160, 800,
                0, 10, 200, 1.1);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 melee damage");
        }

        attacker = new SpearInfantry("Royal Pikemen", 160, 800,
                50, -1, 200, 1.1);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: -1 armor");
        }

        attacker = new SpearInfantry("Royal Pikemen", 160, 800,
                50, 10, 0, 1.1);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 movement cost");
        }

        System.out.println("Spear Infantry tests successfully passed");
        System.out.println("---------------------------------------");
    }
}
