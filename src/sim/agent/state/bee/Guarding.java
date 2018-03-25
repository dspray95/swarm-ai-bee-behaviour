package sim.agent.state.bee;

import sim.Coordinate;
import sim.agent.Agent;
import sim.agent.state.State;

public class Guarding extends State {

    Agent threat;

    public Guarding(Agent parent) {
        super(parent);
    }

    @Override
    public Coordinate GetTarget() {
        return null;
    }
}
