package sim.agent.module;

import sim.Coordinate;
import sim.Simulation;
import sim.agent.Agent;
import sim.agent.Bee;
import sim.config.Options;

import java.util.ArrayList;

public class Perceptor {

    Agent parent;
    ArrayList<Bee> perceivedBees;
    Simulation environment;
    Coordinate swarmCenterpoint;
    int perceptionDistance;

    public Perceptor(Agent parent){
        this.parent = parent;
        this.environment = parent.getParent();
        this.perceptionDistance = environment.getOptions().getPerceptionDistance();
    }

    public void PerceptorTick() {
        int totalX = 0;
        int totalY = 0;
        perceivedBees = null;
        perceivedBees = new ArrayList<>();
        //Get all the bees we can currently perceive
        for (Bee agent : environment.getSwarm()) {
            if (parent.getLocation().DistanceTo(agent.getLocation()) <= perceptionDistance) {
                perceivedBees.add(agent);
                totalX = totalX + agent.getLocation().X();
                totalY = totalY + agent.getLocation().Y();
            }
        }
        //TODO divide by zero exception
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
}
