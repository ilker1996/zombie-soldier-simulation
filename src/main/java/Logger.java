/**
 * Logger utility class
 */
public class Logger {

    /**
     * Logs the information of the {@code bullet} hitting the {@code zombie} on the screen
     * @param bullet {@link Bullet} object that hit the {@code zombie}
     * @param zombie {@link Zombie} object that is hit by {@code bullet}
     */
    public static void bulletHitZombie(Bullet bullet, Zombie zombie)
    {
        System.out.println(bullet.toString() + " hit " + zombie.toString() + ".");
    }

    /**
     * Logs the information of the {@code bullet} moved out of bounds of the simulation on the screen
     * @param bullet {@link Bullet} object that moved out of bounds
     */
    public static void bulletOutOfBounds(Bullet bullet)
    {
        System.out.println(bullet.toString() + " moved out of bounds.");
    }

    /**
     * Logs the information of the {@code bullet} completed its step
     * @param bullet {@link Bullet} object that completed its lifecycle in the simulation
     */
    public static void bulletDropped(Bullet bullet)
    {
        System.out.println(bullet.toString() + " dropped to the ground at " + bullet.getPosition().toString() + ".");
    }


    /**
     * Logs the information of the {@code zombie} changed its {@code state}
     * @param zombie {@link Zombie} that changed its {@link Zombie#zombieState}
     * @param state {@link ZombieState} that {@code zombie} changed its {@link Zombie#zombieState} to
     */
    public static void stateChanged(Zombie zombie, ZombieState state)
    {
        System.out.println(zombie.toString() + " changed state to " + state.toString()  + ".");
    }

    /**
     * Logs the information of the {@code soldier} changed its {@code state}
     * @param soldier {@link Soldier} object changed its {@link Soldier#soldierState}
     * @param state {@link SoldierState} that {@code soldier} changed its {@link Soldier#soldierState} to
     */
    public static void stateChanged(Soldier soldier, SoldierState state)
    {
        System.out.println(soldier.toString() + " changed state to " + state.toString()  + ".");
    }

    /**
     * Logs the information of the {@code zombie} changed its {@code position}
     * @param zombie {@link Zombie} that changed its {@link Zombie#position}
     * @param position {@link Position} that {@code zombie} changed its {@link Zombie#position} to
     */
    public static void positionChanged(Zombie zombie, Position position)
    {
        System.out.println(zombie.toString() + " moved to " + position.toString() + ".");
    }

    /**
     * Logs the information of the {@code soldier} changed its {@code position}
     * @param soldier {@link Soldier} that changed its {@link Soldier#position}
     * @param position {@link Position} that {@code soldier} changed its {@link Soldier#position} to
     */
    public static void positionChanged(Soldier soldier, Position position)
    {
        System.out.println(soldier.toString() + " moved to " + position.toString() + ".");

    }

    /**
     * Logs the information of the {@code zombie} changed its {@code direction}
     * @param zombie {@link Zombie} that changed its {@link Zombie#direction}
     * @param direction {@link Position} that {@code zombie} changed its {@link Zombie#direction} to
     */
    public static void directionChanged(Zombie zombie, Position direction)
    {
        System.out.println(zombie.toString() + " changed direction to " + direction.toString());
    }

    /**
     * Logs the information of the {@code soldier} changed its {@code direction}
     * @param soldier {@link Soldier} that changed its {@link Soldier#direction}
     * @param direction {@link Position} that {@code soldier} changed its {@link Soldier#direction} to
     */
    public static void directionChanged(Soldier soldier, Position direction)
    {
        System.out.println(soldier.toString() + " changed direction to " + direction.toString() + ".");
    }

    /**
     * Logs the information of the {@code zombie} killed the {@code soldier}
     * @param zombie {@link Zombie} that killed the {@code soldier}
     * @param soldier {@link Soldier} that is killed
     */
    public static void zombieKilledSoldier(Zombie zombie, Soldier soldier)
    {
        System.out.println(zombie.toString() + " killed " + soldier.toString() + ".");
    }

    /**
     * Logs the information of the {@code soldier} fired the {@code bullet}
     * @param soldier {@link Soldier} that fired the {@code bullet}
     * @param bullet {@link Bullet} that is fired
     * @param direction Direction of the {@code bullet}
     */
    public static void soldierFiredBullet(Soldier soldier, Bullet bullet, Position direction)
    {
        System.out.println(soldier.toString() + " fired " + bullet.toString() + " to direction " + direction.toString() + ".");
    }

}
