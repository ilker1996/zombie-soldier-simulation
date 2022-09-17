/**
 * Zombie of type {@link ZombieType#REGULAR}
 *
 */
public class RegularZombie extends Zombie{

    /**
     * The number of times this {@link RegularZombie} is in FOLLOWING state
     */
    private int followingCount;

    /**
     * @param name name of this  {@link RegularZombie}
     * @param position a {@link Position} object giving the location of this {@link RegularZombie}
     *
     */
    public RegularZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, 5, ZombieState.WANDERING , ZombieType.REGULAR, 2, 20);
        followingCount = 0;
        setZombieType(ZombieType.REGULAR);
    }

    /**
     * Implements the behavior of this {@link RegularZombie} object
     * when its {@link RegularZombie#zombieState} is {@link ZombieState#WANDERING}
     * @param controller controller of the simulation
     */
    @Override
    public void wander(SimulationController controller) {
        // Calculate the next position
        Position nextPosition = getNextPosition();

        // If the position is out of bounds
        if(isOutOfBounds(nextPosition, controller))
        {
            // Change direction to random value
            setDirection(Position.generateRandomDirection(true));
            Logger.directionChanged(this, this.getDirection());
        }
        else {
            // Set position to new position
            setPosition(nextPosition);
            Logger.positionChanged(this, this.getPosition());
        }

        Soldier closestSoldier = controller.getClosestSoldier(getPosition());

        double distance = (closestSoldier == null) ?
                Double.MAX_VALUE :
                closestSoldier.getPosition().distance(getPosition());

        // If the distance is shorter than or equal to the detection range of the zombie
        if(distance <= getDetectionRange()) {
            // Change state to FOLLOWING
            setZombieState(ZombieState.FOLLOWING);
            Logger.stateChanged(this, this.getZombieState());
        }
    }

    /**
     * Implements the behavior of this {@link RegularZombie} object
     * when its {@link RegularZombie#zombieState} is {@link ZombieState#FOLLOWING}
     * @param controller controller of the simulation
     */
    @Override
    public void follow(SimulationController controller) {
        // Increment FOLLOWING state count
        incrFollowingCount();

        // Calculate the next position
        Position nextPosition = getNextPosition();

        // If the position is out of bounds
        if(isOutOfBounds(nextPosition, controller))
        {
            // Change direction to random value
            setDirection(Position.generateRandomDirection(true));
            Logger.directionChanged(this, this.getDirection());
        }
        else {
            // Set position to new position
            setPosition(nextPosition);
            Logger.positionChanged(this, this.getPosition());
        }
        // If the number of step zombie has been in FOLLOWING state is reached the  (4 for now)
        if(isOutOfFollowingCountBound())
        {
            // Reset the count
            resetFollowingCount();
            // Change state to WANDERING
            setZombieState(ZombieState.WANDERING);
            Logger.stateChanged(this,this.getZombieState());
        }
    }

    /**
     * Make {@link RegularZombie#followingCount} zero
     */
    private void resetFollowingCount()
    {
        followingCount = 0;
    }

    /**
     * Increments {@link RegularZombie#followingCount} by 1
     */
    private void incrFollowingCount()
    {
        followingCount++;
    }

    /**
     * Following Count limit is <b>4</b> currently
     * @return {@code true}, if {@link RegularZombie#followingCount} is in the limit<br>
     * false , if {@link RegularZombie#followingCount} is not in the limit<br>
     */
    private boolean isOutOfFollowingCountBound(){return 4 == followingCount;}
}
