package sim.agent.state;

import sim.Coordinate;
import sim.agent.Agent;
import sim.agent.Bee;
import sim.config.Defaults;

import java.util.Random;

/**
 * State is an abstract class for all implementations of Agent States in the simulation.
 * In this context, a 'state' is the current attitude of the agent and affects its goals,
 * movements and actions.
 * The GetTarget function is the main interaction between an agent and its state.
 */
public abstract class State {

    protected Agent parent;

    /**
     * Class constructor, each state must have one parent.
     * @param parent An agent which this state instance will be affecting. Each agent will have one state.
     */
    public State(Agent parent){
        this.parent = parent;
    }

    /**
     * Abstract function to be implemented by child classes since the target will vary significantly on a
     * state-by-state basis, called by the parent agent.
     * @return
     */
    public abstract Coordinate GetTarget();

    /**
     * Perform a random wandering behaviour, the agent moves around in it's local area.
     * @return A target coordinate for the agent to attempt to move to
     */
    public Coordinate RandomWalk(){
        Coordinate nextVector = Defaults.VECTORS[new Random().nextInt(Defaults.VECTORS.length)];
        return GetBestVector(VectorToCoordinate(nextVector, parent.getLocation()));
    }

    /**
     * Ranks each cardinal direction by distance between the current position + the cardinal direction to the
     * target vector. If a point is occupied by another agent or is disallowed (e.g out of simulation bounds)
     * it is automatically ranked at -1.
     * @param target The target location that the agent is attempting to reach
     * @param disallowed Any locations that the agent is restricted from moving to
     * @return The vector which best reduces the euclidean distance between our agent and it's target location
     */
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
            //If we can see a threat make sure it isn't occupying our target vector
            if (parent.getPerceptor().isThreatPerceived()){
                if(parent.getPerceptor().getThreat().getLocation().Equals(absoluteVector)){
                    vectorDistance = -1;
                }
            }
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
     * @param current The current location of the agent
     * @param target the goal location of the agent
     * @return A vector between x:-1,y:-1 and x:1,y:1 facing the target location from the current location
     */
    public Coordinate VectorToTarget(Coordinate current, Coordinate target){
        double angle;
        double cardinality;
        Coordinate vector;
        //atan2 returns a radian, which is then converted to a degree.
        double radian = Math.atan2(target.Y() - current.Y(), target.X() - current.X());
        if(radian < 0){
            radian = radian + 2*Math.PI; //Our radian must be positive in order to be converted to a degree value
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
     * @param vector The vector heading from which to calculate the new coordinate.
     * @param currentLocation the current location from which we are moving.
     * @return A new coordinate which is the sum of vector and currentLocation.
     */
    public Coordinate VectorToCoordinate(Coordinate vector, Coordinate currentLocation){
        return new Coordinate(vector.X() + currentLocation.X(), vector.Y() + currentLocation.Y());
    }

    /**
     * Checks to see if any given coordinate is adjacent to the current location of the parent agent.
     * @param target Coordinate to test
     * @return boolean, true if target is within (-1,-1) or (1,1) distance of the parents current location
     */
    public boolean isProximate(Coordinate target){
        for(Coordinate coord : Defaults.VECTORS){
            Coordinate vectorLocation = new Coordinate(coord.X() + parent.getLocation().X(), coord.Y() + parent.getLocation().Y());
            if(vectorLocation.X() == target.X() && vectorLocation.Y() == target.Y()){
                return true;
            }
        }
        return false;
    }
}
