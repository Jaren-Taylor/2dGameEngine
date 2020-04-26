/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newgame;

import static newgame.Main.TurnCounter;
import static newgame.EnemyMembers.BlueFlame;
import static newgame.EnemyMembers.EnemyPlant;
import static newgame.EnemyMembers.Grim;
import static newgame.EnemyMembers.PurpleDragon;
import static newgame.EnemyMembers.RedFlame;
import static newgame.EnemyMembers.Worm;

/**
 * This class will handle creating the main characters and allowing for
 * manipulation of their stats and moves and whatnot. There will be a JFrame
 * class to handle basic Commands, and the main method will handle creating
 * different instances of enemies for a variety of encounter types. Lastly, this
 * class will create a list of Monsters stored in an array of ADTS, the indices
 * for the monsters will be their position in the array and this will then pass
 * them to whatever is taking their input. Contact me for any clarification on
 * how this works.
 *
 * @author jaren
 */
public class CastMembers {

    private int HP, BP, ATK, DEF, SPCLATK, SPCLDEF, SPD;
    private boolean isBuffed;
    private String name;
    public int xyLoc;

    public CastMembers(int HP, int BP, int ATK, int DEF, int SPCLATK, int SPCLDEF, int SPD, String name, boolean isBuffed) {

        this.HP = HP;
        this.BP = BP;
        this.ATK = ATK;
        this.DEF = DEF;
        this.SPCLATK = SPCLATK;
        this.SPCLDEF = SPCLDEF;
        this.SPD = SPD;
        this.name = name;
        this.isBuffed = false;

    }

    /* only call instances of the cast to create them in battle and augment stats, the permanent instances will
    be used to reset stats after turns and whatnot
     */
    //Main Characters permanent for stat resets and stuff 
    public static final CastMembers Lucia = new CastMembers(850, 20, 150, 75, 150, 50, 150, "Lucia", false);
    public static final CastMembers Rourke = new CastMembers(1000, 40, 75, 150, 100, 150, 75, "Rourke", false);

    // instances of Lucia and Rourke to operate on for buffs
    public static CastMembers LUCIA_INSTANCE = Lucia;
    public static CastMembers ROURKE_INSTANCE = Rourke;

    //Lucia's Moveset
    public static void rushDown(CastMembers target) {
        if (!LUCIA_INSTANCE.isDead()) {
//            if(enoughBP(LUCIA_INSTANCE, LUCIA_INSTANCE.getBP())){
            LUCIA_INSTANCE.setBP(LUCIA_INSTANCE.getBP() - 2);
            int currATK = LUCIA_INSTANCE.getATK() * 2;
            if (target.getDEF() >= currATK) {
                //display "ATK was ineffective
                target.setHP(target.getHP() - (target.getDEF() - currATK));
                System.out.println(" The attack was ineffective! ");
            }
            if (target.getDEF() < currATK) {

                target.setHP(target.getHP() - (currATK - target.getDEF()));
            }
            System.out.println(target.getName() + " has " + target.getHP() + " HP left!");

        }
    }
    

    public static void Lucretia(CastMembers target) {
        if (!LUCIA_INSTANCE.isDead()) {
            int initialTurn = TurnCounter;
            LUCIA_INSTANCE.setBP(LUCIA_INSTANCE.getBP() - 4);
            target.setSPCLATK(target.getSPCLATK() * 2);
            target.setSPD(target.getSPD() * 2);
            System.out.println(target.getName()+ " is buffed now!");

            new Thread(() -> {
                if (TurnCounter - initialTurn == 2) {
                    target.setOriginSPCLATK(target);
                    target.setOriginSPD(target);
                    System.out.println(target.getName()+ "'s Luecretia buff expired!");
                }

            }).start();

            // needs to be for two turns. Implement that counter somehow.
            //Maybe put a counter in the CastMember Based Queue, and whenever that counter = 2, 
            //after we compare speed, set everything equal to the origin except HP. We increment counter on a player if their isBuffed trait is true.
        }
    }

