package sim;

import sim.agent.Agent;
import sim.agent.listener.TickListener;
import sim.config.Defaults;

import java.util.ArrayList;

public class Mob extends ArrayList<Agent> implements TickListener {

    private Simulation parent;
    private int mobSize = 0;
    private double temperature = 0;

    public Mob(Simulation parent){
        this.parent = parent;
    }

    @Override
    public void Tick() {
        this.mobSize = FindMobSize();
        temperature = ((mobSize/10)*(mobSize/10)) + Defaults.BASE_TEMPERATURE;
    }


    public int FindMobSize(){
        int mobSize = 0;
        ArrayList<Agent> closedList = new ArrayList<>();
        ArrayList<Agent> frontier = new ArrayList<>();
        frontier.add(parent.getHornet());

        boolean searching = true;
        while(searching){
            ArrayList<Agent> newFrontier = new ArrayList<>();

            for(Agent node : frontier){
                for(Agent agent : this){
                    if(agent.getLocation().IsAdjacentTo(node.getLocation()) && !isInList(agent, newFrontier) && !isInList(agent, closedList)){
                        mobSize++;
                        newFrontier.add(agent);
                    }
                }
                closedList.add(node);
            }
            frontier = newFrontier;
            if(frontier.size() <= 0){
                searching = false;
            }
        }
        return mobSize;
    }

    public boolean isInList(Agent checkFor, ArrayList<Agent> closedList){
        for(Agent agent : closedList){
            if(checkFor.getLocation().Equals(agent.getLocation())){
                return true;
            }
        }
        return false;
    }

    public double getTemperature(){
        return this.temperature;
    }

    public int getMobSize(){
        return this.mobSize;
    }

}
