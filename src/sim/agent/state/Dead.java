package sim.agent.state;

import sim.Coordinate;
import sim.agent.Agent;

public class Dead extends State {

    public Dead(Agent parent) {
        super(parent);
    }

    @Override
    public Coordinate GetTarget() {
        return parent.getLocation();
    }
}
