package sim;

import sim.agent.Agent;
import sim.agent.Bee;
import sim.agent.Hornet;
import sim.agent.listener.TickListener;
import sim.config.Options;

import java.util.ArrayList;

public class Simulation {

    Options options;
    ArrayList<Bee> swarm;
    Hornet hornet;
    ArrayList<TickListener> tickListeners;
    Logger logger;

    public Simulation(Options options){
        this.swarm = new ArrayList<>();
        this.tickListeners = new ArrayList<>();
        this.options = options;
        this.logger = new Logger();
        Populate();
    }

    public void Populate(){
        for(int i = 0; i < options.getSwarmSize(); i++){
            Coordinate location = new Coordinate();
            int areaLowerBound = (options.getEnvironmentSize() - options.getDeploymentArea());
            boolean gettingCoordinate = true;
            while(gettingCoordinate){
                boolean coordinateClash = false;
                location.RandomCoordinate(options.getDeploymentArea(), areaLowerBound);
                for(Agent agent : swarm){
                    if(location.X() == agent.getLocation().X() && location.Y() == agent.getLocation().Y()){
                        coordinateClash = true;
                        break;
                    }
                }
                if(!coordinateClash){
                    gettingCoordinate = false;
                }
            }
            Bee bee = new Bee(this, location);
            tickListeners.add(bee);
            swarm.add(bee);

        }
        hornet = new Hornet(this, new Coordinate(options.getEnvironmentSize()/2, options.getDeploymentArea()/2-50));
        tickListeners.add(hornet);
    }

    public void Tick(){
        for(TickListener listener : tickListeners){
            listener.Tick();
        }
    }

    public boolean runForTicks(int numTicks){

        for(int i = 0; i<=numTicks; i++){
            this.Tick();
            logger.logPositions(this);
        }
        return true;
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
}
