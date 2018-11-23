package sim.agent;

import sim.Coordinate;
import sim.Simulation;
import sim.agent.state.bee.Working;

/**
 * The Bee class is an agent that operates in the simulation worldspace,
 * modeled on the defensive behaviour of Apis Cerana Japonica. This bee agent
 * will perform a defensive balling behaviour when a Hornet agent threatens
 * the swarm, otherwise it will perform a random wander behaviour to simulate
 * working within a hive.
 */
public class Bee extends Agent {

    /**
     * Class Constructor
     * Sets the Bee Agent specific attributes including
     * <ul>
     *     <li>HP</li>
     *     <li>heatThreashold</li>
     *     <li>attackPoints</li>
     *     <li>aggression</li>
     * </ul>
     * Additionally, the initial state of the bee class is always Working.
     * The cohesion rate of this state is taken from the parent Simulation.Options object.
     *
     * @param parent The simulation to which this agent belongs.
     * @param location The location in which the agent exists in the worldspace
     * @param aggression How aggressive the agent will be in defending itself
     */
    public Bee(Simulation parent, Coordinate location, double aggression) {
        super(parent, location);
        this.state = new Working(this, options.getCohesionRate());
        this.hp = 5;
        this.heatThreshold = 48;
        this.attackPoints = 1;
        this.aggression = aggression;
    }


}
