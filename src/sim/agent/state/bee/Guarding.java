package sim.agent.state.bee;

import sim.Coordinate;
import sim.agent.Agent;
import sim.agent.state.State;
import sim.config.Defaults;

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
                return parent.getLocation();
            }
            if(new Random().nextInt(10) > 1) {
                if (parent.getLocation().DistanceTo(threat.getLocation()) >= parent.getParent().getOptions().getPerceptionDistance() / 1.5) {
                    return Threaten();
                }
                else {
                    return Retreat();
                }
            }
            else{
                return RandomWalk();
            }
        }
        else{
            //do patrolling things
            return RandomWalk();
        }
    }

    /**
     * Willingness if a function of the distance to the threat, the number of nearby bees, and the aggression of the parent
     * agent
     * willingness w = inverse square of distance (d) * aggression (a) * size of mob(c)
     *               = sqrt(d)/d^2 * a * c
     * if c = 0 then c = c + 1;
     * @return
     */
    public double getWillingness(){
        double distance = parent.getLocation().DistanceTo(threat.getLocation());
        double aggression = parent.getAggression();
        double mobSize = parent.getPerceptor().getPerceivedMobSize();
        //If the mob size <= 0, we need to give it some value, otherwise the mob would never start.
        if(mobSize <= 0){
            mobSize = 1;
        }
        double willingness =  (aggression * mobSize)/distance;
        return (aggression * mobSize)/distance;
    }

    public Coordinate Retreat(){
        //get our target vector
        Coordinate targetVector = VectorToCoordinate(parent.getLocation(), threat.getLocation());
        //reverse target vector direction
        if(targetVector.X() != 0){
            targetVector.setX(targetVector.X() * -1);
        }
        if(targetVector.Y() != 0){
            targetVector.setY(targetVector.Y() * -1);
        }
        //vector to actual location;
        return new Coordinate(parent.getLocation().X() + targetVector.X(), parent.getLocation().Y() + targetVector.Y());
    }

    public Coordinate Threaten() {
        Coordinate targetVector = VectorToCoordinate(parent.getLocation(), threat.getLocation());
        return new Coordinate(parent.getLocation().X() + targetVector.X(), parent.getLocation().Y() + targetVector.Y());
    }

    public void setThreat(Agent threat){
        this.threat = threat;
    }
}
