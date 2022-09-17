/**
 * Soldier of type {@link SoldierType#REGULAR}
 *
 */
public class RegularSoldier extends  Soldier{
    /**
     * @param name name of this  {@link RegularSoldier}
     * @param position a {@link Position} object giving the location of this {@link RegularSoldier}
     */
    public RegularSoldier(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, 5, SoldierState.SEARCHING, SoldierType.REGULAR, 2, 20);
        setSoldierType(SoldierType.REGULAR);
    }

    /**
     * Implements the behavior of this {@link RegularSoldier} object
     * when its {@link RegularSoldier#soldierState} is {@link SoldierState#SEARCHING}
     * @param controller controller of the simulation
     */
    @Override
    protected void search(SimulationController controller)
    {
        Position nextPosition = getNextPosition();

        // If out of bounds
        if(isOutOfBounds(nextPosition, controller))
        {
            // Change direction to random value
            setDirection(Position.generateRandomDirection(true));
            Logger.directionChanged(this, this.getDirection());
        }
        else {
            // Change position to new position
            setPosition(nextPosition);
            Logger.positionChanged(this, this.getPosition());
        }

        Zombie closestZombie = controller.getClosestZombie(getPosition());
        double distance = (closestZombie == null) ?
                Double.MAX_VALUE :
                closestZombie.getPosition().distance(getPosition());
        // If the distance to the closest zombie is shorter than or equal to the shooting range of the soldier
        if(distance <= getShootingRange())
        {
            // Set state to AIMING
            setSoldierState(SoldierState.AIMING);
            Logger.stateChanged(this, this.getSoldierState());
        }

    }

    /**
     * Implements the behavior of this {@link RegularSoldier} object
     * when its {@link RegularSoldier#soldierState} is {@link SoldierState#AIMING}
     * @param controller controller of the simulation
     */
    @Override
    protected void aim(SimulationController controller)
    {
        // Distance to the closest zombie
        Zombie closestZombie = controller.getClosestZombie(getPosition());
        double distance = (closestZombie == null) ?
                Double.MAX_VALUE :
                closestZombie.getPosition().distance(getPosition());

        // If the distance is shorter than or equal to the shooting range of the soldier
        if(distance <= getShootingRange())
        {
            // Change direction through zombie
            setDirection(directionThrough(closestZombie));
            Logger.directionChanged(this, this.getDirection());
            // Change state to SHOOTING
            setSoldierState(SoldierState.SHOOTING);
            Logger.stateChanged(this, this.getSoldierState());
        }
        // Change state to SEARCHING otherwise
        else
        {
            setSoldierState(SoldierState.SEARCHING);
            Logger.stateChanged(this, this.getSoldierState());
        }
    }

    /**
     * Implements the behavior of this {@link RegularSoldier} object
     * when its {@link RegularSoldier#soldierState} is {@link SoldierState#SHOOTING}
     * @param controller controller of the simulation
     */
    @Override
    protected void shoot(SimulationController controller)
    {
        Bullet bullet = new Bullet(getPosition(), getSpeed(), getDirection());
        controller.addWaitingBullet(bullet);
        Logger.soldierFiredBullet(this, bullet, this.getDirection());

        Zombie closestZombie = controller.getClosestZombie(getPosition());
        double distance = (closestZombie == null) ?
                Double.MAX_VALUE :
                closestZombie.getPosition().distance(getPosition());
        // If distance shorter than or equal to the shooting range of the soldier
        if(distance <= getShootingRange())
        {
            // Change state to AIMING
            setSoldierState(SoldierState.AIMING);
            Logger.stateChanged(this, this.getSoldierState());
        }
        else
        {
            // Change direction randomly
            setDirection(Position.generateRandomDirection(true));
            Logger.directionChanged(this, this.getDirection());
            // Change state to SEARCHING otherwise
            setSoldierState(SoldierState.SEARCHING);
            Logger.stateChanged(this, this.getSoldierState());
        }
    }
}
