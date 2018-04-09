package sim;

import sim.agent.Agent;
import sim.agent.Bee;
import sim.agent.Hornet;
import sim.config.Options;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Populator {

    Simulation parent;
    Options options;

    public Populator(Simulation parent, Options options){
        this.parent = parent;
        this.options = options;
    }

    public void Populate(){
        for(int i = 0; i < options.getSwarmSize(); i++){
            Coordinate location = new Coordinate();
            int areaLowerBound = (options.getEnvironmentSize() - options.getDeploymentArea());
            boolean gettingCoordinate = true;
            while(gettingCoordinate){
                boolean coordinateClash = false;
                location.RandomCoordinate(options.getDeploymentArea(), areaLowerBound);
                for(Agent agent : parent.getSwarm()){
                    if(location.X() == agent.getLocation().X() && location.Y() == agent.getLocation().Y()){
                        coordinateClash = true;
                        break;
                    }
                }
                if(!coordinateClash){
                    gettingCoordinate = false;
                }
            }
            Bee bee = new Bee(parent, location, getAggression());
            parent.getTickListeners().add(bee);
            parent.getSwarm().add(bee);

        }
        parent.setHornet(new Hornet(parent, new Coordinate(options.getEnvironmentSize()/2, options.getDeploymentArea()/2-50)));
    }

    public double getAggression(){
        double aggressionVal = options.getAggression();
        //value may be zero for testing purposes occasionally
        if(aggressionVal == 0){
            return 0;
        }

        switch(options.getAggressionSetting()){
            case UNIFORM:
                return aggressionVal;
            case RANDOM_SPREAD:
                return ThreadLocalRandom.current().nextDouble(aggressionVal);
            case CLOSE_TO_VALUE:
                return ThreadLocalRandom.current().nextDouble(aggressionVal/2, aggressionVal*1.5);
            default:
                return aggressionVal;
        }
    }
}
