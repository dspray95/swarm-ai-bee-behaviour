package sim.agent.state.hornet;

import sim.Coordinate;
import sim.agent.Agent;
import sim.agent.state.State;
import sim.config.Defaults;
import sun.security.ssl.Debug;

import java.util.Random;

public class Attacking extends State {

    Agent target;

    public Attacking(Agent parent, Agent target) {
        super(parent);
        this.target = target;
        System.out.println("STATE: attacking");
    }

    /**
     * Perfoms an attack roll on the target if the target is close enough
     * Returns to the hunting state or a leaving state if the target is killed
     * @return
     */
    @Override
    public Coordinate GetTarget() {
        if(target.getHP() <= 0){
            parent.increaseBeesKilled();
            if(parent.getBeesKilled() > 3 && parent.getBeesKilled() * 15 > new Random().nextInt(100)) {
                parent.setState(new Leaving(parent));
            }
            else{
                parent.setState(new Hunting(parent));
            }

            return parent.getLocation();
        }
        else {
            if(isProximate(target.getLocation())){
                target.Damage(parent.AttackRoll());
                return parent.getLocation();
            }
            else{
                Coordinate targetVector = VectorToCoordinate(parent.getLocation(), target.getLocation());
                return new Coordinate(parent.getLocation().X() + targetVector.X(),
                        parent.getLocation().Y() + targetVector.Y());
            }
        }
    }

    public boolean isProximate(Coordinate target){
        for(Coordinate coord : Defaults.VECTORS){
            Coordinate vectorLocation = new Coordinate(coord.X() + parent.getLocation().X(), coord.Y() + parent.getLocation().Y());
            if(vectorLocation.X() == target.X() && vectorLocation.Y() == target.Y()){
                return true;
            }
        }
        return false;
    }
}
