package sim.config;

import sim.Coordinate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Defaults {

    public static int NUM_TICKS = 500;
    public static int SWARM_SIZE = 1000;
    public static int ENVIRONMENT_SIZE = 750;
    public static int DEPLOYMENT_AREA = 500;
    public static int PERCEPTION_DISTANCE = 25;
    public static int HORNET_TARGET_RADIUS = 100;
    public static int HORNET_TARGET_THRESHOLD = 3;
    public static int COHESION_RATE = 5;
    public static boolean WRITE_SWARM_FILE = false;
    public static boolean WRITE_LOG_FILE = false;
    public static double AGGRESSION_VALUE = 1d;
    public static int PHEROMONE_STRENGTH = 90;
    public static AggressionSetting AGGRESSION_SETTING = AggressionSetting.UNIFORM;

    //Average temperature in Osaka, Japan according to the Japanese Meteorological http://www.data.jma.go.jp/obd/stats/data/en/normal/normal.html
    public static double BASE_TEMPERATURE = 19; //C
    public static double BEE_LETHAL_TEMPERATURE = 48; //Lower bound lethal temperature for A. Cerana Japnonica
    public static double HORNET_LETHAL_TEMPERATURE = 44; //Lower bound lethal temperature for V. Mandarina

    //Vector information

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

    //Help text
    private static String br = System.getProperty("line.separator");
    public static String HELP_TEXT =
            "SWARM AI - BEE BALLING BEHAVIOUR - HELP" + br +
            "Arguments:" + br +
            "\tSet swarm size:" + br +
            "\t\tsyntax: \"swarm_size:n\"" + br +
            "\tSet aggression level:" + br +
            "\t\tsyntax: \"aggression:n\"" + br +
            "\tSet aggression spread:" + br +
            "\t\tsyntax: \"aggression_setting:SETTING\"" + br +
            "\t\twhere setting is one of the following: \"RANDOM_SPREAD\", \"UNIFORM\", \"CLOSE_TO_VALUE\", " +
            "\"FEW_HIGH\", \"MANY_LOW\", \"FEW_HIGH_LOW\""+ br +
            "\tSet tick count:" + br +
            "\t\tsyntax: \"tick_count:n\"" + br +
            "\tWrite swarm file:" + br +
            "\t\tsyntax: \"write_swarm_file\"" + br +
            "\tWrite log file:" + br +
            "\t\tsyntax: \"write_log_file\"" + br +
            "\tChance output location:" + br +
            "\t\tsyntax: \"filepath:DESIRED_FILEPATH\"" + br;
}