    public static void Perpetuation(CastMembers target) {
        if (!LUCIA_INSTANCE.isDead()) {
            LUCIA_INSTANCE.setBP(LUCIA_INSTANCE.getBP() - 5);

            new Thread(() -> {
                System.out.println("The pheonix comes... only for you " + target.getName());

                if (target.isDead()) {
                    target.setOriginHP(target);
                    target.setOriginDEF(target);
                    target.setOriginSPCLDEF(target);
                    System.out.println(target.getName() + " is okay now!\n");
                }
            }).start();

        }
    }

    public static void Sacrifice(CastMembers target) {
        if (!LUCIA_INSTANCE.isDead()) {
            LUCIA_INSTANCE.setHP(LUCIA_INSTANCE.getHP() / 2);
            target.setSPCLATK(target.getSPCLATK() * 2);
            target.setSPD(target.getSPD() * 2);
            System.out.println(target.getName()+ " is buffed now!");
        }
    }

    //Rourkes Moveset
    public static void Solemn(CastMembers target) {
        if (!ROURKE_INSTANCE.isDead()) {
            int currTurn = TurnCounter;
            ROURKE_INSTANCE.setBP(ROURKE_INSTANCE.getBP() - 5);
            target.setDEF(target.getDEF() * 2);
            target.setSPCLDEF(target.getSPCLDEF() * 2);
            System.out.println(target.getName()+ " is buffed now!");
            new Thread(() -> {
                if (TurnCounter - currTurn == 3) {
                    target.setOriginDEF(target);
                    target.setOriginSPD(target);
                    System.out.println(target.getName()+ "'s Solemn buff expired!");
                }
            }).start();
        }
    }

    public static void Expulsion(CastMembers target, CastMembers target2) {
        //target 1 will be chosen to get rid of buffs/debuffs and target2 will take the damage from the attack
        if (!ROURKE_INSTANCE.isDead()) {
            int counter = 0;
            if (target.getATK() != getOriginATK(target)) {
                target.setOriginATK(target);
                counter++;
            }
            if (target.getSPCLATK() != getOriginSPCLATK(target)) {
                target.setOriginSPCLATK(target);
                counter++;
            }
            if (target.getDEF() != getOriginDEF(target)) {
                target.setOriginDEF(target);
                counter++;
            }
            if (target.getSPCLDEF() != getOriginSPCLDEF(target)) {
                target.setOriginSPCLDEF(target);
                counter++;
            }
            if (target.getSPD() != getOriginSPD(target)) {
                target.setOriginSPD(target);
                counter++;
            }
            if(counter==0){
                counter=1;
            }
            int currSPCLATK = ((counter * 4) * (ROURKE_INSTANCE.getSPCLATK()));
            if (target2.getSPCLDEF() >= currSPCLATK) {
                //display "ATK was ineffective
                ROURKE_INSTANCE.setHP(ROURKE_INSTANCE.getHP() - (target2.getSPCLDEF() - currSPCLATK));
                System.out.println(" The attack was ineffective! /n " + ROURKE_INSTANCE.getName()
                        + "took: " + (ROURKE_INSTANCE.getHP() - (target2.getSPCLDEF() - currSPCLATK)) + " recoil damage!/n");
                System.out.println(ROURKE_INSTANCE.getName() + " has " + ROURKE_INSTANCE.getHP() + " HP left");
            } else {
                if (target2.getSPCLDEF() < currSPCLATK) {

                    target2.setHP(target2.getHP() - (currSPCLATK - target2.getSPCLDEF()));
                }
                System.out.println(target2.getName() + " has " + target2.getHP() + " HP left!");

            }
        }
    }

