package sim.config;

public class Options {

    int swarmSize;
    int deploymentArea;
    int environmentSize;
    int perceptionDistance;
    int cohesionRate;
    boolean writeSwarmFile;
    boolean writeLogFile;

    public Options(){
        this.cohesionRate = Defaults.COHESION_RATE;
        this.swarmSize = Defaults.SWARM_SIZE;
        this.environmentSize = Defaults.ENVIRONMENT_SIZE;
        this.perceptionDistance = Defaults.PERCEPTION_DISTANCE;
        this.deploymentArea = Defaults.DEPLOYMENT_AREA;
        this.writeSwarmFile = Defaults.WRITE_SWARM_FILE;
        this.writeLogFile = Defaults.WRITE_LOG_FILE;
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
}
