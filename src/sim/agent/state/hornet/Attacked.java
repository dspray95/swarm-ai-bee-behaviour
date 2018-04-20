package sim.agent.state.hornet;

import sim.Coordinate;
import sim.agent.Agent;
import sim.agent.Bee;
import sim.agent.state.State;

public class Attacked extends State{

    Bee target;
    Leaving leaving;
    Hunting hunting;

    public Attacked(Agent parent) {
        super(parent);
        this.leaving = new Leaving(parent);
        this.hunting = new Hunting(parent);
    }

    @Override
    public Coordinate GetTarget() {
        Leaving leaving = new Leaving(parent);
        Coordinate leavingVector = leaving.getLeaveTarget();
        if(GetBestVector(leavingVector).Equals(parent.getLocation())){
            if(target != null && isProximate(target.getLocation())){
                target.Damage(parent.AttackRoll());
                if(target.getHP() <= 0){
                    target = null;
                }
            }
            else{
                target = hunting.GetClosestBee(parent.getPerceptor().GetPerceivedBees());
            }
            return GetBestVector(leavingVector);
        }
        else{
            return GetBestVector(leavingVector);
        }
    }
}