    public static void cast(CastMembers target) {
        if (!ROURKE_INSTANCE.isDead()) {
            ROURKE_INSTANCE.setBP(ROURKE_INSTANCE.getBP() - 5);
            if (target.getSPCLDEF() >= (ROURKE_INSTANCE.getSPCLATK() * 2)) {
                //display "ATK was ineffective
                ROURKE_INSTANCE.setHP(ROURKE_INSTANCE.getHP() - (target.getSPCLDEF() - (ROURKE_INSTANCE.getSPCLATK() * 2)));
                System.out.println(" The attack was ineffective! /n " + ROURKE_INSTANCE.getName()
                        + "took: " + (ROURKE_INSTANCE.getHP() - (target.getSPCLDEF() - (ROURKE_INSTANCE.getSPCLATK() * 2))) + " recoil damage!/n");
                System.out.println(ROURKE_INSTANCE.getName() + " has " + ROURKE_INSTANCE.getHP() + " HP left");
            } else {
                if (target.getSPCLDEF() < (ROURKE_INSTANCE.getSPCLATK() * 2)) {

                    target.setHP(target.getHP() - ((ROURKE_INSTANCE.getSPCLATK() * 2) - target.getSPCLDEF()));
                }
                System.out.println(target.getName() + " has " + target.getHP() + " HP left!");

            }
        }
    }

    public static void TheEnd(CastMembers target) {
        if (!ROURKE_INSTANCE.isDead()) {
            int cumulativeATK = (ROURKE_INSTANCE.getATK()
                    + ROURKE_INSTANCE.getSPCLATK()
                    + ROURKE_INSTANCE.getDEF()
                    + ROURKE_INSTANCE.getSPCLDEF()
                    + ROURKE_INSTANCE.getSPD());
            if (target.getSPCLDEF() >= cumulativeATK) {
                //display "ATK was ineffective
                ROURKE_INSTANCE.setHP(ROURKE_INSTANCE.getHP() - (target.getSPCLDEF() - cumulativeATK));
                System.out.println(" The attack was ineffective! /n " + ROURKE_INSTANCE.getName()
                        + "took: " + (ROURKE_INSTANCE.getHP() - (target.getSPCLDEF() - cumulativeATK)) + " recoil damage!/n");
                System.out.println(ROURKE_INSTANCE.getName() + " has " + ROURKE_INSTANCE.getHP() + " HP left");
            } else {
                if (target.getSPCLDEF() < cumulativeATK) {

                    target.setHP(target.getHP() - (cumulativeATK - target.getSPCLDEF()));
                }
                System.out.println(target.getName() + " has " + target.getHP() + " HP left!");

            }
            ROURKE_INSTANCE.setHP(0);
            System.out.println("Rourke is unable to battle!");

        }
    }

    //Basic stats and instance functions
    public boolean isDead() {
        return (HP <= 0);
    }
    public static boolean enoughBP(CastMembers target, int requiredBP){
        return ((target.getBP()-requiredBP) >= 0);
    }

    public CastMembers getEnemy(CastMembers target) {

        return target;
    }

    public boolean isBuffed() {
        return isBuffed;
    }

    public int getHP() {
        return HP;
    }

    public int getBP() {
        return BP;
    }

    public int getATK() {
        return ATK;
    }

    public int getDEF() {
        return DEF;
    }

    public int getSPCLATK() {
        return SPCLATK;
    }

    public int getSPCLDEF() {
        return SPCLDEF;
    }

    public int getSPD() {
        return SPD;
    }

    public String getName() {
        return name;
    }

    public void setHP(int newHP) {
        HP = newHP;
    }

    public void setBP(int newBP) {
        BP = newBP;
    }

    public void setATK(int newATK) {
        ATK = newATK;

    }

    public void setDEF(int newDEF) {
        DEF = newDEF;
    }

    public void setSPCLATK(int newSPCLATK) {
        SPCLATK = newSPCLATK;
    }

    public void setSPCLDEF(int newSPCLDEF) {
        SPCLDEF = newSPCLDEF;
    }

    public void setSPD(int newSPD) {
        SPD = newSPD;
    }
//origin methods set the specified stat to the value stored in the permanent instances of the objects, so their Original atk
public void setOriginHP(CastMembers target) {
        String memberName = target.getName();
        switch (memberName) {
            case "Quiet Blaze":
                target.setHP(BlueFlame.getHP());
                break;

            case "Carniverous Daisy":
                target.setHP(EnemyPlant.getHP());
                break;

            case "The Collector":
                target.setHP(Grim.getHP());
                break;

            case "The Relinquished":
                target.setHP(PurpleDragon.getHP());
                break;

            case "Raging Blaze":
                target.setHP(RedFlame.getHP());
                break;

            case "The Subterranian":
                target.setHP(Worm.getHP());
                break;

            case "Lucia":
                target.setHP(CastMembers.Lucia.getHP());
                break;

            case "Rourke":
                target.setHP(CastMembers.Rourke.getHP());
                break;

        }

    }

