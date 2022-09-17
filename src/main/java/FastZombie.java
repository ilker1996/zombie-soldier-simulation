/**
 * Zombie of type {@link ZombieType#FAST}
 *
 */
public class FastZombie extends Zombie{
    /**
     * @param name name of this  {@link FastZombie}
     * @param position a {@link Position} object giving the location of this {@link FastZombie}
     *
     */
    public FastZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, 20, ZombieState.WANDERING, ZombieType.FAST, 2, 20);
        setZombieType(ZombieType.FAST);
    }

    /**
     * Implements the behavior of this {@link FastZombie} object
     * when its {@link FastZombie#zombieState} is {@link ZombieState#WANDERING}
     * @param controller controller of the simulation
     */
    @Override
    public void wander(SimulationController controller) {
        Soldier closestSoldier = controller.getClosestSoldier(getPosition());
        double distance = (closestSoldier == null) ?
                Double.MAX_VALUE :
                closestSoldier.getPosition().distance(getPosition());

        // If the distance is shorter than or equal to the detection range of the zombie
        if(distance <= getDetectionRange())
        {   // Change direction through soldier
            setDirection(directionThrough(closestSoldier));
            Logger.directionChanged(this, this.getDirection());
            // Change state to FOLLOWING
            setZombieState(ZombieState.FOLLOWING);
            Logger.stateChanged(this, this.getZombieState());
            return;
        }

        // Calculate the next position
        Position nextPosition = getNextPosition();

        // If the position is out of bounds
        if(isOutOfBounds(nextPosition, controller))
        {
            // Change direction to random value
            setDirection(Position.generateRandomDirection(true));
            Logger.directionChanged(this, this.getDirection());
        }
        else
        {
            // Set position to new position
            setPosition(nextPosition);
            Logger.positionChanged(this, this.getPosition());

        }
    }
    /**
     * Implements the behavior of this {@link FastZombie} object
     * when its {@link FastZombie#zombieState} is {@link ZombieState#FOLLOWING}
     * @param controller controller of the simulation
     *
     */
    @Override
    public void follow(SimulationController controller) {
        // Calculate the next position
        Position nextPosition = getNextPosition();

        // If the position is out of bounds
        if(isOutOfBounds(nextPosition, controller))
        {
            // Change direction to random value
            setDirection(Position.generateRandomDirection(true));
            Logger.directionChanged(this, this.getDirection());
        }
        else
        {
            // Set position to new position
            setPosition(nextPosition);
            Logger.positionChanged(this, this.getPosition());
        }
        // Change state to WANDERING
        setZombieState(ZombieState.WANDERING);
        Logger.stateChanged(this, this.getZombieState());
    }
}
