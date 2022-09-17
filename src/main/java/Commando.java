/**
 * Soldier of type {@link SoldierType#COMMANDO}
 *
 */
public class Commando extends Soldier {
    /**
     * @param name name of this  {@link Commando}
     * @param position a {@link Position} object giving the location of this {@link Commando}
     */
    public Commando(String name, Position position) {
        super(name, position, 10, SoldierState.SEARCHING, SoldierType.COMMANDO, 2, 10);
        setSoldierType(SoldierType.COMMANDO);
    }

    /**
     * Implements the behavior of this {@link Commando} object
     * when its {@link Commando#soldierState} is {@link SoldierState#SEARCHING}
     * @param controller controller of the simulation
     */
    @Override
    protected void search(SimulationController controller) {
        // Calculate the euclidean distance to the closest zombie.
        Zombie closestZombie = controller.getClosestZombie(getPosition());
        double distance = (closestZombie == null) ?
                Double.MAX_VALUE :
                closestZombie.getPosition().distance(getPosition());

        // If the distance is shorter than or equal to the shooting range of the soldier
        if(distance <= getShootingRange())
        {
            setDirection(directionThrough(closestZombie));
            Logger.directionChanged(this, this.getDirection());
            setSoldierState(SoldierState.SHOOTING);
            Logger.stateChanged(this, this.getSoldierState());
            return;
        }

        Position nextPosition = getNextPosition();
        // If out of bounds
        if(isOutOfBounds(nextPosition, controller))
        {
            // Change direction to random value
            setDirection(Position.generateRandomDirection(true));
            Logger.directionChanged(this,this.getDirection());
        }
        else {
            // Change position to new position
            setPosition(nextPosition);
            Logger.positionChanged(this, this.getPosition());
        }

        // Calculate the euclidean distance to the closest zombie.
        closestZombie = controller.getClosestZombie(getPosition());
        distance = (closestZombie == null) ?
                Double.MAX_VALUE :
                closestZombie.getPosition().distance(getPosition());
        // If the distance is shorter than or equal to the shooting range of the soldier
        if (distance <= getShootingRange()) {
            setDirection(directionThrough(closestZombie));
            Logger.directionChanged(this, this.getDirection());
            setSoldierState(SoldierState.SHOOTING);
            Logger.stateChanged(this, this.getSoldierState());
        }
    }

    /**
     * Implements the behavior of this {@link Commando} object
     * when its {@link Commando#soldierState} is {@link SoldierState#AIMING}<br>
     * <b> The soldier type of {@link Commando} does nothing currently when {@link Commando#soldierState} is {@link SoldierState#AIMING} </b>
     * @param controller controller of the simulation
     */
    @Override
    protected void aim(SimulationController controller) {}

    /**
     * Implements the behavior of this {@link Commando}
     * when its {@link Commando#soldierState} is {@link SoldierState#SHOOTING}
     * @param controller controller of the simulation
     */
    @Override
    protected void shoot(SimulationController controller) {
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
            // Change direction through zombie
            setDirection(directionThrough(closestZombie));
            Logger.directionChanged(this, this.getDirection());
        }
        else
        {
            // Change direction randomly
            setDirection(Position.generateRandomDirection(true));
            Logger.directionChanged(this, this.getDirection());
            // Change state to SEARCHING
            setSoldierState(SoldierState.SEARCHING);
            Logger.stateChanged(this, this.getSoldierState());
        }
    }
}