    public void setOriginATK(CastMembers target) {
        String memberName = target.getName();
        switch (memberName) {
            case "Lucia":
                target.setATK(Lucia.getATK());
                break;

            case "Rourke":
                target.setATK(Rourke.getATK());
                break;

            case "Quiet Blaze":
                target.setATK(BlueFlame.getATK());
                break;

            case "Carniverous Daisy":
                target.setATK(EnemyPlant.getATK());
                break;

            case "The Collector":
                target.setATK(Grim.getATK());
                break;

            case "The Relinquished":
                target.setATK(PurpleDragon.getATK());
                break;

            case "Raging Blaze":
                target.setATK(RedFlame.getATK());
                break;

            case "The Subterranian":
                target.setATK(Worm.getATK());
                break;

        }
    }

    public void setOriginSPCLATK(CastMembers target) {
        String memberName = target.getName();
        switch (memberName) {
            case "Lucia":
                target.setSPCLATK(Lucia.getSPCLATK());
                break;

            case "Rourke":
                target.setSPCLATK(Rourke.getSPCLATK());
                break;

            case "Quiet Blaze":
                target.setSPCLATK(BlueFlame.getSPCLATK());
                break;

            case "Carniverous Daisy":
                target.setSPCLATK(EnemyPlant.getSPCLATK());
                break;

            case "The Collector":
                target.setSPCLATK(Grim.getSPCLATK());
                break;

            case "The Relinquished":
                target.setSPCLATK(PurpleDragon.getSPCLATK());
                break;

            case "Raging Blaze":
                target.setSPCLATK(RedFlame.getSPCLATK());
                break;

            case "The Subterranian":
                target.setSPCLATK(Worm.getSPCLATK());
                break;

        }
    }

    public void setOriginDEF(CastMembers target) {
        String memberName = target.getName();
        switch (memberName) {
            case "Lucia":
                target.setDEF(Lucia.getDEF());
                break;

            case "Rourke":
                target.setDEF(Rourke.getDEF());
                break;
            case "Quiet Blaze":
                target.setDEF(BlueFlame.getDEF());
                break;

            case "Carniverous Daisy":
                target.setDEF(EnemyPlant.getDEF());
                break;

            case "The Collector":
                target.setDEF(Grim.getDEF());
                break;

            case "The Relinquished":
                target.setDEF(PurpleDragon.getDEF());
                break;

            case "Raging Blaze":
                target.setDEF(RedFlame.getDEF());
                break;

            case "The Subterranian":
                target.setDEF(Worm.getDEF());
                break;

        }
    }

    public void setOriginSPCLDEF(CastMembers target) {
        String memberName = target.getName();
        switch (memberName) {
            case "Lucia":
                target.setSPCLDEF(Lucia.getSPCLDEF());
                break;

            case "Rourke":
                target.setSPCLDEF(Rourke.getSPCLDEF());
                break;
            case "Quiet Blaze":
                target.setSPCLDEF(BlueFlame.getSPCLDEF());
                break;

            case "Carniverous Daisy":
                target.setSPCLDEF(EnemyPlant.getSPCLDEF());
                break;

            case "The Collector":
                target.setSPCLDEF(Grim.getSPCLDEF());
                break;

            case "The Relinquished":
                target.setSPCLDEF(PurpleDragon.getSPCLDEF());
                break;

            case "Raging Blaze":
                target.setSPCLDEF(RedFlame.getSPCLDEF());
                break;

            case "The Subterranian":
                target.setSPCLDEF(Worm.getSPCLDEF());
                break;

        }
    }

