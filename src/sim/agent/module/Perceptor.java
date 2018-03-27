package sim.agent.module;

import sim.Coordinate;
import sim.Simulation;
import sim.agent.Agent;
import sim.agent.Bee;
import sim.agent.Hornet;
import sim.agent.Pheromone;
import sim.config.Options;

import java.util.ArrayList;

public class Perceptor {

    private Agent parent;
    private ArrayList<Bee> perceivedBees;
    private Simulation environment;
    private Coordinate swarmCenterpoint;
    private int perceptionDistance;
    private boolean threatPerceived;
    private Hornet threat;

    public Perceptor(Agent parent){
        this.parent = parent;
        this.environment = parent.getParent();
        this.perceptionDistance = environment.getOptions().getPerceptionDistance();
    }

    public void PerceptorTick() {
        int totalX = 0;
        int totalY = 0;
        perceivedBees = new ArrayList<>();

        if(parent.getLocation().DistanceTo(environment.getHornet().getLocation()) <= perceptionDistance){
            threatPerceived = true;
            threat = environment.getHornet();
        }

        //Get all the bees we can currently perceive
        for (Bee agent : environment.getSwarm()) {
            if (parent.getLocation().DistanceTo(agent.getLocation()) <= perceptionDistance && agent != parent && agent.getHP() > 0) {
                perceivedBees.add(agent);
                totalX = totalX + agent.getLocation().X();
                totalY = totalY + agent.getLocation().Y();
            }
        }

        for (Pheromone p : environment.getPheromones()){
            if(parent.getLocation().CircleIntersects(p.getLocation(), p.getRadius(), p.getRadius())){
                System.out.println("pheromone perceived at strength " + p.getStrength());
                parent.increaseAlertLevel(p.getStrength());
            }
        }
        try{
            swarmCenterpoint = new Coordinate(totalX / perceivedBees.size(), totalY / perceivedBees.size());
        }
        catch(ArithmeticException e){ //Divide by zero exception fires when no bees are perceived
            Options o = parent.getParent().getOptions();
            swarmCenterpoint = new Coordinate(o.getEnvironmentSize()/2, o.getEnvironmentSize()/2);
        }
    }

    public ArrayList<Bee> GetPerceivedBees(){
        return this.perceivedBees;
    }

    public Coordinate GetSwarmCenterpoint(){
        return this.swarmCenterpoint;
    }

    public boolean isThreatPerceived() {
        return threatPerceived;
    }

    public Hornet getThreat() {
        return threat;
    }
}
