import sim.Logger;
import sim.Simulation;
import sim.config.AggressionSetting;
import sim.config.Defaults;
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
            //Sets to defaults if there are no args
            options = new Options();
        }

        sim = new Simulation(options);
        sim.runForTicks(options.getNumTicks());

        if(options.isWriteSwarmFile()) {
            logger = sim.getLogger();
            try {
                logger.WriteSwarmFile(sim);
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

            if(arg.equalsIgnoreCase("help")) {
                System.out.print(Defaults.HELP_TEXT);
                System.exit(0);
            }
            else if(arg.contains(":")){  //Check if the argument has a value
                argCurrent = arg.split(":");
                if (argCurrent[0].equalsIgnoreCase("swarm_size")) {
                    options.setSwarmSize(Integer.parseInt(argCurrent[1]));
                }
                else if (argCurrent[0].equalsIgnoreCase("aggression")) {
                    options.setAggression(Integer.parseInt(argCurrent[1]));
                }
                else if (argCurrent[0].equalsIgnoreCase("tick_count")){
                    options.setNumTicks(Integer.parseInt(argCurrent[1]));
                }
                else if (argCurrent[0].equalsIgnoreCase("filepath")){
                    options.setFilepath(argCurrent[1]);
                }
                else if(argCurrent[0].equalsIgnoreCase("aggression_setting")){
                    switch(argCurrent[1]){
                        case("UNIFORM"):
                            options.setAggressionSetting(AggressionSetting.UNIFORM);
                            break;
                        case("RANDOM_SPREAD"):
                            options.setAggressionSetting(AggressionSetting.RANDOM_SPREAD);
                            break;
                        case("CLOSE_TO_VALUE"):
                            options.setAggressionSetting(AggressionSetting.CLOSE_TO_VALUE);
                            break;
                    }
                }
            }
            else if (arg.equalsIgnoreCase("write_swarm_file")){
                options.setWriteSwarmFile(true);
            }
            else if(arg.equalsIgnoreCase("write_log_file")){
                options.setWriteLogFile(true);
            }
        }
        return options;
    }
}
