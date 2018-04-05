package sim.agent.state.bee;

import sim.Coordinate;
import sim.agent.Agent;
import sim.agent.state.State;

import java.util.Random;

public class Working extends State {

    protected int cohesionRate;

    public Working(Agent parent, int cohesionRate) {
        super(parent);
        this.cohesionRate = cohesionRate;
    }

    public Coordinate GetTarget(){
        if(parent.getPerceptor().isThreatPerceived()){
            parent.setPheromone(parent.getOptions().getPheromoneStrength());
            Guarding nextState = new Guarding(parent);
            nextState.setThreat(parent.getPerceptor().getThreat());
            parent.setState(nextState);
            return parent.getLocation();
        }
        else if(parent.getAlertLevel() > 0){
            parent.increaseAlertLevel(-0.1d); //decrement alert level by tick if we cant see the threat
        }
        return CohesiveRandomWalk();
    }

    public Coordinate CohesiveRandomWalk(){
        Random r = new Random();
        if(r.nextInt(100) <= cohesionRate){
            return GetBestVector(parent.getPerceptor().GetPerceivedCenterpoint());
        }
        else{
            return RandomWalk();
        }
    }


}
