package sim.agent;

import sim.Coordinate;
import sim.Simulation;
import sim.agent.state.bee.Working;
import sim.agent.state.hornet.Hunting;

public class Hornet extends Agent {

    public Hornet(Simulation parent, Coordinate location) {
        super(parent, location);
//        this.state = new Working(this, options.getCohesionRate());
        this.state = new Hunting(this);
        this.hp = 50;
        this.heatThreshold = 47;
    }

}
