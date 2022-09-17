import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Runs simulation
 *
 */
public class SimulationRunner {

    public static void main(String[] args) {
        SimulationController simulation = new SimulationController(50, 50);

        
        simulation.addSimulationObject(new RegularSoldier("Soldier1", new Position(10, 10)));
        simulation.addSimulationObject(new RegularZombie("Zombie1", new Position(40, 40)));
        simulation.addSimulationObject(new RegularZombie("Zombie2", new Position(20, 20)));
        simulation.addSimulationObject(new RegularZombie("Zombie3", new Position(30, 30)));

        

        while (!simulation.isFinished()) {
            simulation.stepAll();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(SimulationRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
