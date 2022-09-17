/**
 * Generic object of the simulation
 *
 */
public abstract class SimulationObject {
    private final String name;
    private Position position;
    private Position direction;
    private final double speed;
    private boolean active;

    public SimulationObject(String name, Position position, double speed) {
        this.name = name;
        this.position = position;
        this.speed = speed;
        this.direction = null;
        this.active = true;
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getDirection() {
        return direction;
    }

    public void setDirection(Position direction) {
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     *
     * @return name of this {@link SimulationObject}
     */
    @Override
    public String toString() { return getName(); }
    
    public abstract void step(SimulationController controller);


    /**
     *
     * @return next position of the object by using its {@link SimulationObject#speed} and {@link SimulationObject#direction}
     */
    protected Position getNextPosition()
    {
        Position newPosition = (Position) getPosition().clone();
        Position tmpDirection = (Position) getDirection().clone();
        tmpDirection.mult(getSpeed());
        newPosition.add(tmpDirection);

        return newPosition;
    }

    /**
     * Checks if {@code position} is out of bounds of the simulation
     * @param position {@link Position} object to controlled if it is out of bounds of the simulation
     * @param controller controller of the simulation
     * @return {@code true}, if {@code position} is out of bounds of the simulation<br>
     * {@code false}, if {@code position} is not out of bounds of the simulations
     */
    protected boolean isOutOfBounds(Position position, SimulationController controller)
    {
        return position.getX() >= controller.getWidth() || position.getY() >= controller.getHeight()
                || position.getX() < 0 || position.getY() < 0;
    }

    /**
     * @param target {@link SimulationObject} that direction is wanted to be pointed on
     * @return {@link Position} through the {@code target}
     */
    protected Position directionThrough(SimulationObject target)
    {
        Position newPosition = new Position(target.getPosition().getX() - getPosition().getX(),
                target.getPosition().getY() - getPosition().getY());
        newPosition.normalize();
        return newPosition;
    }

}
