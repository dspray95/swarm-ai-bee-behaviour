package sim.agent.state.bee;

import sim.Coordinate;
import sim.Mob;
import sim.agent.Agent;
import sim.agent.Bee;
import sim.agent.state.State;
import sim.config.Defaults;

import java.util.Random;

public class Mobbing extends State {

    private Agent target;
    private Mob mob;
    private boolean leaving;

    public Mobbing(Agent parent, Agent target) {
        super(parent);
        this.target = target;
        this.mob = parent.getParent().getMob();
        this.leaving = false;
        if(!mob.contains(parent)){
            this.mob.add(parent);
        }
    }

    @Override
    public Coordinate GetTarget() {
        /*
        If the temp is too high, try to leave
         */
        parent.setTemperature(mob.getTemperature());
        if(target.getHP() <= 0){
            if(parent.getLocation().DistanceTo(target.getLocation()) > parent.getOptions().getPerceptionDistance()/2){
                parent.setState(new Working(parent, parent.getOptions().getCohesionRate()));
                return parent.getLocation();
            }
            else{
                return GetBestVector(LeaveMobVector());
            }
        }
        if(leaving){
            if(parent.getLocation().DistanceTo(target.getLocation()) > parent.getOptions().getPerceptionDistance()/2){
                mob.modifyCurrentlyLeaving(-1);
                Guarding nextState = new Guarding(parent);
                nextState.setThreat(target);
                parent.setState(nextState);
                return GetBestVector(target.getLocation());
            }
            else{
                return GetBestVector(LeaveMobVector());
            }
        }

        if(parent.getTemperature() >= Defaults.BEE_LETHAL_TEMPERATURE - 2){
            Coordinate leavingVector = LeaveMobVector();
            if(GetBestVector(leavingVector).Equals(parent.getLocation())){
                return GetBestVector(target.getLocation());
            }
            else if(mob.getCurrentlyLeaving() < 3) {
                mob.modifyCurrentlyLeaving(1);
                leaving = true;
                return GetBestVector(LeaveMobVector());
            }
            else{
                return GetBestVector(target.getLocation());
            }
        }
        else{
            return GetBestVector(target.getLocation());
        }
    }

    public Coordinate LeaveMobVector(){
        //get the vector to center of the mob, will be the location of the hornet
        Coordinate centerOfMob = VectorToTarget(parent.getLocation(), parent.getPerceptor().getThreat().getLocation());
        //reverse target vector direction
        Coordinate leaveVector = new Coordinate();
        if (centerOfMob .X() != 0) {
            leaveVector.setX(centerOfMob .X() * -1);
        }
        if (centerOfMob .Y() != 0) {
            leaveVector.setY(centerOfMob .Y() * -1);
        }

        return new Coordinate(leaveVector.X() + parent.getLocation().X(), leaveVector.Y() + parent.getLocation().Y());
    }
}
