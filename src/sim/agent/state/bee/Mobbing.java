package sim.agent.state.bee;

import sim.Coordinate;
import sim.agent.Agent;
import sim.agent.Bee;
import sim.agent.state.State;
import sim.config.Defaults;

import java.util.Random;

public class Mobbing extends State {

    Agent target;

    public Mobbing(Agent parent, Agent target) {
        super(parent);
        this.target = target;
        System.out.println("NEW MOBBING STATE");
    }

    @Override
    public Coordinate GetTarget() {
        return GetBestVector(target.getLocation());
    }
}
