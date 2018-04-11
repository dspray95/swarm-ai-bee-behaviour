package sim.config;

import sim.Coordinate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Defaults {

    public static Coordinate[] VECTORS = {new Coordinate(1,0), //N
            new Coordinate(1,1),    //NE
            new Coordinate(0,1),    //E
            new Coordinate(-1,1),   //SE
            new Coordinate(-1, 0),  //S
            new Coordinate(-1, -1), //SW
            new Coordinate(0, -1),  //W
            new Coordinate(1, -1),  //NW
    };

    public static Map<String, Coordinate> CARDINAL_VECTOR = CardinalVector();

    public static Map<String, Coordinate> CardinalVector(){
        Map map = new HashMap<String, Coordinate>();
            map.put("NORTH", VECTORS[0]);
            map.put("NORTHEAST", VECTORS[1]);
            map.put("EAST", VECTORS[2]);
            map.put("SOUTHEAST", VECTORS[3]);
            map.put("SOUTH", VECTORS[4]);
            map.put("SOUTHWEST", VECTORS[5]);
            map.put("WEST", VECTORS[6]);
            map.put("NORTHWEST", VECTORS[7]);
        return Collections.unmodifiableMap(map);

    }

    public static int SWARM_SIZE = 500;
    public static int ENVIRONMENT_SIZE = 750;
    public static int DEPLOYMENT_AREA = 500;
    public static int PERCEPTION_DISTANCE = 250;
    public static int HORNET_TARGET_RADIUS = 100;
    public static int HORNET_TARGET_THRESHOLD = 3;
    public static int COHESION_RATE = 5;
    public static boolean WRITE_SWARM_FILE = false;
    public static boolean WRITE_LOG_FILE = true;
    public static AggressionSetting AGGRESSION_SETTING = AggressionSetting.UNIFORM;
    public static double AGGRESSION_VALUE = 0.75d;
    public static int PHEROMONE_STRENGTH = 250;

    //Average temperature in Osaka, Japan according to the Japanese Meteorological http://www.data.jma.go.jp/obd/stats/data/en/normal/normal.html
    public static double BASE_TEMPERATURE = 19; //C
    public static double BEE_LETHAL_TEMPERATURE = 48;
    public static double HORNET_LETHAL_TEMPERATURE = 46;
}
