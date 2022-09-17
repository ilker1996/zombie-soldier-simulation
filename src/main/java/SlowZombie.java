/**
 * Zombie of type {@link ZombieType#SLOW}
 *
 */
public class SlowZombie extends Zombie{

    /**
     * @param name name of this  {@link SlowZombie}
     * @param position a {@link Position} object giving the location of this {@link SlowZombie}
     *
     */
    public SlowZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, 2, ZombieState.WANDERING, ZombieType.SLOW, 1, 40);
        setZombieType(ZombieType.SLOW);
    }

    /**
     * Implements the behavior of this {@link SlowZombie} object
     * when its {@link SlowZombie#zombieState} is {@link ZombieState#WANDERING}
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
        {
            // Change state to FOLLOWING
            setZombieState(ZombieState.FOLLOWING);
            Logger.stateChanged(this,this.getZombieState());
            return;
        }
        else
        {
            // Calculate the next position
            Position nextPosition = getNextPosition();

            // If next position is out of bounds
            if(isOutOfBounds(nextPosition, controller))
            {
                // Change direction to random value
                setDirection(Position.generateRandomDirection(true));
                Logger.directionChanged(this,this.getDirection());
            }
            else
            {
                // Change position to new position
                setPosition(nextPosition);
                Logger.positionChanged(this, this.getPosition());

            }
        }
    }

    /**
     * Implements the behavior of this {@link SlowZombie} object
     * when its {@link SlowZombie#zombieState} is {@link ZombieState#FOLLOWING}
     * @param controller controller of the simulation
     */
    @Override
    public void follow(SimulationController controller) {
        Soldier closestSoldier = controller.getClosestSoldier(getPosition());
        double distance = (closestSoldier == null) ?
                Double.MAX_VALUE :
                closestSoldier.getPosition().distance(getPosition());

        // If the distance is shorter than or equal to the detection range of the zombie
        if(distance <= getDetectionRange())
        {
            // Change direction through soldier
            setDirection(directionThrough(closestSoldier));
            Logger.directionChanged(this,this.getDirection());
        }
        // Calculate the next position
        Position nextPosition = getNextPosition();

        // If next position is out of bounds
        if(isOutOfBounds(nextPosition, controller))
        {
            // Change direction to random value
            setDirection(Position.generateRandomDirection(true));
            Logger.directionChanged(this,this.getDirection());

        }
        else
        {
            // Change position to new position
            setPosition(nextPosition);
            Logger.positionChanged(this, this.getPosition());

        }

        // If the distance is shorter than or equal to the detection range of the zombie
        if(distance <= getDetectionRange())
        {
            // Change state to FOLLOWING
            setZombieState(ZombieState.WANDERING);
            Logger.stateChanged(this,this.getZombieState());
        }
    }
}
