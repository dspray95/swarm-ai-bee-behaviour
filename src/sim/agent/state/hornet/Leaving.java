package sim.agent.state.hornet;

import sim.Coordinate;
import sim.agent.Agent;
import sim.agent.state.State;

import java.util.Random;

public class Leaving extends State {

    Coordinate leaveTarget;

    public Leaving(Agent parent) {
        super(parent);
        System.out.print("STATE: leaving");
        //initialise the vector for the leave target
        leaveTarget = getLeaveTarget();
        //set the leave target absolute position to the edge of the environment
        //-1 to avoid out of bounds issues
        leaveTarget.setX(leaveTarget.X() * (parent.getOptions().getEnvironmentSize() - 1));
        leaveTarget.setY(leaveTarget.Y() * (parent.getOptions().getEnvironmentSize() - 1));
    }

    @Override
    public Coordinate GetTarget() {
        if(new Random().nextInt(100) > 25){
            return GetBestVector(getLeaveTarget());
        }
        else{
            return RandomWalk();
        }
    }

    public Coordinate getLeaveTarget(){
        //First we need to get the vector to the center of the swarm
        Coordinate current = parent.getLocation();
        Coordinate targetVector = VectorToTarget(current, parent.getPerceptor().getActualSwarmCenterpoint());
//        then we can invert it
        if(targetVector.X() != 0){
            targetVector.setX(targetVector.X() * -1);
        }
        if(targetVector.Y() != 0){
            targetVector.setY(targetVector.Y() * -1);
        }
        return targetVector;
    }
}
