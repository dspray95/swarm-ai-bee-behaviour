package sim;

import sim.agent.Agent;
import sim.agent.Bee;
import sim.agent.Hornet;
import sim.agent.state.bee.Guarding;
import sim.agent.state.bee.Mobbing;
import sim.agent.state.bee.Working;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Logger {

    ArrayList<ArrayList<Coordinate>> positionList;
    ArrayList<Coordinate> hornetPositions;
    ArrayList<ArrayList<Boolean>> aliveList;
    ArrayList<Boolean> hornetAlive;
    ArrayList<Double> mobTemperature;
    boolean initialised;

    public Logger(){
        initialised = false;
        positionList = new ArrayList<>();
        aliveList = new ArrayList<>();
        hornetAlive = new ArrayList<>();
        mobTemperature = new ArrayList<>();
    }

    public void Log(Simulation sim){
        ArrayList<Bee> swarm = sim.getSwarm();
        Hornet hornet = sim.getHornet();
        if(initialised){
            for(Agent agent : swarm){
                int index = swarm.indexOf(agent);
                positionList.get(index).add(agent.getLocation());
                aliveList.get(index).add(agent.getHP() > 0);
            }
            hornetPositions.add(hornet.getLocation());
            hornetAlive.add(hornet.getHP() > 0);
            mobTemperature.add(sim.getMob().getTemperature());
        }
        else{
            for(Agent agent : swarm){
                ArrayList<Coordinate> agentPositions = new ArrayList<>();
                ArrayList<Boolean> agentAlive = new ArrayList<>();
                aliveList.add(agentAlive);
                positionList.add(agentPositions);
            }
            hornetPositions = new ArrayList<>();
            hornetAlive = new ArrayList<>();
            mobTemperature.add(sim.getMob().getTemperature());
            initialised = true;
        }
    }

    public void WriteLogFIle(Simulation sim) throws IOException {
        System.out.println("WRITING LOG FILE...");
        //gather up infomration
        int numWorking = 0;
        int numGuarding = 0;
        int numMobbing = 0;
        int activeMobSize = 0;    //Differs from numMobbing in that this value is the number of agents that are contributing to the heat generation in the mob
        int numPerceivingHornet = 0;
        int numDead = 0;
        int swarmProductivity;
        double mobTemperature = 0d;

        for(Agent agent : sim.getSwarm()){
            Class stateClass = agent.getState().getClass();
            if(stateClass == Working.class){
                numWorking++;
            }
            else if(stateClass == Guarding.class){
                numGuarding++;
            }
            else if(stateClass == Mobbing.class){
                numMobbing++;
            }

            if(agent.getHP() <= 0){
                numDead++;
            }
            if(agent.getPerceptor().getThreat() != null){
                numPerceivingHornet++;
            }
        }
        activeMobSize = sim.getMob().getMobSize();
        mobTemperature = sim.getMob().getTemperature();
        swarmProductivity = sim.getProductivity();
        //create file and write
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        String filepath = System.getProperty("user.home") + File.separator + "swarm-ai-mobbing" + File.separator + "log";
        File file = new File(filepath);
        file.mkdirs();
        filepath = filepath + File.separator + dateFormat.format(date) + ".LOG";
        file = new File(filepath);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        String lineBreak = System.getProperty("line.separator");
        bw.write("workers:" + numWorking + lineBreak);
        bw.write("guards:" + numGuarding + lineBreak);
        bw.write("mobs:" + numMobbing + lineBreak);
        bw.write("dead:"+ numDead + lineBreak);
        bw.write("perceived_threat:" + numPerceivingHornet + lineBreak);
        bw.write("active_mob_size:" + activeMobSize + lineBreak);
        bw.write("mob_temperature:" + mobTemperature + lineBreak);
        bw.write("productivity:" + swarmProductivity + lineBreak);
        bw.close();
        System.out.println("FINISHED WRITING LOG FILE");
    }

    public void WriteSwarmFile() throws IOException{
        System.out.println("WRITING SWARM FILE...");
        ArrayList<String> coordinatesToString = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        String filepath = System.getProperty("user.home") + File.separator + "swarm-ai-mobbing" + File.separator + "swarmlogs";
        File file = new File(filepath);
        file.mkdirs();
        filepath = filepath + File.separator + dateFormat.format(date) + ".SWARM";
        file = new File(filepath);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        int lineNumber = 0;

        for(ArrayList<Coordinate> positions : positionList){
            String line = "apid";
            int index = positionList.indexOf(positions);
            ArrayList<Boolean> alive = aliveList.get(index);
            for(Coordinate coord : positions){
                try{
                    line = line + ":" + coord.X() + "," + coord.Y() + "," + (alive.get(positions.indexOf(coord)) ? 1 : 0);
                }
                catch(IndexOutOfBoundsException e){
                    System.out.println(e);
                }
            }
            bw.write(line);
            bw.newLine();
            lineNumber++;
        }

        String hornetLine = "vespid";
        for(Coordinate coord : hornetPositions){
            hornetLine = hornetLine + ":" + coord.X() + "," + coord.Y() + "," + (hornetAlive.get(hornetPositions.indexOf(coord)) ? 1 : 0);
        }
        bw.write(hornetLine);
        bw.newLine();
        String temperatureLine = "temp";
        for(Double temp : mobTemperature){
            DecimalFormat df = new DecimalFormat("###.##");
            temperatureLine = temperatureLine + ":" + df.format(temp);
        }
        bw.write(temperatureLine);
        bw.close();
        System.out.println("FINISHED WRITING SWARM FILE");
    }

    public ArrayList<ArrayList<Coordinate>> getPositionList() {
        return positionList;
    }

    public ArrayList<ArrayList<Boolean>> getAliveList(){
        return this.aliveList;
    }

    public ArrayList<Coordinate> getHornetPositions(){
        return this.hornetPositions;
    }
}
