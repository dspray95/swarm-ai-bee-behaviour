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
        Coordinate vector;
        //atan2 returns a radian, which is then converted to a degree.
        double radian = Math.atan2(target.Y() - current.Y(), target.X() - current.X());
        if(radian < 0){
            radian = radian + 2*Math.PI;
        }
        angle = Math.toDegrees(radian);
        angle += 90;
        cardinality = Math.round(angle/45); //break down into the eight cardinal directions used in the model
        switch((int) cardinality) {
            case 0:
                vector = Defaults.CARDINAL_VECTOR.get("WEST");
                break;
            case 1:
                vector = Defaults.CARDINAL_VECTOR.get("NORTHWEST");
                break;
            case 2:
                vector = Defaults.CARDINAL_VECTOR.get("NORTH");
                break;
            case 3:
                vector = Defaults.CARDINAL_VECTOR.get("NORTHEAST");
                break;
            case 4:
                vector = Defaults.CARDINAL_VECTOR.get("EAST");
                break;
            case 5:
                vector = Defaults.CARDINAL_VECTOR.get("SOUTHEAST");
                break;
            case 6:
                vector = Defaults.CARDINAL_VECTOR.get("SOUTH");
                break;
            case 7:
                vector = Defaults.CARDINAL_VECTOR.get("SOUTHWEST");
                break;
            default:
                vector = new Coordinate(0, 0);
                break;
        }
        return new Coordinate(vector.X(), vector.Y());
    }
}
