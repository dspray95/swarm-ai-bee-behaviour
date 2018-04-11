package sim.agent.state.bee;

import sim.Coordinate;
import sim.Mob;
import sim.agent.Agent;
import sim.agent.Bee;
import sim.agent.state.State;
import sim.config.Defaults;

import java.util.Random;

public class Mobbing extends State {

    Agent target;
    Mob mob;
    public Mobbing(Agent parent, Agent target) {
        super(parent);
        this.target = target;
        this.mob = parent.getParent().getMob();
        if(!mob.contains(parent)){
            this.mob.add(parent);
        }
    }

    @Override
    public Coordinate GetTarget() {
        //Todo more complex behaviour
        /*
        If the temp is too high, try to leave
         */
        ManageTemperature();
        return GetBestVector(target.getLocation());
    }

    public boolean ManageTemperature(){
        double currentCenterTemperature = mob.getTemperature();
        double currentLocationTemperature = currentCenterTemperature - (parent.getLocation().DistanceTo(target.getLocation())/2);
        if(currentLocationTemperature >= Defaults.BEE_LETHAL_TEMPERATURE){

        }
        return false;
    }
}
