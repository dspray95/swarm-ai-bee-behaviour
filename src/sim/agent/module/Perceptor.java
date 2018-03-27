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
    private Coordinate perceivedCenterpoint;
    private Coordinate actualSwarmCenterpoint;
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
        int perceivedX = 0;
        int perceivedY = 0;
        perceivedBees = new ArrayList<>();

        if(parent.getLocation().DistanceTo(environment.getHornet().getLocation()) <= perceptionDistance){
            threatPerceived = true;
            threat = environment.getHornet();
        }

        //Get all the bees we can currently perceive
        for (Bee agent : environment.getSwarm()) {
            if (parent.getLocation().DistanceTo(agent.getLocation()) <= perceptionDistance && agent != parent && agent.getHP() > 0) {
                perceivedBees.add(agent);
                perceivedX = perceivedX + agent.getLocation().X();
                perceivedY = perceivedY + agent.getLocation().Y();
            }
            totalX = totalX + agent.getLocation().X();
            totalY = totalY + agent.getLocation().Y();
        }

        for (Pheromone p : environment.getPheromones()){
            if(parent.getLocation().CircleIntersects(p.getLocation(), p.getRadius(), p.getRadius())){
                System.out.println("pheromone perceived at strength " + p.getStrength());
                parent.increaseAlertLevel(p.getStrength());
            }
        }

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

    public Hornet getThreat() {
        return threat;
    }
}
