import sim.Logger;
import sim.Simulation;
import sim.config.AggressionSetting;
import sim.config.Defaults;
import sim.config.Options;
import sun.misc.FloatingDecimal;

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
                    System.out.println("swarm size: " + argCurrent[1]);
                }
                else if (argCurrent[0].equalsIgnoreCase("aggression")) {
                    options.setAggression(Double.parseDouble(argCurrent[1]));
                    System.out.println("aggression: " + argCurrent[1]);
                }
                else if (argCurrent[0].equalsIgnoreCase("tick_count")){
                    options.setNumTicks(Integer.parseInt(argCurrent[1]));
                    System.out.println("tick count: " + argCurrent[1]);
                }
                else if (argCurrent[0].equalsIgnoreCase("filepath")){
                    String path = argCurrent[1] + ":" + argCurrent[2]; //argCurrent will be split into 3 due to the : after drive letters
                    path = path.replace("\\", "\\\\"); //fixing escape characters with directory separators
                    options.setFilepath(path);
                    System.out.println("file path: " + path);
                }
                else if(argCurrent[0].equalsIgnoreCase("aggression_setting")){
                    switch(argCurrent[1]){
                        case("UNIFORM"):
                            options.setAggressionSetting(AggressionSetting.UNIFORM);
                            System.out.println("aggression setting: UNIFORM");
                            break;
                        case("RANDOM_SPREAD"):
                            options.setAggressionSetting(AggressionSetting.RANDOM_SPREAD);
                            System.out.println("aggression setting: RANDOM_SPREAD");
                            break;
                        case("CLOSE_TO_VALUE"):
                            options.setAggressionSetting(AggressionSetting.CLOSE_TO_VALUE);
                            System.out.println("aggression setting: CLOSE_TO_VALUE");
                            break;
                    }
                }
            }
            else if (arg.equalsIgnoreCase("write_swarm_file")){
                options.setWriteSwarmFile(true);
                System.out.println("Writing swarm file");
            }
            else if(arg.equalsIgnoreCase("write_log_file")){
                options.setWriteLogFile(true);
                System.out.println("Writing log file");
            }
        }
        return options;
    }
}
