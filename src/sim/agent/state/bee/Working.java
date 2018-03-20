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
        return CohesiveRandomWalk();
    }

    public Coordinate CohesiveRandomWalk(){
        Random r = new Random();
        if(r.nextInt(100) <= cohesionRate){
            Coordinate current = parent.getLocation();
            Coordinate cohesiveVector = VectorToCoordinate(current, parent.getPerceptor().GetSwarmCenterpoint());
            return new Coordinate(current.X() + cohesiveVector.X(), current.Y() + cohesiveVector.Y());
        }
        else{
            return RandomWalk();
        }
    }


}
