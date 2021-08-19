package testing.battletesting;

import battle.battlefield.BattleHandler;
import battle.interfaces.Unit;
import battle.units.ancientunits.MeleeCavalry;
import battle.units.ancientunits.MeleeInfantry;
import battle.units.ancientunits.SkirmisherCavalry;
import battle.units.ancientunits.SpearInfantry;

import java.util.ArrayList;
import java.util.Random;

public class AutoBattleTC {
    /**
     * Tests the AutoBattle method from BattleHandler.java
     */
    public static void main(String[] args) {
        // list of desired units
        Unit inf1 = new SpearInfantry("Militia Hoplites", 160,
                150, 20, 3, 150, 0.5);

        Unit inf2 = new MeleeInfantry("Legionaries", 160,
                350, 40, 10, 250, 0.2);

        Unit cav1 = new MeleeCavalry("Cataphracts", 80, 700,
                60, 15, 250, 1.5);

        Unit cav2 = new SkirmisherCavalry("Horse Archers", 80, 250, 10,
                35, 5, 100, 4, 1.8);

        // all desired units compiled into array
        Unit[] units = {inf1, inf2, cav1, cav2};

        // declares arrays of units
        Unit[] attackers;
        Unit[] defenders;

        // randomizes lists
        Random random = new Random();

        // chooses a random number of units to test for both sides
        // (i.e. attackers may get 7 units while defenders may
        // get 10 units)
        int aCount = random.nextInt(10) + 1;
        int dCount = random.nextInt(10) + 1;

        // initialize arrays of units
        attackers = new Unit[aCount];
        defenders = new Unit[dCount];

        // randomizes units in both sides
        for (int i = 0; i < attackers.length; i++) {
            int u = random.nextInt(units.length);
            attackers[i] = units[u].copyUnit();
        }

        for (int i = 0; i < defenders.length; i++) {
            int u = random.nextInt(units.length);
            defenders[i] = units[u].copyUnit();
        }

        System.out.println("Now printing original units:");
        System.out.println("Attackers:\n-------------------");

        // total number of attacker and defender soldiers
        int aTotal = 0;
        int dTotal = 0;

        // prints the original units and counts total number of
        // soldiers
        for (Unit attacker : attackers) {
            aTotal += attacker.getLimit();
            System.out.println(attacker.getUnitSummary());
            System.out.println("---------------------");
        }

        System.out.println("Defenders:\n----------------------");
        for (Unit defender : defenders) {
            dTotal += defender.getLimit();
            System.out.println(defender.getUnitSummary());
            System.out.println("---------------------");
        }

        // tests the BattleHandler's autoBattle method
        boolean isAttacker =
                BattleHandler.autoBattle(attackers, defenders);

        System.out.println("---------------------");

        // declares a winner
        if (isAttacker) {
            System.out.println("Attacker has won!");
        } else {
            System.out.println("Defender has won!");
        }

        System.out.println("---------------------");

        // prints result of battle
        System.out.println("Battle Summary:");
        System.out.println("---------------------");

        // counts total attacker and defender casualties
        int aCasualties = 0;
        int dCasualties = 0;

        // prints the resulting units
        for (Unit attacker : attackers) {
            if (attacker == null) {
                System.out.println("Empty");
            } else {
                aCasualties += attacker.getLimit() - attacker.getNumber();
                System.out.println(attacker.getUnitSummary());
            }
            System.out.println("---------------------");
        }

        System.out.println("Defenders:");
        for (Unit defender : defenders) {
            if (defender == null) {
                System.out.println("Empty");
            } else {
                dCasualties += defender.getLimit() - defender.getNumber();
                System.out.println(defender.getUnitSummary());
            }
            System.out.println("---------------------");
        }

        System.out.println("Attacker Total: " + aTotal);
        System.out.println("Defender Total: " + dTotal);
        System.out.println("Attacker Casualties: " + aCasualties);
        System.out.println("Defender Casualties: " + dCasualties);
    }
}
