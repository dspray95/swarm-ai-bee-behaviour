import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import sim.Coordinate;
import sim.Logger;
import sim.Simulation;
import sim.config.AggressionSetting;
import sim.config.Options;

import java.io.IOException;
import java.util.ArrayList;

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
            options.setAggressionSetting(AggressionSetting.RANDOM_SPREAD);
            options.setAggression(0.5d);
            options.setPheromoneStrength(500);
        }
        sim = new Simulation(options);
        sim.runForTicks(1000);
        if(options.isWriteLogFile()) {
            logger = sim.getLogger();
            try {
                logger.WriteFile();
            } catch (IOException e) {
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
