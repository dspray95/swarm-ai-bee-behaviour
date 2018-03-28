package sim.agent.state.bee;

import sim.Coordinate;
import sim.agent.Agent;
import sim.agent.state.State;

public class Mobbing extends State {

    public Mobbing(Agent parent) {
        super(parent);
    }

    @Override
    public Coordinate GetTarget() {
        return null;
    }
}
