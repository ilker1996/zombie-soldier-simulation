import java.util.ArrayList;
import java.util.List;

/**
 * Controls objects in the simulation
 *
 */
public class SimulationController {
    private final double height;
    private final double width;
    /**
     * List of zombies in the simulation
     */
    private List<Zombie> zombies;
    /**
     * List of zombies in the simulation
     */
    private List<Soldier> soldiers;
    /**
     * List of bullets in the simulation
     */
    private List<Bullet> bullets;
    /**
     * Bullets that waits to enter the simulation after current stepAll is finished
     */
    private List<Bullet> waitingBullets;

    /**
     * @param width Width of the simulation
     * @param height Height of the simulation
     */
    public SimulationController(double width, double height) {
        this.width = width;
        this.height = height;
        zombies = new ArrayList<>();
        soldiers = new ArrayList<>();
        bullets = new ArrayList<>();
        waitingBullets = new ArrayList<>();
    }
    
    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    /**
     * Simulates all of objects in the simulation for one step
     * except newly created {@link Bullet} objects
     */
    public void stepAll() {
        soldiers.forEach(s -> s.step(this));
        zombies.forEach(z ->  z.step(this));
        bullets.forEach(b -> b.step(this));

        soldiers.removeIf(s -> !s.isActive());
        zombies.removeIf(z -> !z.isActive());

        // Clear bullets
        bullets.clear();
        // Transfer waitingBullets
        bullets.addAll(waitingBullets);
        // Clear waiting bullets
        waitingBullets.clear();
    }

    /**
     * Adds {@code obj} to the simulation
     * @param obj {@link Zombie} object to be added to the simulation
     */
    public void addSimulationObject(Zombie obj) {
        zombies.add(obj);
    }
    /**
     * Adds {@code obj} to the simulation
     * @param obj {@link Soldier} object to be added to the simulation
     */
    public void addSimulationObject(Soldier obj) {
        soldiers.add(obj);
    }
    /**
     * Removes {@code obj} from the simulation
     * @param obj {@link Zombie} object to be removed from the simulation
     */
    public void removeSimulationObject(Zombie obj) {
        zombies.remove(obj);
    }
    /**
     * Removes {@code obj} from the simulation
     * @param obj {@link Soldier} object to be removed from the simulation
     */
    public void removeSimulationObject(Soldier obj) {
        soldiers.remove(obj);
    }

    /**
     * Adds newly created {@code bullet} to waiting queue
     * @param bullet Newly created {@link Bullet} object
     */
    public void addWaitingBullet(Bullet bullet){waitingBullets.add(bullet);}

    /**
     *
     * @return {@code true} if there is any {@link Zombie} or {@link Soldier} object left in the simulation<br>
     *     {@code false} otherwise
     */
    public boolean isFinished() {
        return zombies.isEmpty() || soldiers.isEmpty();
    }


    /**
     * @param position {@link Position} where closest {@link Zombie} is searched from
     * @return the closest {@link Zombie} object to the {@code position}
     */
    protected Zombie getClosestZombie(Position position)
    {
        double closestDistance = Double.MAX_VALUE;
        Zombie closestZombie = null;

        for(Zombie zombie: this.zombies)
        {
            if(zombie.isActive())
            {
                double distance = zombie.getPosition().distance(position);
                if(distance <= closestDistance)
                {
                    closestDistance = distance;
                    closestZombie = zombie;
                }
            }

        }
        return closestZombie;
    }
    /**
     * @param position {@link Position} where closest {@link Soldier} is searched from
     * @return the closest {@link Soldier} object to the {@code position}
     */
    protected Soldier getClosestSoldier(Position position)
    {
        double closestDistance = Double.MAX_VALUE;
        Soldier closestSoldier = null;

        for(Soldier soldier : soldiers)
        {
            if(soldier.isActive())
            {
                double distance = soldier.getPosition().distance(position);
                if(distance <= closestDistance)
                {
                    closestDistance = distance;
                    closestSoldier = soldier;
                }
            }

        }
        return closestSoldier;
    }
}
