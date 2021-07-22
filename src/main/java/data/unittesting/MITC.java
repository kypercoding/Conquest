package data.unittesting;

import data.implementations.ancientunits.MeleeInfantry;
import data.implementations.ancientunits.SpearInfantry;

public class MITC {
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

        System.out.println("Testing MeleeInfantry normal cases...");

        // melee infantry implementation - normal cases

        // unit 1: levy swordsmen
        MeleeInfantry melInfAttacker = new MeleeInfantry("Levy Swordsmen",
                80, 300, 20, 10, 450, 1.5);
        System.out.println("Testing unit: " + melInfAttacker.getUnitName());

        StateType check = ConstructionTests.testUnitStats(melInfAttacker);
        if (!StateType.checkIfSuccess(check)) {
            throw new Exception(check.getMessage());
        }
        check = MethodTests.testMeleeMethods(melInfAttacker, defender, 0.5);
        if (!StateType.checkIfSuccess(check)) {
            throw new Exception(check.getMessage());
        }

        // unit 2: armoured swordsmen
        melInfAttacker = new MeleeInfantry("Armoured Swordsmen", 80, 450,
                20, 1, 600, 1.8);
        System.out.println("Testing unit: " + melInfAttacker.getUnitName());

        check = ConstructionTests.testUnitStats(melInfAttacker);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }
        check = MethodTests.testMeleeMethods(melInfAttacker, defender, 0.5);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }

        // unit 3: royal swordsmen
        melInfAttacker = new MeleeInfantry("Royal Swordsmen", 80, 800,
                20, 1, 700, 1.8);
        System.out.println("Testing unit: " + melInfAttacker.getUnitName());

        check = ConstructionTests.testUnitStats(melInfAttacker);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }
        check = MethodTests.testMeleeMethods(melInfAttacker, defender, 0.5);
        if (StateType.checkIfFailure(check)) {
            throw new Exception(check.getMessage());
        }

        System.out.println("Testing MeleeInfantry construction errors...");

        // melee cavalry implementation - construction errors
        melInfAttacker = new MeleeInfantry(null,
                160, 1000, 20, 10, 600, 1.5);

        check = ConstructionTests.testUnitStats(melInfAttacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: null name");
        }

        melInfAttacker = new MeleeInfantry("Armoured Swordsmen",
                0, 1000, 20, 10, 600, 1.5);

        check = ConstructionTests.testUnitStats(melInfAttacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 limit");
        }

        melInfAttacker = new MeleeInfantry("Armoured Swordsmen",
                80, 0, 20, 10, 600, 1.5);

        check = ConstructionTests.testUnitStats(melInfAttacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 unit cost");
        }

        melInfAttacker = new MeleeInfantry("Armoured Swordsmen",
                80, 1000, 0, 10, 600, 1.5);

        check = ConstructionTests.testUnitStats(melInfAttacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 melee damage");
        }

        melInfAttacker = new MeleeInfantry("Armoured Swordsmen",
                80, 1000, 20, -1, 600, 1.5);

        check = ConstructionTests.testUnitStats(melInfAttacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: -1 armor");
        }

        melInfAttacker = new MeleeInfantry("Armoured Swordsmen",
                80, 1000, 20, 10, 0, 1.5);

        check = ConstructionTests.testUnitStats(melInfAttacker);
        if (StateType.checkIfSuccess(check) || StateType.checkIfDestroy(check)) {
            throw new Exception("Undetected Construction Error: 0 movement cost");
        }

        System.out.println("Melee infantry tests successfully passed");
        System.out.println("---------------------------------------");
    }
}
