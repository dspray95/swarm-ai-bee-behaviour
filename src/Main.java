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
import sim.config.Options;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static Options options;
    private static Simulation sim;
    private static Logger logger;

    public static void main(String[] args){
        //ToDo args options
        options = new Options();
            options.setEnvironmentSize(750);
            options.setSwarmSize(500);
            options.setCohesionRate(5);
            options.setPerceptionDistance(250);
            options.setDeploymentArea(500);
            options.setWriteSwarmFile(true);
        sim = new Simulation(options);
        sim.runForTicks(500);
        if(options.isWriteLogFile()) {
            logger = sim.getLogger();
            try {
                logger.WriteFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
