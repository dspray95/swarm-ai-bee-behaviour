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

    /**
     * Given a current and target coordinate, returns the vector heading from current to target
     * @param current
     * @param target
     * @return
     */
    public Coordinate VectorToCoordinate(Coordinate current, Coordinate target){
        double angle;
        double cardinality;
        //atan2 returns a radian, which is then converted to a degree.
        double radian = Math.atan2(target.Y() - current.Y(), target.X() - current.X());
        if(radian < 0){
            radian = radian + 2*Math.PI;
        }
        angle = Math.toDegrees(radian);
        angle += 90;
        cardinality = Math.round(angle/45); //break down into the eight cardinal directions used in the model
        switch((int) cardinality){
            case 0:
                return Defaults.CARDINAL_VECTOR.get("WEST");
            case 1:
                return Defaults.CARDINAL_VECTOR.get("NORTHWEST");
            case 2:
                return Defaults.CARDINAL_VECTOR.get("NORTH");
            case 3:
                return Defaults.CARDINAL_VECTOR.get("NORTHEAST");
            case 4:
                return Defaults.CARDINAL_VECTOR.get("EAST");
            case 5:
                return Defaults.CARDINAL_VECTOR.get("SOUTHEAST");
            case 6:
                return Defaults.CARDINAL_VECTOR.get("SOUTH");
            case 7:
                return Defaults.CARDINAL_VECTOR.get("SOUTHWEST");
            default:
                return new Coordinate(0,0);
        }
    }
}
