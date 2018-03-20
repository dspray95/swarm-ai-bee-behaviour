package sim.agent;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sim.Coordinate;
import sim.Simulation;
import sim.config.Options;

import static org.testng.Assert.*;

public class AgentTest {

    Agent agent;
    Simulation sim;
    Coordinate initPos;
    @BeforeMethod
    public void setUp() throws Exception {
        Options options = new Options();
        sim = new Simulation(options);
        initPos = new Coordinate(10, 10);
        agent = new Bee(sim, initPos);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        agent = null;
        sim = null;
        initPos = null;
    }

    @Test
    public void testTick() throws Exception {
        agent.Tick();
        System.out.println("new location - x:" + agent.getLocation().X() + " y:" + agent.getLocation().Y());
        assertTrue(agent.getLocation() != initPos);
    }
}