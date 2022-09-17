/**
 * A bullet of the simulation
 */
public class Bullet extends SimulationObject{
    /**
     * The number of this bullet instance.
     * <p>This number holds the value of the number of bullet instances created.<br>
     *  It is used to determine name of this {@link Bullet} instance</p>
     */
    private static long bulletNo = 0;

    /**
     * @param position a {@link Position} object giving the location of this {@link Bullet}
     * @param speed movement speed of this {@link Bullet} object in one step
     * @param direction a {@link Position} object giving the direction of this {@link Bullet}
     */
    public Bullet(Position position, double speed, Position direction) {
        super("Bullet" + bulletNo, position, speed);
        bulletNo++;
        setDirection(direction);
    }

    /**
     * Implements the behavior of this {@link Bullet} in one step
     * @param controller controller of the simulation
     */
    @Override
    public void step(SimulationController controller) {
        for(int step = 0; step < this.getSpeed(); step++)
        {
            Zombie closestZombie = controller.getClosestZombie(getPosition());
            if(closestZombie != null)
            {
                double distance = closestZombie.getPosition().distance(getPosition());

                // If bullet hits a zombie; remove zombie and the bullet
                if(distance <= closestZombie.getCollisionRange())
                {
                    Logger.bulletHitZombie(this,closestZombie);
                    closestZombie.setActive(false);
                    // Remove bullet
                    return;
                }
            }

            getPosition().add(getDirection());
            if(isOutOfBounds(getPosition(),controller))
            {
                Logger.bulletOutOfBounds(this);
                return;
            }
        }
        // Drop bullet
        Logger.bulletDropped(this);
    }

}
