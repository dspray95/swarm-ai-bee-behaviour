package sim;

import sim.agent.Agent;
import sim.agent.Bee;
import sim.agent.Hornet;

import java.util.ArrayList;

public class Logger {

    ArrayList<ArrayList<Coordinate>> positionList;
    ArrayList<Coordinate> hornetPositions;
    ArrayList<ArrayList<Boolean>> aliveList;
    boolean initialised;

    public Logger(){
        initialised = false;
        positionList = new ArrayList<>();
        aliveList = new ArrayList<>();
    }

    public void logPositions(Simulation sim){
        ArrayList<Bee> swarm = sim.getSwarm();
        Hornet hornet = sim.getHornet();
        if(initialised){
            for(Agent agent : swarm){
                int index = swarm.indexOf(agent);
                positionList.get(index).add(agent.getLocation());
                aliveList.get(index).add(agent.getHP() <= 0);
            }
            hornetPositions.add(hornet.getLocation());
            aliveList.get(aliveList.size() -1 ).add(hornet.getHP() <= 0);
        }
        else{
            for(Agent agent : swarm){
                ArrayList<Coordinate> agentPositions = new ArrayList<>();
                ArrayList<Boolean> agentAlive = new ArrayList<>();
                agentAlive.add(agent.getHP() > 0);
                aliveList.add(agentAlive);
                agentPositions.add(agent.getLocation());
                positionList.add(agentPositions);
            }
            hornetPositions = new ArrayList<>();
            ArrayList<Boolean> hornetAlive = new ArrayList<>();
            hornetAlive.add(hornet.getHP() > 0);
            aliveList.add(hornetAlive);
            initialised = true;
        }
    }

    public ArrayList<ArrayList<Coordinate>> getPositionList() {
        return positionList;
    }

    public ArrayList<ArrayList<Boolean>> getAliveList(){
        return this.aliveList;
    }

    public ArrayList<Coordinate> getHornetPositions(){
        return this.hornetPositions;
    }
}
