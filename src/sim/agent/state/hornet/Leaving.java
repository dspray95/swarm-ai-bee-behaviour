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
        else{
            return RandomWalk();
        }
    }

    public Coordinate getLeaveTarget(){
        //First we need to get the vector to the center of the swarm
        Coordinate current = parent.getLocation();
        Coordinate targetVector = VectorToCoordinate(current, parent.getPerceptor().getActualSwarmCenterpoint());
//        then we can invert it
        if(targetVector.X() != 0){
            targetVector.setX(targetVector.X() * -1);
        }
        if(targetVector.Y() != 0){
            targetVector.setY(targetVector.Y() * -1);
        }
        return new Coordinate(current.X() + targetVector.X(), current.Y() + targetVector.Y());
    }
}
