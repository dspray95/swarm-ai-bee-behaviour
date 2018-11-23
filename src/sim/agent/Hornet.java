package sim.agent;

import sim.Coordinate;
import sim.Simulation;
import sim.agent.state.bee.Working;
import sim.agent.state.hornet.Hunting;

/**
 * The Hornet class is an agent that operates in the simulation worldspace,
 * modelled on the behaviour of Vespa Mandarina when attacking a honey bee hive.
 * The hornet will attempt to move towards a swarm and attack bee agents on the periphery.
 * If the hornet feels that it has killed enough bees, or that it is currently under threat
 * it will attempt to escape the worldspace.
 */
public class Hornet extends Agent {

    /**
     * Class constructor for the hornet class.
     * Sets the Hornet Agent specific attributes including
     * <ul>
     *     <li>hp</li>
     *     <li>heatThreshold</li>
     *     <li>attackPoints</li>
     * </ul>
     * Additionally, the initial state of the hornet is set as 'Hunting'
     * @param parent
     * @param location
     */
    public Hornet(Simulation parent, Coordinate location) {
        super(parent, location);
        this.state = new Hunting(this);
        this.hp = 25;
        this.heatThreshold = 47;
        this.attackPoints = 3;
    }

    public boolean IsEscaped()
    {
        /*if hornet position is:
           x || y < 20
           x || y > environment size - 20
           true
         */
        int edgeBuffer = 20;
        int envSize = parent.getOptions().getEnvironmentSize();
        if(hp > 0) {
            if (location.X() < edgeBuffer || location.Y() < edgeBuffer
                    || location.X() > envSize - edgeBuffer || location.Y() > envSize - edgeBuffer) {
                return true;
            } else return false;

        } else return false;
    }
}
