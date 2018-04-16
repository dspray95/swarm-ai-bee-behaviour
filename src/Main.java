import sim.Logger;
import sim.Simulation;
import sim.config.AggressionSetting;
import sim.config.Options;

import java.io.IOException;

public class Main {

    private static Options options;
    private static Simulation sim;
    private static Logger logger;

    public static void main(String[] args){
        if(args.length > 0){
            options = ParseArgs(args);
        }
        else {
            //ToDo args options
            options = new Options();
            options.setCohesionRate(5);
            options.setWriteLogFile(true);
            options.setWriteSwarmFile(true);
            options.setPerceptionDistance(25);
            options.setAggressionSetting(AggressionSetting.UNIFORM);
            options.setAggression(1d);
            options.setPheromoneStrength(90);
            options.setSwarmSize(1000);
        }
        sim = new Simulation(options);
        sim.runForTicks(500);

        if(options.isWriteSwarmFile()) {
            logger = sim.getLogger();
            try {
                logger.WriteSwarmFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        if(options.isWriteLogFile()){
            logger = sim.getLogger();
            try{
                logger.WriteLogFIle(sim);
            } catch (IOException e){
                System.out.println(e);
            }
        }

    }

    public static Options ParseArgs(String[] args) {
        Options options = new Options();
        String[] argCurrent;
        for (String arg : args) {
            if (arg.contains("swarmsize")) {
                argCurrent = arg.split(":");
                options.setSwarmSize(Integer.parseInt(argCurrent[1]));
            } else if (arg.contains("aggression")) {
                argCurrent = arg.split(":");
                options.setAggression(Integer.parseInt(argCurrent[1]));
            } else if (arg.contains("writeswarmfile")){
                argCurrent = arg.split(":");
                options.setWriteSwarmFile(argCurrent[1].equals("true"));
            }
        }
        return options;
    }
}
