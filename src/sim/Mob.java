package sim;

import sim.agent.Agent;
import sim.agent.listener.TickListener;
import sim.config.Defaults;

import java.util.ArrayList;

public class Mob extends ArrayList<Agent> implements TickListener {

    private Simulation parent;
    private int mobSize = 0;
    private int currentlyLeaving = 0;
    private double temperature = 0;

    public Mob(Simulation parent){
        this.parent = parent;
        this.temperature = Defaults.BASE_TEMPERATURE;
    }

    @Override
    public void Tick() {
        this.mobSize = FindMobSize();
        /*
        Temperature = T1 + (T2-T1/T1)
        Where:
            T1 = current temperature
        and T2 = (n/5)^2 + T1
                where:
                    n = mob size
        So Temperature
        = T1 + ((n/5)^2 + T1 - T1)/T1
        = T1 + (n/5)^2 / T1
         */
        temperature = Defaults.BASE_TEMPERATURE + (((mobSize/2)*(mobSize/2) - temperature)/Defaults.BASE_TEMPERATURE);

        if(temperature > Defaults.BASE_TEMPERATURE){
            temperature--;
        }
        if(temperature >= Defaults.HORNET_LETHAL_TEMPERATURE){
            double damage = Defaults.HORNET_TARGET_RADIUS - temperature + 1;
            parent.getHornet().Damage(damage/10);
        }
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

    public void setCurrentlyLeaving(int val){
        this.currentlyLeaving = val;
    }

    public int getCurrentlyLeaving(){
        return this.currentlyLeaving;
    }

    public void modifyCurrentlyLeaving(int val){
        this.currentlyLeaving += val;
    }
}