    public void setOriginSPD(CastMembers target) {
        String memberName = target.getName();
        switch (memberName) {
            case "Lucia":
                target.setSPD(Lucia.getSPD());
                break;

            case "Rourke":
                target.setSPD(Rourke.getSPD());
                break;

            case "Quiet Blaze":
                target.setSPD(BlueFlame.getSPD());
                break;

            case "Carniverous Daisy":
                target.setSPD(EnemyPlant.getSPD());
                break;

            case "The Collector":
                target.setSPD(Grim.getSPD());
                break;

            case "The Relinquished":
                target.setSPD(PurpleDragon.getSPD());
                break;

            case "Raging Blaze":
                target.setSPD(RedFlame.getSPD());
                break;

            case "The Subterranian":
                target.setSPD(Worm.getSPD());
                break;

        }
    }

    //get origin methods- returns the original value of a stat for a specific castMember object
    public static int getOriginATK(CastMembers target) {
        String memberName = target.getName();
        int value = 0;
        switch (memberName) {
            case "Lucia":
                value = Lucia.getATK();

            case "Rourke":
                value = Rourke.getATK();

        }
        return value;
    }

    public static int getOriginSPCLATK(CastMembers target) {
        String memberName = target.getName();
        int value = 0;
        switch (memberName) {
            case "Lucia":
                value = Lucia.getSPCLATK();
                break;

            case "Rourke":
                value = Rourke.getSPCLATK();
                break;

        }
        return value;
    }

    public static int getOriginDEF(CastMembers target) {
        String memberName = target.getName();
        int value = 0;
        switch (memberName) {
            case "Lucia":
                value = Lucia.getDEF();
                break;

            case "Rourke":
                value = Rourke.getDEF();
                break;

        }
        return value;
    }

    public static int getOriginSPCLDEF(CastMembers target) {
        String memberName = target.getName();
        int value = 0;
        switch (memberName) {
            case "Lucia":
                value = Lucia.getSPCLDEF();
                break;

            case "Rourke":
                value = Rourke.getSPCLDEF();
                break;

        }
        return value;
    }

    public static int getOriginSPD(CastMembers target) {
        String memberName = target.getName();
        int value = 0;
        switch (memberName) {
            case "Lucia":
                value = Lucia.getSPD();
                break;

            case "Rourke":
                value = Rourke.getSPD();
                break;

        }
        return value;
    }

    //basic commands anyone can use
    public static void physAtk(CastMembers Attacker, CastMembers target) {
        int currATK = Attacker.getATK();
        if (target.getDEF() >= currATK) {
            //display "ATK was ineffective
            Attacker.setHP(Attacker.getHP() - (target.getDEF() - currATK));
            System.out.println(" The attack was ineffective! /n " + Attacker.getName()
                    + "took: " + (Attacker.getHP() - (target.getDEF() - currATK)) + " recoil damage!/n");
            System.out.println(Attacker.getName() + " has " + Attacker.getHP() + " HP left");
        }
        if (target.getDEF() < currATK) {

            target.setHP(target.getHP() - (currATK - target.getDEF()));
        }
        System.out.println(target.getName() + " has " + target.getHP() + " HP left!");

    }

    public static void spclAtk(CastMembers Attacker, CastMembers target) {
        int currSPCLATK = Attacker.getSPCLATK();
        if (target.getSPCLDEF() >= currSPCLATK) {
            //display "ATK was ineffective
            Attacker.setHP(Attacker.getHP() - (target.getSPCLDEF() - currSPCLATK));
            System.out.println(" The attack was ineffective! /n " + Attacker.getName()
                    + "took: " + (Attacker.getHP() - (target.getSPCLDEF() - currSPCLATK)) + " recoil damage!\n");
            System.out.println(Attacker.getName() + " has " + Attacker.getHP() + " HP left\n");
        }
        if (target.getSPCLDEF() < currSPCLATK) {

            target.setHP(target.getHP() - (currSPCLATK - target.getSPCLDEF()));
        }
        System.out.println(target.getName() + " has " + target.getHP() + " HP left!\n");

    }

    public static void defend(CastMembers Defender) {
        Defender.setDEF(Defender.getDEF() * 2);
        System.out.println("Defense is doubled for one turn.\n");
        int counter = TurnCounter;
        new Thread(() -> {

            if ((TurnCounter - counter) == 1) {
                Defender.setOriginDEF(Defender);
                System.out.println(Defender.getName() + "'s Defense has returned to normal.\n");

            }
        }).start();
    }
}
