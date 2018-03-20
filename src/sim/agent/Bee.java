package sim.agent;

import sim.Coordinate;
import sim.Simulation;
import sim.agent.state.bee.Working;

public class Bee extends Agent {


    public Bee(Simulation parent, Coordinate location) {
        super(parent, location);
        this.state = new Working(this, options.getCohesionRate());
        this.hp = 5;
        this.heatThreshold = 48;
    }
}
