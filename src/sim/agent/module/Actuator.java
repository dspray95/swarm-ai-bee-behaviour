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

    public boolean Move(Coordinate target){

        if(parent.getHP() <= 0){
            return true;
        }
        else {
            for (Agent agent : parent.getPerceptor().GetPerceivedBees()) {
                if (agent.getLocation().X() == target.X() && agent.getLocation().Y() == target.Y() && parent.getClass() != Hornet.class) {
                    return false;
                }
            }
        }

        if(target.X() < 0 || target.Y() < 0 || target.X() > options.getEnvironmentSize() || target.Y() > options.getEnvironmentSize()){
            return false;
        }
        else{
            parent.setLocation(target);
            return true;
        }
    }
}
