package sim.agent.state;

import sim.Coordinate;
import sim.agent.Agent;

/**
 * The Dead class is a sub-class of State.
 * Any agent which can no longer make any movements or perceive its environment will be assigned this state.
 *
 */
public class Dead extends State {

    /**
     * Class constructor.
     * When the Dead state is created a warning pheromone is placed at the current position of the state's parent.
     * @param parent The parent agent of the state
     */
    public Dead(Agent parent) {
        super(parent);
        parent.PlacePheromone(parent.getOptions().getPheromoneStrength() * 2);
    }

    /**
     * We can't move when we're dead, our goal is just our current location
     * @return Coordinate, parent's current location
     */
    @Override
    public Coordinate GetTarget() {
        return parent.getLocation();
    }
}
