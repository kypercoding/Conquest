package data.unittesting;

import data.implementations.ancientunits.SkirmisherCavalry;
import data.implementations.ancientunits.SpearInfantry;

public class SCTC {
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

        System.out.println("Testing Skirmisher Cavalry normal cases...");

        // skirmisher cavalry implementation - normal cases

        // unit 1: horse archers
        SkirmisherCavalry attacker = new SkirmisherCavalry("Horse Archers",
                80, 300, 20, 10, 2,
                150, 3, 1.5);
        System.out.println("Testing unit: " + attacker.getUnitName());

        StateType check = ConstructionTests.testUnitStats(attacker);
        if (!StateType.checkIfSuccess(check)) {
            throw new Exception(check.getMessage());
        }
        check = MethodTests.testRangedMethods(attacker, defender, 0.5);
        if (!StateType.checkIfSuccess(check)) {
            throw new Exception(check.getMessage());
        }

        // unit 2: armoured horse archers
        attacker = new SkirmisherCavalry("Armoured Horse Archers", 80, 450,
                20, 15, 5, 200, 3, 1.25);
        System.out.println("Testing unit: " + attacker.getUnitName());

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }
        check = MethodTests.testRangedMethods(attacker, defender, 0.5);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }

        // unit 3: noble horse archers
        attacker = new SkirmisherCavalry("Royal Horse Archers", 80, 800,
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

        System.out.println("Testing SkirmisherCavalry construction errors...");

        // skirmisher cavalry implementation - construction errors
        attacker = new SkirmisherCavalry(null,
                160, 1000, 20, 10, 2, 150, 2,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: null name");
        }

        attacker = new SkirmisherCavalry("Armoured Horse Skirmishers",
                0, 1000, 20, 10, 2, 150, 2,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 limit");
        }

        attacker = new SkirmisherCavalry("Armoured Horse Skirmishers",
                160, 0, 20, 10, 2, 150, 2,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 unit cost");
        }

        attacker = new SkirmisherCavalry("Armoured Horse Skirmishers",
                160, 1000, 0, 10, 2, 150, 2,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 melee damage");
        }

        attacker = new SkirmisherCavalry("Armoured Horse Skirmishers",
                160, 1000, 20, -1, 2, 150, 2,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: -1 ranged damage");
        }

        attacker = new SkirmisherCavalry("Armoured Horse Skirmishers",
                160, 1000, 20, 10, -1, 150, 2,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: -1 armor");
        }

        attacker = new SkirmisherCavalry("Armoured Horse Skirmishers",
                160, 1000, 20, 10, 2, 0, 2,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 movement cost");
        }

        attacker = new SkirmisherCavalry("Armoured Horse Skirmishers",
                160, 1000, 20, 10, 2, 0, 2,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 movement cost");
        }

        attacker = new SkirmisherCavalry("Armoured Horse Skirmishers",
                160, 1000, 20, 10, 2, 150, 0,
                1.5);

        check = ConstructionTests.testUnitStats(attacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: range factor 0 or negative");
        }

        System.out.println("Skirmisher Cavalry tests successfully passed");
        System.out.println("---------------------------------------");
    }
}
