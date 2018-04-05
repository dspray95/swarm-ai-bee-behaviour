package sim.agent.state.hornet;

import sim.Coordinate;
import sim.agent.Agent;
import sim.agent.Bee;
import sim.agent.state.State;
import sim.config.Defaults;

import java.util.ArrayList;
import java.util.Random;

public class Hunting extends State {

    Bee targetBee;
    /**
     * Searching state of the hornet
     * @param parent
     */
    public Hunting(Agent parent) {
        super(parent);
        System.out.println("STATE: hunting");
    }

    /**
     * If there is a valid target to hunt, notify the parent to change state
     * otherwise we can continue seeking by either getting closer to the swarm center or
     * random walking.
     * @return
     */
    @Override
    public Coordinate GetTarget() {
        if(CheckForTarget()){
            parent.setState(new Attacking(parent, targetBee));
            return GetBestVector(targetBee.getLocation());
        }
        else if(new Random().nextInt(100) < 75) {
            return GetBestVector(parent.getPerceptor().GetPerceivedCenterpoint());
        }
        else{
            return RandomWalk();
        }
    }

    /**
     * Checks to see if the proposed target bee is actually valid through
     * using the perceptor to find out the number of other bees around it
     * @return
     */
    public boolean CheckForTarget(){
        ArrayList<Bee> perceivedBees = parent.getPerceptor().GetPerceivedBees();
        Bee closestBee = GetClosestBee(perceivedBees);

        if(closestBee == null || closestBee.getHP() <= 0){
            return false;
        }
        else{
            targetBee = closestBee;
            return GetValidTarget(closestBee, perceivedBees);
        }
    }

    /** Finds the closest potential target bee
     * @param perceivedBees All potential target bees
     * @return
     */
    public Bee GetClosestBee(ArrayList<Bee> perceivedBees){
        Bee closestBee = null;
        double distanceToClosestBee = Defaults.ENVIRONMENT_SIZE; //ToDo: potential issue here if sim environemnt size is greater than the default size
        for(Bee bee : perceivedBees){
            double distanceToBee = parent.getLocation().DistanceTo(bee.getLocation());
            if(distanceToBee < distanceToClosestBee && bee.getHP() > 0){
                closestBee = bee;
                distanceToClosestBee = distanceToBee;
            }
        }
        return closestBee;
    }

    /**
     * Checks to see if the target bee is valid for attacking
     * Its true if the number of bees near the target bee is smaller than a threshold variable
     * @param targetBee Potential target bee
     * @param perceivedBees All perceived bees
     * @return
     */
    public boolean GetValidTarget(Bee targetBee, ArrayList<Bee> perceivedBees){
        int proximityCount = 0;
        for(Bee perceivedBee : perceivedBees){
            if(perceivedBee.getLocation().DistanceTo(targetBee.getLocation()) <= Defaults.HORNET_TARGET_RADIUS){
                proximityCount++;
            }
        }
        return proximityCount <= Defaults.HORNET_TARGET_THRESHOLD;
    }

}
