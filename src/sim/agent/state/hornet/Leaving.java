package sim.agent.state.hornet;

import sim.Coordinate;
import sim.agent.Agent;
import sim.agent.state.State;

import java.util.Random;

public class Leaving extends State {

    public Leaving(Agent parent) {
        super(parent);
        System.out.print("STATE: leaving");
    }

    @Override
    public Coordinate GetTarget() {
        if(new Random().nextInt(100) > 25){
            return getLeaveTarget();
        }
//        else{
            return RandomWalk();
//        }
    }

    public Coordinate getLeaveTarget(){
        //First we need to get the vector to the center of the swarm
        Coordinate vectorFromCenter = VectorToCoordinate(parent.getLocation(), parent.getPerceptor().GetSwarmCenterpoint());
        //then we can invert it
        vectorFromCenter.setXY(vectorFromCenter.X() * -1, vectorFromCenter.Y() * -1);
        return new Coordinate(parent.getLocation().X() + vectorFromCenter.X(), parent.getLocation().Y() + vectorFromCenter.Y());
    }
}
