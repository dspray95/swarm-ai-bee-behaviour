package sim;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CoordinateTest {

    Coordinate coordinate;
    int bounds;

    @BeforeMethod
    public void setUp() throws Exception {
        coordinate = new Coordinate();
        bounds = 100;
    }

    @AfterMethod
    public void tearDown() throws Exception {
        coordinate = null;
        bounds = 0;
    }

    @Test
    public void testRandomCoordinate() throws Exception {
        coordinate.RandomCoordinate(bounds, 0);
        assertTrue(coordinate.X() <= bounds && coordinate.Y() <= bounds);
    }
}