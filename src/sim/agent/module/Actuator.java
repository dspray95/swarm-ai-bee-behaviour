package sim.agent.module;

import sim.Coordinate;
import sim.agent.Agent;
import sim.agent.Hornet;
import sim.config.Options;

/**
 * The Actuator handles all operations pertaining to movement of an agent.
 * Each actuator must have one and only one parent Agent object.
 */
public class Actuator {

    Agent parent;
    Options options;

    /**
     * Class constructor.
     * The agent that one instance of the Actuator class moves is defined by the parent parameter.
     * Options are used in determining the size of each movement step.
     * @param parent
     */
    public Actuator(Agent parent){
        this.parent = parent;
        this.options = parent.getOptions();
    }

    /**
     * Attempts to move the parent Agent to the target location.
     * This is achieved by checking if the target location is already occupied (via the parents perceptor)
     * If it is occupied, we cannot move, otherwise we move. This is all providing the parent agent is not currently
     * dead.
     * @param target location to attempt a move to
     * @return boolean, true if movement is possible, false otherwise
     */
    public boolean Move(Coordinate target){
        //Do not attempt to move if the parent is dead - simply return
        if(parent.getHP() <= 0){
            return true;
        }
        else { //If the target coordinate is already occupied - we can't move, so return false
            for (Agent agent : parent.getPerceptor().GetPerceivedBees()) {
                if (agent.getLocation().X() == target.X() && agent.getLocation().Y() == target.Y() && agent != parent) { //also make sure that we can stay in the current coord
                    return false;
                }
            }
        }
        //return false if the target is outside the environment
        if(target.X() < 0 || target.Y() < 0 || target.X() > options.getEnvironmentSize() || target.Y() > options.getEnvironmentSize()){
            return false;
        }
        else{ //otherwise move the parent to the target
            parent.setLocation(target);
            return true;
        }
    }

}
