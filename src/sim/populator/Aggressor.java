package sim.populator;

import sim.config.AggressionSetting;

import java.util.concurrent.ThreadLocalRandom;

import static sim.config.AggressionSetting.CLOSE_TO_VALUE;
import static sim.config.AggressionSetting.RANDOM_SPREAD;
import static sim.config.AggressionSetting.UNIFORM;

public class Aggressor {

    AggressionSetting setting;
    double aggressionVal;
    int swarmSize;
    int countHigh;
    int countLow;

    public Aggressor(AggressionSetting setting, double aggressionVal, int swarmSize){
        this.setting = setting;
        this.aggressionVal = aggressionVal;
        this.swarmSize = swarmSize;
        this.countHigh = 0;
        this.countLow = 0;
    }

    public double getAggression(){
        //value may be zero for testing purposes occasionally
        if(aggressionVal == 0){
            return 0;
        }

        switch(setting){
            case UNIFORM:
                return aggressionVal;
            case RANDOM_SPREAD:
                return ThreadLocalRandom.current().nextDouble(aggressionVal*2);
            case CLOSE_TO_VALUE:
                return ThreadLocalRandom.current().nextDouble(aggressionVal/2, aggressionVal*1.5);
            case FEW_HIGH:
                return FewHighAggression();
            case MANY_LOW:
                return ManyLowAggression();
            case FEW_HIGH_LOW:
                return FewHighFewLowAggression();
            default:
                return aggressionVal;
        }
    }

    public double FewHighAggression(){
        if(countHigh < swarmSize/10){
            countHigh++;
            return ThreadLocalRandom.current().nextDouble(aggressionVal + (aggressionVal/2), aggressionVal*2);
        } else return aggressionVal;
    }

    public double ManyLowAggression(){
        if(countLow < swarmSize - (swarmSize/10) ){
            countLow++;
            return ThreadLocalRandom.current().nextDouble(0, aggressionVal - (aggressionVal/2));
        } else return aggressionVal;
    }

    public double FewHighFewLowAggression(){
        if(countHigh < swarmSize/10){
            countHigh++;
            return ThreadLocalRandom.current().nextDouble(aggressionVal + (aggressionVal/2), aggressionVal*2);
        }
        else if(countLow < swarmSize/10) {
            countLow++;
            return ThreadLocalRandom.current().nextDouble(aggressionVal + (aggressionVal/2), aggressionVal*2);
        } else return aggressionVal;
    }
}
