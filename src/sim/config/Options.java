package sim.config;

public class Options {

    int swarmSize;
    int deploymentArea;
    int environmentSize;
    int perceptionDistance;
    int cohesionRate;
    AggressionSetting aggressionSetting;
    double aggression;
    boolean writeSwarmFile;
    boolean writeLogFile;
    int pheromoneStrength;
    int numTicks;

    public Options(){
        this.cohesionRate = Defaults.COHESION_RATE;
        this.swarmSize = Defaults.SWARM_SIZE;
        this.environmentSize = Defaults.ENVIRONMENT_SIZE;
        this.perceptionDistance = Defaults.PERCEPTION_DISTANCE;
        this.deploymentArea = Defaults.DEPLOYMENT_AREA;
        this.writeSwarmFile = Defaults.WRITE_SWARM_FILE;
        this.writeLogFile = Defaults.WRITE_LOG_FILE;
        this.aggressionSetting = Defaults.AGGRESSION_SETTING;
        this.aggression = Defaults.AGGRESSION_VALUE;
        this.pheromoneStrength = Defaults.PHEROMONE_STRENGTH;
        this.numTicks = Defaults.NUM_TICKS;
    }


    public int getPheromoneStrength() {
        return pheromoneStrength;
    }

    public void setPheromoneStrength(int pheromoneStrength) {
        this.pheromoneStrength = pheromoneStrength;
    }

    public int getSwarmSize() {
        return swarmSize;
    }

    public void setSwarmSize(int swarmSize) {
        this.swarmSize = swarmSize;
    }

    public int getEnvironmentSize() {
        return environmentSize;
    }

    public void setEnvironmentSize(int environmentSize) {
        this.environmentSize = environmentSize;
    }

    public int getPerceptionDistance() {
        return perceptionDistance;
    }

    public void setPerceptionDistance(int perceptionDistance) {
        this.perceptionDistance = perceptionDistance;
    }

    public int getDeploymentArea() {
        return deploymentArea;
    }

    public void setDeploymentArea(int deploymentArea) {
        this.deploymentArea = deploymentArea;
    }

    public int getCohesionRate() {
        return cohesionRate;
    }

    public void setCohesionRate(int cohesionRate) {
        this.cohesionRate = cohesionRate;
    }

    public boolean isWriteSwarmFile() {
        return writeSwarmFile;
    }

    public void setWriteSwarmFile(boolean writeSwarmFile) {
        this.writeSwarmFile = writeSwarmFile;
    }

    public boolean isWriteLogFile() {
        return writeLogFile;
    }

    public void setWriteLogFile(boolean writeLogFile) {
        this.writeLogFile = writeLogFile;
    }

    public double getAggression(){
        return this.aggression;
    }

    public void setAggression(double aggression){
        this.aggression = aggression;
    }

    public AggressionSetting getAggressionSetting() {
        return aggressionSetting;
    }

    public void setAggressionSetting(AggressionSetting aggressionSetting) {
        this.aggressionSetting = aggressionSetting;
    }

    public int getNumTicks(){
        return this.numTicks;
    }

    public void setNumTicks(int numTicks){
        this.numTicks = numTicks;
    }

}
