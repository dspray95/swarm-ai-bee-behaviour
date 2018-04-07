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
        Coordinate nextVector = Defaults.VECTORS[new Random().nextInt(Defaults.VECTORS.length)];
        return GetBestVector(VectorToCoordinate(nextVector, parent.getLocation()));
    }

    public Coordinate GetBestVector(Coordinate target, Coordinate... disallowed){
        Coordinate bestCoordinate = parent.getLocation();
        double bestDistance = bestCoordinate.DistanceTo(target);

        for(Coordinate vector : Defaults.VECTORS){
            double vectorDistance = 0;
            Coordinate absoluteVector = VectorToCoordinate(vector, parent.getLocation());
            //Do some checking to make sure that the coordinate isnt already occupied
            //Check vector isnt on disallowed list
            if(disallowed.length > 0){
                if(vector.equals(disallowed[0])){
                    vectorDistance = -1;
                }
            }
            //Check vector isnt already occupied by other bees
            for (Bee agent : parent.getPerceptor().GetPerceivedBees()) {
                if (absoluteVector.Equals(agent.getLocation())) {
                    vectorDistance = -1;
                }
            }
            //If we can see a threat make sure the vector isnt occupied by it
            if (parent.getPerceptor().isThreatPerceived()){
                if(parent.getPerceptor().getThreat().getLocation().Equals(absoluteVector)){
                    vectorDistance = -1;
                }
            }
            //Check vector isnt already occupied by target
//            if (absoluteVector.Equals(target)){
//                vectorDistance = -1;
//            }

            //if the position isn't occupied calculate the distance
            if(vectorDistance >= 0){
                vectorDistance = absoluteVector.DistanceTo(target);
                if(vectorDistance <= bestDistance){ //<= means a preference to moving rather than staying still
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
    public Coordinate VectorToTarget(Coordinate current, Coordinate target){
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

    /**
     * Get the absolute value of the sum of two coordinates ie
     *  Coordinate vector = {0,1}
     *  Coordinate current location = {100,100}
     *  return {100, 101}
     * @param vector
     * @param currentLocation
     * @return
     */
    public Coordinate VectorToCoordinate(Coordinate vector, Coordinate currentLocation){
        return new Coordinate(vector.X() + currentLocation.X(), vector.Y() + currentLocation.Y());
    }
}
