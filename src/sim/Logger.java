package sim;

import sim.agent.Agent;
import sim.agent.Bee;
import sim.agent.Hornet;

import java.util.ArrayList;

public class Logger {

    ArrayList<ArrayList<Coordinate>> positionList;
    ArrayList<Coordinate> hornetPositions;
    boolean initialised;

    public Logger(){
        initialised = false;
        positionList = new ArrayList<>();
    }

    public void logPositions(Simulation sim){
        ArrayList<Bee> swarm = sim.getSwarm();
        Hornet hornet = sim.getHornet();
        if(initialised){
            for(Agent agent : swarm){
                int index = swarm.indexOf(agent);
                positionList.get(index).add(agent.getLocation());
            }
            hornetPositions.add(hornet.getLocation());
        }
        else{
            for(Agent agent : swarm){
                ArrayList<Coordinate> agentPositions = new ArrayList<>();
                agentPositions.add(agent.getLocation());
                positionList.add(agentPositions);
            }
            hornetPositions = new ArrayList<>();
            initialised = true;
        }
    }

    public ArrayList<ArrayList<Coordinate>> getPositionList() {
        return positionList;

    }
    public ArrayList<Coordinate> getHornetPositions(){
        return this.hornetPositions;
    }
}
