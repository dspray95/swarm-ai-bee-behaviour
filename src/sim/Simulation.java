package sim;

import com.sun.org.apache.bcel.internal.generic.PopInstruction;
import sim.agent.Agent;
import sim.agent.Bee;
import sim.agent.Hornet;
import sim.agent.Pheromone;
import sim.agent.listener.TickListener;
import sim.config.Options;

import java.util.ArrayList;

public class Simulation {

    Options options;
    ArrayList<Bee> swarm;
    ArrayList<Pheromone> pheromones;
    Hornet hornet;
    ArrayList<TickListener> tickListeners;
    Logger logger;

    public Simulation(Options options){
        this.swarm = new ArrayList<>();
        this.pheromones = new ArrayList<>();
        this.tickListeners = new ArrayList<>();
        this.options = options;
        this.logger = new Logger();
        new Populator(this, options).Populate();
    }

    public void Tick(){
        for(TickListener listener : tickListeners){
            listener.Tick();
        }
        for(Pheromone p : pheromones){
            tickListeners.add(p);
        }
    }

    public boolean runForTicks(int numTicks){

        for(int i = 0; i<=numTicks; i++){
            this.Tick();
            logger.logPositions(this);
        }
        return true;
    }

    public void RemovePheromone(Pheromone p){
        pheromones.remove(pheromones.get(pheromones.indexOf(p)));
    }
    public void AddPheremone(Pheromone p){
        this.pheromones.add(p);
    }

    public Options getOptions(){
        return this.options;
    }

    public ArrayList<Bee> getSwarm(){
        return this.swarm;
    }

    public Hornet getHornet(){
        return this.hornet;
    }

    public Logger getLogger(){
        return this.logger;
    }

    public ArrayList<Pheromone> getPheromones(){
        return this.pheromones;
    }
}
