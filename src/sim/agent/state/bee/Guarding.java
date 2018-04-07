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
        Coordinate centerPoint = new Coordinate(parent.getOptions().getEnvironmentSize(), parent.getOptions().getEnvironmentSize());
        if(threat != null){
            if(getWillingness() > new Random().nextInt(100)){
                parent.setState(new Mobbing(parent, threat));
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
        Coordinate targetVector = VectorToTarget(parent.getLocation(), threat.getLocation());
        Coordinate centerVector = VectorToTarget(parent.getLocation(), parent.getPerceptor().getActualSwarmCenterpoint());
        //reverse target vector direction
        Coordinate runAwayVector = new Coordinate();
        if (targetVector.X() != 0) {
            runAwayVector.setX(targetVector.X() * -1);
        }
        if (targetVector.Y() != 0) {
            runAwayVector.setY(targetVector.Y() * -1);
        }

        return GetBestVector(VectorToCoordinate(runAwayVector, parent.getLocation()));

    }

    public Coordinate Threaten() {
        return GetBestVector(threat.getLocation());
    }

    public void setThreat(Agent threat){
        this.threat = threat;
    }
}
