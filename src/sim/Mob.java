package sim;

import sim.agent.Agent;
import sim.agent.listener.TickListener;

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
        FindMobSize();
    }

    public void FindMobSize(){
        mobSize = 0;
        ArrayList<Agent> closedList = new ArrayList<>();
        ArrayList<Agent> frontier = new ArrayList<>();
        frontier.add(parent.getHornet());

        boolean searching = true;
        while(searching){
            ArrayList<Agent> newFrontier = new ArrayList<>();
            for(Agent node : frontier){
                for(Agent agent : this){
                    if(agent.getLocation().IsAdjacentTo(node.getLocation()) && !closedList.contains(agent)){
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
    }

    public int getMobSize(){
        return this.mobSize;
    }

}
