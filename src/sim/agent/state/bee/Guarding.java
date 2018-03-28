package sim.agent.state.bee;

import sim.Coordinate;
import sim.agent.Agent;
import sim.agent.state.State;

import java.util.Random;

public class Guarding extends State {

    Agent threat;
    int threshold;

    public Guarding(Agent parent) {
        super(parent);
        threshold = 50;
    }

    /**
     * Guard state - patrol, flee, and begin mobbing functionality
     * if a threat is detected:
         * gets willingness to begin mobbing behaviour based on aggression
         * performs roll to see if the agent will begin mobbing this tick
         * otherwise try to run away
     * if no threat is detected:
         * move around the edge of the swarm
     * @return
     */
    @Override
    public Coordinate GetTarget() {
        if(threat != null){
            if(getWillingness() > new Random().nextInt(100)){
                parent.setState(new Mobbing(parent));
            }
            else{
                //run away
            }
        }
        else{
            //do patrolling things
        }
        return RandomWalk();
    }

    /**
     * Willingness if a function of the distance to the threat, the number of nearby bees, and the aggression of the parent
     * agent
     * willingness w = inverse square of distance (d) * aggression (a) * size of mob(c)
     * if c = 0 then c = c + 1;
     * @return
     */
    public double getWillingness(){
        double distance = parent.getLocation().DistanceTo(threat.getLocation());
        double inverseSquareDistance = Math.sqrt(distance)/Math.exp(distance);
        double aggression = parent.getAggression();
        double mobSize = parent.getPerceptor().getPerceivedMobSize();

        if(mobSize <= 0){
            mobSize++;
        }

        return inverseSquareDistance * aggression * mobSize;
    }

    public Coordinate Retreat(){

    }

    public void setThreat(Agent threat){
        this.threat = threat;
    }
}
