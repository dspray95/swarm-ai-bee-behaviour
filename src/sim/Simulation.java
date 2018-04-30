package sim;

import sim.agent.Bee;
import sim.agent.Hornet;
import sim.agent.Pheromone;
import sim.agent.listener.TickListener;
import sim.agent.state.bee.Working;
import sim.config.Options;
import sim.populator.Populator;

import java.util.ArrayList;

public class Simulation {

    private Options options;
    private ArrayList<Bee> swarm;
    private ArrayList<Pheromone> pheromones;
    private Hornet hornet;
    private ArrayList<TickListener> tickListeners;
    private Logger logger;
    private Mob mob;
    private int productivity;
    private long timeToKill;

    public Simulation(Options options){
        this.swarm = new ArrayList<>();
        this.pheromones = new ArrayList<>();
        this.tickListeners = new ArrayList<>();
        this.options = options;
        this.logger = new Logger();
        this.mob = new Mob(this);
        this.tickListeners.add(mob);
        this.productivity = 0;
        new Populator(this, options).Populate();
    }

    public void Tick(){
        for(TickListener listener : tickListeners){
            listener.Tick();
            if(listener.getClass() == Bee.class){
                Bee bee = (Bee)listener;
                if(bee.getState().getClass() == Working.class){
                    this.productivity++;
                }
                if(hornet.IsEscaped()){
                    this.productivity = 0;
                }
            }
        }
        for(Pheromone p : pheromones){
            tickListeners.add(p);
        }
    }

    public boolean runForTicks(int numTicks){
        long startTime = System.nanoTime();
        long endTime = 0;
        for(int i = 0; i<=numTicks; i++){
            this.Tick();
            if(hornet.getHP() <= 0){
                endTime = System.nanoTime();
                timeToKill = endTime - startTime;
            }
            logger.Log(this);
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

    public void setHornet(Hornet hornet){
        this.hornet = hornet;
        this.tickListeners.add(this.hornet);
    }
    public Logger getLogger(){
        return this.logger;
    }

    public ArrayList<Pheromone> getPheromones(){
        return this.pheromones;
    }

    public ArrayList<TickListener> getTickListeners(){
        return this.tickListeners;
    }

    public Mob getMob(){
        return this.mob;
    }

    public int getProductivity(){
        return this.productivity;
    }

    public long getTimeToKill(){
        return this.timeToKill;
    }
}

