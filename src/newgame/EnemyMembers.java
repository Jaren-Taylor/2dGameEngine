/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newgame;

/**
 * For the sake of our turn Queue we use the same ADT as CastMembers here, but
 * we create the enemies in this file. We also will have supporting functions
 * for reseting stats to their original value here, as well as Enemy Signatures
 * but they will be limited to two per enemy.
 *
 * @author Jaren Taylor
 */
public class EnemyMembers {

    //Enemies Permanent instances for stat resets and stuff
    public static final CastMembers BlueFlame = new CastMembers(600, 100, 75, 75, 75, 75, 80, "Quiet Blaze", false);
    public static final CastMembers EnemyPlant = new CastMembers(1000, 100, 100, 100, 100, 100, 100, "Carniverous Daisy", false);
    public static final CastMembers Grim = new CastMembers(1500, 1000, 100, 150, 100, 150, 175, "The Collector", false);
    public static final CastMembers PurpleDragon = new CastMembers(1500, 1000, 150, 150, 150, 150, 200, "The Relinquished", false);
    public static final CastMembers RedFlame = new CastMembers(850, 100, 75, 100, 75, 100, 70, "Raging Blaze", false);
    public static final CastMembers Worm = new CastMembers(1500, 1000, 200, 100, 150, 100, 50, "The Subterranian", false);

    //Enemy Instances
    public static CastMembers BlueFlameInstance = BlueFlame;
    public static CastMembers EnemyPlantInstance = EnemyPlant;
    public static CastMembers GrimInstance = Grim;
    public static CastMembers PurpleDragonInstance = PurpleDragon;
    public static CastMembers RedFlameInstance = RedFlame;
    public static CastMembers WormInstance = Worm;

    
    

}
