package sim.agent.state.hornet;

import sim.Coordinate;
import sim.agent.Agent;
import sim.agent.state.State;

public class Attacking extends State {

    Agent target;

    public Attacking(Agent parent, Agent target) {
        super(parent);
        this.target = target;
    }

    @Override
    public Coordinate GetTarget() {
        Coordinate targetVector = VectorToCoordinate(parent.getLocation(), target.getLocation());
        return new Coordinate(parent.getLocation().X() + targetVector.X(),
                parent.getLocation().Y() + targetVector.Y());
    }

}
