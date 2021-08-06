package testing.unittesting.ancient;

import battle.units.ancientunits.SkirmisherInfantry;
import battle.units.ancientunits.SpearInfantry;
import testing.methods.ConstructionTests;
import testing.methods.MethodTests;
import testing.unittesting.StateType;

public class SITC {
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

        System.out.println("Testing Skirmisher Infantry normal cases...");

        // skirmisher infantry implementation - normal cases

        // unit 1: javelinmen
        SkirmisherInfantry attacker = new SkirmisherInfantry("Javelinmen",
                60, 150, 20, 10, 2,
                150, 3, 1.2);
        System.out.println("Testing unit: " + attacker.getUnitName());

        StateType check = ConstructionTests.testUnitStats(attacker);
        if (!StateType.checkIfSuccess(check)) {
            throw new Exception(check.getMessage());
        }
        check = MethodTests.testRangedMethods(attacker, defender, 0.5);
        if (!StateType.checkIfSuccess(check)) {
            throw new Exception(check.getMessage());
        }

        // unit 2: archers
        attacker = new SkirmisherInfantry("Archers", 60, 200,
                15, 20, 4, 200, 4, 1.5);
        System.out.println("Testing unit: " + attacker.getUnitName());

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }
        check = MethodTests.testRangedMethods(attacker, defender, 0.5);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }

        // unit 3: royal archers
        attacker = new SkirmisherInfantry("Royal Archers", 80, 800,
                20, 30, 7, 240, 4, 1.2);
        System.out.println("Testing unit: " + attacker.getUnitName());

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }
        check = MethodTests.testRangedMethods(attacker, defender, 0.5);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }

        System.out.println("Testing SkirmisherInfantry construction errors...");

        // skirmisher cavalry implementation - construction errors
        attacker = new SkirmisherInfantry(null,
                160, 1000, 20, 10, 2, 150, 2,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: null name");
        }

        attacker = new SkirmisherInfantry("Royal Archers",
                0, 1000, 20, 10, 2, 150, 2,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 limit");
        }

        attacker = new SkirmisherInfantry("Royal Archers",
                160, 0, 20, 10, 2, 150, 2,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 unit cost");
        }

        attacker = new SkirmisherInfantry("Royal Archers",
                160, 1000, 0, 10, 2, 150, 2,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 melee damage");
        }

        attacker = new SkirmisherInfantry("Royal Archers",
                160, 1000, 20, -1, 2, 150, 2,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: -1 ranged damage");
        }

        attacker = new SkirmisherInfantry("Royal Archers",
                160, 1000, 20, 10, -1, 150, 2,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: -1 armor");
        }

        attacker = new SkirmisherInfantry("Royal Archers",
                160, 1000, 20, 10, 2, 0, 2,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 movement cost");
        }

        attacker = new SkirmisherInfantry("Royal Archers",
                160, 1000, 20, 10, 2, 0, 2,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 movement cost");
        }

        attacker = new SkirmisherInfantry("Royal Archers",
                160, 1000, 20, 10, 2, 150, 0,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: range factor 0 or negative");
        }

        System.out.println("Skirmisher Infantry tests successfully passed");
        System.out.println("---------------------------------------");
    }
}
