package sim.agent.module;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sim.Coordinate;
import sim.Simulation;
import sim.agent.Bee;
import sim.config.Options;

import static org.testng.Assert.*;

public class ActuatorTest {

    Actuator actuator;
    Bee parent;

    @BeforeMethod
    public void setUp() throws Exception {
        Options options = new Options();
        options.setEnvironmentSize(10);
        Simulation sim = new Simulation(options);
        parent = new Bee(sim, new Coordinate(10,10));
        actuator = new Actuator(parent);
    }

    @AfterMethod
    public void tearDown() throws Exception {
        actuator = null;
        parent = null;
    }

    @Test
    public void testMoveFailure() throws Exception {
        assertFalse(actuator.Move(new Coordinate(11, 11)));
    }

    @Test
    public void testMoveSuccess(){
        assertTrue(actuator.Move(new Coordinate(9,9)));
    }

    @Test
    public void testMoveBorder(){
        assertTrue(actuator.Move(new Coordinate(10,10)));
    }

    @Test
    public void testMoveParent(){
        Coordinate location = new Coordinate(9,9);
        actuator.Move(location);
        assertEquals(parent.getLocation(), location);
    }
}