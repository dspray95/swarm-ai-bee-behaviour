package sim.agent.module;

import sim.Coordinate;
import sim.agent.Agent;
import sim.agent.Hornet;
import sim.config.Options;

public class Actuator {

    Agent parent;
    Options options;

    public Actuator(Agent parent){
        this.parent = parent;
        this.options = parent.getOptions();
    }

    /**
     * Attempts to move the parent Agent to the target location
     * @param target location to move to
     * @return true if movement is possible, false otherwise
     */
    public boolean Move(Coordinate target){
        //Do not attempt to move if the parent is dead - simply return - probably a more efficient way to achieve this
        if(parent.getHP() <= 0){
            return true;
        }
        else { //If the target coordinate is already occupied - we can't move, so return false
            for (Agent agent : parent.getPerceptor().GetPerceivedBees()) {
                if (agent.getLocation().X() == target.X() && agent.getLocation().Y() == target.Y() && parent.getClass() != Hornet.class) {
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
