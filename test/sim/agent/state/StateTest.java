package sim.agent.state;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sim.Coordinate;
import sim.Simulation;
import sim.agent.Bee;
import sim.agent.module.Actuator;
import sim.agent.state.bee.Working;
import sim.config.Options;

import static org.testng.Assert.*;

public class StateTest {

    State state;
    Bee parent;
    Coordinate initPos;

    @BeforeMethod
    public void setUp() throws Exception {
        Options options = new Options();
        options.setEnvironmentSize(100);
        Simulation sim = new Simulation(options);
        initPos = new Coordinate(9,9);
        parent = new Bee(sim, initPos);
        state = new Working(parent, options.getCohesionRate());
    }

    @AfterMethod
    public void tearDown() throws Exception {
        state = null;
        parent = null;
        initPos = null;
    }

    @Test
    public void testRandomWalk() throws Exception {
        parent.setLocation(state.RandomWalk());
        assertTrue(parent.getLocation() != initPos);
    }

    @Test
    public void testLongRandomWalk(){
        for(int i = 0; i<=100; i++){
            parent.setLocation(state.RandomWalk());
            System.out.println("new location - x:" + parent.getLocation().X() + " y:" + parent.getLocation().Y());
        }
        assertTrue(parent.getLocation() != initPos);
    }
}