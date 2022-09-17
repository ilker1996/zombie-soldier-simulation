/**
 * Soldier of type {@link SoldierType#SNIPER}
 *
 */
public class Sniper extends Soldier{
    /**
     * @param name name of this  {@link Sniper}
     * @param position a {@link Position} object giving the location of this {@link Sniper}
     */
    public Sniper(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name, position, 2, SoldierState.SEARCHING, SoldierType.SNIPER, 5, 40);
        setSoldierType(SoldierType.SNIPER);
    }

    /**
     * Implements the behavior of this {@link Sniper} object
     * when its {@link Sniper#soldierState} is {@link SoldierState#SEARCHING}
     * @param controller controller of the simulation
     */
    @Override
    protected void search(SimulationController controller) {
        Position nextPosition = getNextPosition();

        // If next position is out of bounds
        if(isOutOfBounds(nextPosition, controller))
        {
            setDirection(Position.generateRandomDirection(true));
            Logger.directionChanged(this,this.getDirection());
        }
        else
        {
            setPosition(nextPosition);
            Logger.positionChanged(this,this.getPosition());
        }
        setSoldierState(SoldierState.AIMING);
        Logger.stateChanged(this,this.getSoldierState());
    }
    /**
     * Implements the behavior of this {@link Sniper} object
     * when its {@link Sniper#soldierState} is {@link SoldierState#AIMING}
     * @param controller controller of the simulation
     */
    @Override
    protected void aim(SimulationController controller) {
        Zombie closestZombie = controller.getClosestZombie(getPosition());
        double distance = (closestZombie == null) ?
                Double.MAX_VALUE :
                closestZombie.getPosition().distance(getPosition());

        // If the distance is shorter than or equal to the shooting range of the soldier
        if(distance <= getShootingRange())
        {
            // Change direction through zombie and change state to SHOOTING
            setDirection(directionThrough(closestZombie));
            Logger.directionChanged(this,this.getDirection());
            setSoldierState(SoldierState.SHOOTING);
            Logger.stateChanged(this,this.getSoldierState());
        }
        else
        {   // If not , change state to SEARCHING
            setSoldierState(SoldierState.SEARCHING);
            Logger.stateChanged(this,this.getSoldierState());
        }
    }

    /**
     * Implements the behavior of this {@link Sniper} object
     * when its {@link Sniper#soldierState} is {@link SoldierState#SHOOTING}
     * @param controller controller of the simulation
     */
    @Override
    protected void shoot(SimulationController controller) {
        Bullet bullet = new Bullet(getPosition(), getSpeed(), getDirection());
        controller.addWaitingBullet(bullet);
        Logger.soldierFiredBullet(this,bullet,this.getDirection());

        Zombie closestZombie = controller.getClosestZombie(getPosition());
        double distance = (closestZombie == null) ?
                Double.MAX_VALUE :
                closestZombie.getPosition().distance(getPosition());
        // If distance shorter than or equal to the shooting range of the soldier
        if(distance <= getShootingRange())
        {
            // Change state to AIMING
            setSoldierState(SoldierState.AIMING);
            Logger.stateChanged(this,this.getSoldierState());
        }
        else
        {
            // Change direction randomly
            setDirection(Position.generateRandomDirection(true));
            Logger.directionChanged(this,this.getDirection());
            // Change state to SEARCHING
            setSoldierState(SoldierState.SEARCHING);
            Logger.stateChanged(this,this.getSoldierState());
        }
    }
}
