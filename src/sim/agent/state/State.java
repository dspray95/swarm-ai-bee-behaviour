package sim.agent.state;

import sim.Coordinate;
import sim.agent.Agent;
import sim.agent.Bee;
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

    public Coordinate GetBestVector(Coordinate target){
        Coordinate bestCoordinate = parent.getLocation();
        double bestDistance = bestCoordinate.DistanceTo(target);

        for(Coordinate vector : Defaults.VECTORS){
            double vectorDistance = 0;
            Coordinate absoluteVector = new Coordinate(vector.X() + parent.getLocation().X(), vector.Y() + parent.getLocation().Y());
            //Do some checking to make sure that the coordinate isnt already occupied
            for(Bee agent : parent.getPerceptor().GetPerceivedBees()){
                if(absoluteVector.X() == agent.getLocation().X() && absoluteVector.Y() == agent.getLocation().Y()){
                    vectorDistance = -1;
                }
            }
            if(absoluteVector.X() == target.X() && absoluteVector.Y() == target.Y()){
                vectorDistance = -1;
            }
            //if the position isnt occupied calculate the distance
            if(vectorDistance >= 0){
                vectorDistance = absoluteVector.DistanceTo(target);
                if(vectorDistance <= bestDistance){
                    bestDistance = vectorDistance;
                    bestCoordinate = absoluteVector;
                }
            }
        }

        return bestCoordinate;
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
            case 8:
                vector = Defaults.CARDINAL_VECTOR.get("WEST");
                break;
            default:
                vector = new Coordinate(0, 0);
                break;
        }
        return new Coordinate(vector.X(), vector.Y()); //Return a new coordinate object as return Defaults vectors can result in strange behaviour
    }
}
