/**
 * Generic zombie in the simulation
 *
 */
public abstract class Zombie extends SimulationObject{
    /**
     * State of this zombie
     */
    private ZombieState zombieState;
    /**
     * Type of this zombie
     */
    private ZombieType zombieType;
    /**
     * Collision range of this Zombie
     */
    private final double collisionRange;
    /**
     * Detection range of this Zombie
     */
    private final double detectionRange;
    /**
     * Flag to check if this Zombie enter the simulation currently
     */
    private boolean newlyCreated;

    /**
     * @param name name of this {@link Zombie}
     * @param position {@link Position} of this {@link Zombie}
     * @param speed speed of this {@link Zombie}
     * @param zombieState state of this {@link Zombie}
     * @param zombieType type of this {@link Zombie}
     * @param collisionRange collision range of this {@link Zombie}
     * @param detectionRange shooting range of this {@link Zombie}
     */
    public Zombie(String name, Position position, double speed,
                  ZombieState zombieState, ZombieType zombieType, double collisionRange, double detectionRange) {
        super(name, position, speed);
        this.zombieState = zombieState;
        this.zombieType = zombieType;
        this.collisionRange = collisionRange;
        this.detectionRange = detectionRange;
        newlyCreated = true;

    }
    public ZombieState getZombieState() {
        return zombieState;
    }

    public void setZombieState(ZombieState zombieState) {
        this.zombieState = zombieState;
    }

    public ZombieType getZombieType() {
        return zombieType;
    }

    public void setZombieType(ZombieType zombieType) {
        this.zombieType = zombieType;
    }

    public double getCollisionRange() {
        return collisionRange;
    }

    public double getDetectionRange() {
        return detectionRange;
    }

    /**
     *
     * @return {@code true}, if this Soldier enter the simulation
     * {@code false}, otherwise
     */
    private boolean isNewlyCreated(){return newlyCreated;}

    /**
     * Implements action of this {@link Zombie} in one step
     * @param controller controller of the simulation
     */
    @Override
    public void step(SimulationController controller)
    {
        // If this Zombie is dead, return
        if(!isActive())
        {
            return;
        }
        if(isNewlyCreated())
        {
            setDirection(Position.generateRandomDirection(true));
            Logger.directionChanged(this,this.getDirection());
            newlyCreated = false;
        }
        // Common behaviour among zombies
        Soldier closestSoldier = controller.getClosestSoldier(getPosition());
        if(closestSoldier != null)
        {
            double distance = closestSoldier.getPosition().distance(getPosition());

            // If the distance is shorter than or equal to the sum of collision distance of zombie and soldier
            if(distance <= closestSoldier.getCollisionRange() + getCollisionRange())
            {
                // Remove the soldier from the simulation and return
                closestSoldier.setActive(false);
                Logger.zombieKilledSoldier(this, closestSoldier);
                return;
            }
        }

        ZombieState state = getZombieState();
        switch (state)
        {
            case WANDERING:
                wander(controller);
                break;
            case FOLLOWING:
                follow(controller);
                break;
        }

    }

    /**
     * Abstract method for behavior of the {@link Zombie} when its state is {@link ZombieState#WANDERING}
     * @param controller controller of the simulation
     */
    public abstract void wander(SimulationController controller);
    /**
     * Abstract method for behavior of the {@link Zombie} when its state is {@link ZombieState#FOLLOWING}
     * @param controller controller of the simulation
     */
    public abstract void follow(SimulationController controller);

}