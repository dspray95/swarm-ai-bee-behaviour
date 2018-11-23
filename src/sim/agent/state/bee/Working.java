package sim.agent.state.bee;

import sim.Coordinate;
import sim.agent.Agent;
import sim.agent.state.State;

import java.util.Random;

/**
 * The Working class (a sub-class of State) makes the agent perform behaviour designed to emulate a bee
 * 'working' in a hive. This is achieved by performing a 'Cohesive Random Walk'. Working similarly to the random walk
 * function in the State class, CohesiveRandomWalk takes into account other nearby agents and is based loosely on
 * 'Flocking' algorithms.
 */
public class Working extends State {

    protected int cohesionRate;

    /**
     * Class constructor.
     * CohesionRate is used in determining how close the agent sticks to its local swarm
     * @param parent The parent agent, each parent can have only one state
     * @param cohesionRate Cohesion rate is usually taken from the simulation options. Should be less than 100.
     */
    public Working(Agent parent, int cohesionRate) {
        super(parent);
        this.cohesionRate = cohesionRate;
    }

    /**
     * Called every tick.
     * First check to see if we can perceive a threat. If we can change our parent's state into a
     * 'Guarding' state and set the parents threat to the one we have just perceived. Also place a pheromone.
     * If the parent has an alert level greater than zero, slowly decrement it
     * Finally perform a cohesive random walk.
     * @return A Coordinate target from the CohesiveRandomWalk function
     */
    public Coordinate GetTarget(){
        if(parent.getPerceptor().isThreatPerceived()){
            if(parent.getPerceptor().getThreat().getHP() > 0) {
                parent.PlacePheromone(parent.getOptions().getPheromoneStrength());
                Guarding nextState = new Guarding(parent);
                nextState.setThreat(parent.getPerceptor().getThreat());
                parent.setState(nextState);
            }
        }
        else if(parent.getAlertLevel() > 0){
            parent.IncreaseAlertLevel(-0.1d); //decrement alert level by tick if we cant see the threat
        }

        return CohesiveRandomWalk();
    }

    /**
     * Cohesive random walk is based loosely on Flocking algorithms.
     * Generates a random value between 0 and 100, if the value is smaller than the cohesion rate we try to move the
     * agent towards the swarm centre point. Otherwise we just walk randomly.
     * @return
     */
    public Coordinate CohesiveRandomWalk(){
        Random r = new Random();
        if(r.nextInt(100) < cohesionRate){
            return GetBestVector(parent.getPerceptor().GetPerceivedCenterpoint());
        }
        else{
            return RandomWalk();
        }
    }


}
