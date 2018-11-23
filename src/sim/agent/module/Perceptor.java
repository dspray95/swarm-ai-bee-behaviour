package sim.agent.module;

import sim.Coordinate;
import sim.Simulation;
import sim.agent.Agent;
import sim.agent.Bee;
import sim.agent.Hornet;
import sim.agent.Pheromone;
import sim.agent.state.bee.Mobbing;
import sim.config.Options;

import java.util.ArrayList;

/**
 * The perceptor class manages an Agents percepts in its current worldspace.
 * This includes both 'visual' (sight) and pheromone senses.
 * The perceptor keeps track of whether a threat has been perceived, and also the perceived centerpoint of
 * the local group of bees.
 */
public class Perceptor {

    private Agent parent;
    private ArrayList<Bee> perceivedBees;
    private Simulation environment;
    private Coordinate perceivedCenterpoint;
    private Coordinate actualSwarmCenterpoint;
    private int perceptionDistance;
    private boolean threatPerceived;
    private Hornet threat;
    private int perceivedMobSize;


    /**
     * Constructor for the perceptor class. Each perceptor instance must have one and only one parent Agent.
     * The environment is used when gathering information about the agents local area (defined by its perception
     * distance), which in turn is set by the simulation options.
     * @param parent
     */
    public Perceptor(Agent parent){
        this.parent = parent;
        this.environment = parent.getParent();
        this.perceptionDistance = environment.getOptions().getPerceptionDistance();
    }

    /**
     * This is called by the parent agent every tick. The Perceptor tick function gathers information about
     * any important percepts of the agent including:
     * <ul>
     *     <li>Threats</li>
     *     <li>Bees</li>
     *     <li>Pheromones</li>
     * </ul>
     * The perceived centerpoint - i.e the point at the middle of all the bees we can currently perceive - is also calculated,
     * as is the actual centrepoint of all bees in the simulation.
     */
    public void PerceptorTick() {
        int totalX = 0;
        int totalY = 0;
        int perceivedX = 0;
        int perceivedY = 0;
        perceivedMobSize = 0;
        perceivedBees = new ArrayList<>();
        //See if the agent can perceive the the Hornet in the simulation.
        if(parent.getLocation().DistanceTo(environment.getHornet().getLocation()) <= perceptionDistance){
                threatPerceived = true;
                threat = environment.getHornet();
        }
        //Get all the bees we can currently perceive
        for (Bee agent : environment.getSwarm()) {
            if (parent.getLocation().DistanceTo(agent.getLocation()) <= perceptionDistance && agent != parent && agent.getHP() > 0) {
                perceivedBees.add(agent);
                //Increment percieved X and Y positions of all alive perceived bees for use in calculating the perceived
                //centrepoint
                perceivedX = perceivedX + agent.getLocation().X();
                perceivedY = perceivedY + agent.getLocation().Y();
                if(agent.getState().getClass() == Mobbing.class){
                    perceivedMobSize++;
                }
            }
            //Increment total count of X and Y positions for use in calculating the exact centrepoint of the swarm
            totalX = totalX + agent.getLocation().X();
            totalY = totalY + agent.getLocation().Y();
        }
        //Gather all pheromones we can currently perceive
        for (Pheromone p : environment.getPheromones()){
            if(parent.getLocation().CircleIntersects(p.getLocation(), p.getRadius(), perceptionDistance)){
                parent.PheromonePerceived(p); //Notify the parent agent that we have perceived a pheromone.
            }
        }
        //Attempt to calculate the percieved centrepoint
        try{
            perceivedCenterpoint = new Coordinate(perceivedX / perceivedBees.size(), perceivedY / perceivedBees.size());
        }
        catch(ArithmeticException e){ //Divide by zero exception fires when no bees are perceived
            Options o = parent.getParent().getOptions();
            perceivedCenterpoint = new Coordinate(o.getEnvironmentSize()/2, o.getEnvironmentSize()/2);
        }

        actualSwarmCenterpoint = new Coordinate(totalX / environment.getSwarm().size(), totalY / environment.getSwarm().size());
    }

    public ArrayList<Bee> GetPerceivedBees(){
        return this.perceivedBees;
    }

    public Coordinate GetPerceivedCenterpoint(){
        return this.perceivedCenterpoint;
    }

    public boolean isThreatPerceived() {
        return threatPerceived;
    }

    public Coordinate getActualSwarmCenterpoint() {
        return actualSwarmCenterpoint;
    }

    public int getPerceivedMobSize(){
        return this.perceivedMobSize;
    }

    public Hornet getThreat() {
        return threat;
    }
}
