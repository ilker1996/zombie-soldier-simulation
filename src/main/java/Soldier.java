/**
 * Generic soldier in the simulation
 *
 */
public abstract class Soldier extends SimulationObject{
    /**
     * Current state of this soldier
     */
    private SoldierState soldierState;
    /**
     * Type of this soldier
     */
    private SoldierType soldierType;
    /**
     * Collision range of this soldier
     */
    private final double collisionRange;
    /**
     * Shooting range of this soldier
     */
    private final double shootingRange;

    /**
     * Flag to check if this Soldier enter the simulation currently
     */
    private boolean newlyCreated;

    /**
     * @param name name of this {@link Soldier}
     * @param position {@link Position} of this {@link Soldier}
     * @param speed speed of this {@link Soldier}
     * @param soldierState state of this {@link Soldier}
     * @param soldierType type of this {@link Soldier}
     * @param collisionRange collision range of this {@link Soldier}
     * @param shootingRange shooting range of this {@link Soldier}
     */
    public Soldier(String name, Position position, double speed,
                   SoldierState soldierState, SoldierType soldierType, double collisionRange, double shootingRange) {
        super(name, position, speed);
        this.soldierState = soldierState;
        this.soldierType = soldierType;
        this.collisionRange = collisionRange;
        this.shootingRange = shootingRange;
        this.newlyCreated = true;
    }

    public SoldierState getSoldierState() {
        return soldierState;
    }

    public void setSoldierState(SoldierState soldierState) {
        this.soldierState = soldierState;
    }

    public SoldierType getSoldierType() {
        return soldierType;
    }

    public void setSoldierType(SoldierType soldierType) {
        this.soldierType = soldierType;
    }

    public double getCollisionRange() {
        return collisionRange;
    }

    public double getShootingRange() {
        return shootingRange;
    }

    /**
     *
     * @return {@code true}, if this Soldier enter the simulation
     * {@code false}, otherwise
     */
    private boolean isNewlyCreated(){return newlyCreated;}


    /**
     * Implements action of this {@link Soldier} in one step
     * @param controller controller of the simulation
     */
    @Override
    public void step(SimulationController controller)
    {
        // If this Soldier is dead, return
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
        SoldierState state = getSoldierState();
        switch (state)
        {
            case SEARCHING:
                search(controller);
                break;
            case AIMING:
                aim(controller);
                break;
            case SHOOTING:
                shoot(controller);
                break;
        }
    }

    /**
     * Abstract method for behavior of the {@link Soldier} when its state is {@link SoldierState#SEARCHING}
     * @param controller controller of the simulation
     */
    protected abstract void search(SimulationController controller);
    /**
     * Abstract method for behavior of the {@link Soldier} when its state is {@link SoldierState#AIMING}
     * @param controller controller of the simulation
     */
    protected abstract void aim(SimulationController controller);
    /**
     * Abstract method for behavior of the {@link Soldier} when its state is {@link SoldierState#SHOOTING}
     * @param controller controller of the simulation
     */
    protected abstract void shoot(SimulationController controller);
}
