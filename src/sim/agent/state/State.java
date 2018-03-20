package sim.agent.state;

import sim.Coordinate;
import sim.agent.Agent;
import sim.config.Defaults;

import java.util.Random;

public abstract  class State {

    protected Agent parent;

    public State(Agent parent){
        this.parent = parent;
    }

    public abstract Coordinate GetTarget();

    public Coordinate RandomWalk(){
        Coordinate currentPos = parent.getLocation();
        Random r = new Random();

        Coordinate nextVector = Defaults.VECTORS[r.nextInt(Defaults.VECTORS.length)];
        return new Coordinate(currentPos.X() + nextVector.X(), currentPos.Y() + nextVector.Y());
    }

    public Coordinate VectorToCoordinate(Coordinate current, Coordinate target){
        double angle;
        double angleDegree;
        double cardinality;
        Coordinate targetVector;
        //atan2 returns a radian, which is then converted to a degree.
        angle = Math.toDegrees(Math.atan2(current.Y() - target.Y(), current.X() - target.X())) % 360;
        angle += 90;

        //NORTH = 270, WEST = 180, EAST = 0, SOUTH = 90
        cardinality = Math.round(angle/45); //break down into the eight cardinal directions used in the model
        switch((int) cardinality){
            case 0:
                return Defaults.CARDINAL_VECTOR.get("EAST");
            case 1:
                return Defaults.CARDINAL_VECTOR.get("SOUTHEAST");
            case 2:
                return Defaults.CARDINAL_VECTOR.get("SOUTH");
            case 3:
                return Defaults.CARDINAL_VECTOR.get("SOUTHWEST");
            case 4:
                return Defaults.CARDINAL_VECTOR.get("WEST");
            case 5:
                return Defaults.CARDINAL_VECTOR.get("NORTHWEST");
            case 6:
                return Defaults.CARDINAL_VECTOR.get("NORTH");
            case 7:
                return Defaults.CARDINAL_VECTOR.get("NORTHEAST");
            default:
                return new Coordinate(0,0);
        }
    }
}
