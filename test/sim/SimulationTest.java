package sim;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sim.agent.Agent;
import sim.config.Options;

import static org.testng.Assert.*;

public class SimulationTest {

    Simulation simulation;
    Options options;

    @BeforeMethod
    public void setUp() throws Exception {
    options = new Options();
        options.setSwarmSize(100);
        options.setEnvironmentSize(200);
        simulation = new Simulation(options);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        simulation = null;
        options = null;
    }

    @Test
    public void testPopulate() throws Exception {
        simulation.Populate();
        assertTrue(simulation.getSwarm().size() == options.getSwarmSize());
    }

    @Test
    public void testTick(){
        simulation.Populate();
        Coordinate initLocation = simulation.getSwarm().get(10).getLocation();
        simulation.Tick();
        assertTrue(simulation.getSwarm().get(10).getLocation() != initLocation);
    }

    @Test
    public void testSmallTicks(){
        simulation.Populate();
        Coordinate initLocation = simulation.getSwarm().get(10).getLocation();
        for(int i = 0; i<= 100; i++){
            simulation.Tick();
            Agent agent = simulation.getSwarm().get(10);
            System.out.println("new location - x:" + agent.getLocation().X() + " y:" + agent.getLocation().Y());
        }
        assertTrue(simulation.getSwarm().get(10).getLocation() != initLocation);
    }
}