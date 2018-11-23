package sim;

import sim.agent.Agent;
import sim.agent.Bee;
import sim.agent.Hornet;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
        //gather up information
        int numDead = 0;
        int swarmProductivity = sim.getProductivity();;
        double aggression = sim.getOptions().getAggression();
        long time = TimeUnit.NANOSECONDS.toMillis(sim.getTimeToKill());
        String aggressionSetting = "";
        switch(sim.getOptions().getAggressionSetting()){
            case UNIFORM:
                aggressionSetting = "UNIFORM";
                break;
            case CLOSE_TO_VALUE:
                aggressionSetting = "CLOSE_TO_VALUE";
                break;
            case RANDOM_SPREAD:
                aggressionSetting = "RANDOM_SPREAD";
                break;
            case FEW_HIGH:
                aggressionSetting = "FEW_HIGH";
                break;
            case FEW_HIGH_LOW:
                aggressionSetting = "FEW_HIGH_LOW";
                break;
            case MANY_LOW:
                aggressionSetting = "MANY_LOW";
                break;
        }

        for(Agent agent : sim.getSwarm()){
            if(agent.getHP() <= 0){
                numDead++;
            }
        }
        //create file and write
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        String filepath = sim.getOptions().getFilepath() + File.separator + "log";
        File file = new File(filepath);
        file.mkdirs();
        filepath = filepath + File.separator + dateFormat.format(date) + ".LOG";
        file = new File(filepath);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        String lineBreak = System.getProperty("line.separator");
        bw.write("aggression:" + aggression + lineBreak);
        bw.write("distribution:" + aggressionSetting + lineBreak);
        bw.write("dead:"+ numDead + lineBreak);
        bw.write("time:" + time + lineBreak);
        bw.write("productivity:" + swarmProductivity + lineBreak);
        bw.write("hornet:" + (sim.getHornet().getHP() > 0 ? "ALIVE":"DEAD"));
        bw.close();
        System.out.println("FINISHED WRITING LOG FILE");
    }

    public void WriteSwarmFile(Simulation sim) throws IOException{
        System.out.println("WRITING SWARM FILE...");
        ArrayList<String> coordinatesToString = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        String filepath = sim.getOptions().getFilepath() + File.separator + "swarmlogs";
        File file = new File(filepath);
        file.mkdirs();
        filepath = filepath + File.separator + dateFormat.format(date) + ".SWARM";
        file = new File(filepath);
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
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
