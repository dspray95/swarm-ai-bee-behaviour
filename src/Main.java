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
import visualiser.Visualiser;

import javax.naming.TimeLimitExceededException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main extends Application {

    private static Options options;
    private static Simulation sim;
    private static Logger logger;

    public static void main(String[] args){
        options = new Options();
            options.setEnvironmentSize(750);
            options.setSwarmSize(500);
            options.setCohesionRate(5);
            options.setPerceptionDistance(250);
            options.setDeploymentArea(500);
        //todo config options from args
        sim = new Simulation(options);
        sim.runForTicks(500);
        logger = sim.getLogger();
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        ArrayList<ArrayList<Coordinate>> positions = logger.getPositionList();
        ArrayList<ArrayList<Boolean>> deadList = logger.getAliveList();
        positions.add(logger.getHornetPositions());
        ArrayList<Boolean> hornetPositions = new ArrayList<>();
        hornetPositions.add(false);
        deadList.add(hornetPositions);
        primaryStage.setTitle("Swarm AI Mobbing - Visualisation");
        Group squares = new Group();

        for(ArrayList<Coordinate> position : positions){
            Rectangle r = new Rectangle();
            r.setX(position.get(0).X());
            r.setY(position.get(0).Y());
            r.setWidth(1);
            r.setHeight(1);
            if(positions.indexOf(position) == positions.size() - 1){
                r.setFill(Color.GOLD);
                r.setWidth(2);
                r.setHeight(2);
            }
            else{
                r.setFill(Color.WHITE);
            }
            squares.getChildren().add(r);
        }

        Group root = new Group();
        root.getChildren().add(squares);
        Scene scene = new Scene(root, options.getEnvironmentSize(), options.getEnvironmentSize(), Color.BLACK);
        primaryStage.setScene(scene);
        primaryStage.show();
        ArrayList<PathTransition> anim = pathAnimation(squares, positions, deadList);
        for(PathTransition pt : anim){
            pt.play();
        }
    }

    public ArrayList<PathTransition> pathAnimation(Group group, ArrayList<ArrayList<Coordinate>> positions,
                                                   ArrayList<ArrayList<Boolean>> aliveList){

        ArrayList<PathTransition> ptList = new ArrayList<>();

        for(Node rect : group.getChildren()){
            Path path = new Path();
            int index = group.getChildren().indexOf(rect);
            Coordinate init = positions.get(index).get(0);
            ArrayList<Boolean> dead = aliveList.get(index);
            path.getElements().add(new MoveTo(init.X(), init.Y()));

            for(Coordinate coord : positions.get(index)){
                if(dead.get(index)) {
                    path.getElements().add(new MoveTo(-10, -10));
                }
                else{
                    path.getElements().add(new LineTo(coord.X(), coord.Y()));
                }
            }

            PathTransition pathTransition = new PathTransition();
            pathTransition.setPath(path);
            pathTransition.setNode(rect);
            pathTransition.setDuration(Duration.millis(5000));
            pathTransition.setCycleCount(Timeline.INDEFINITE);
            pathTransition.setInterpolator(Interpolator.LINEAR);
            pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
            ptList.add(pathTransition);
        }
        return ptList;
    }
}
