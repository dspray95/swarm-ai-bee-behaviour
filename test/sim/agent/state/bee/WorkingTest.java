package sim.agent.state.bee;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sim.Coordinate;
import sim.Simulation;
import sim.agent.Agent;
import sim.agent.Bee;
import sim.config.Options;

import static org.testng.Assert.*;

public class WorkingTest {

    Coordinate pos;
    Coordinate target;
    Working working;

    @BeforeMethod
    public void setUp() throws Exception {
        pos = new Coordinate(10,1);
        target = new Coordinate(1,1);
        Options options = new Options();
        Simulation sim = new Simulation(options);
        Agent agent = new Bee(sim, pos);
        working = new Working(agent, options.getCohesionRate());
    }

    @AfterMethod
    public void tearDown() throws Exception {
        pos = null;
        target = null;
    }

    @Test
    public void testVectorToCoordinate() throws Exception {
        working.VectorToCoordinate(pos, target);
    }
}