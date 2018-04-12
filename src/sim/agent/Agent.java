package sim.agent;

import sim.Coordinate;
import sim.Simulation;
import sim.agent.listener.TickListener;
import sim.agent.module.Actuator;
import sim.agent.module.Perceptor;
import sim.agent.state.Dead;
import sim.agent.state.State;
import sim.agent.state.bee.Guarding;
import sim.agent.state.bee.Working;
import sim.config.Defaults;
import sim.config.Options;

import java.util.Random;

public abstract class Agent implements TickListener {


    protected Simulation parent;
    protected Coordinate location;
    protected Options options;
    protected State state;
    protected Perceptor perceptor;
    protected Actuator actuator;
    protected int hp;
    protected int heatThreshold;
    protected int attackPoints;
    protected int beesKilled;
    protected double temperature;
    protected double aggression;
    protected double alertLevel;
    protected boolean pheromoneSet;

    public Agent(Simulation parent, Coordinate location){
        this.parent = parent;
        this.location = location;
        this.options = parent.getOptions();
        this.perceptor = new Perceptor(this);
        this.actuator = new Actuator(this);
        this.beesKilled = 0;
        this.temperature = Defaults.BASE_TEMPERATURE;
    }

    public void Damage(int value){
        this.hp -= value;
        System.out.println("Attacked for " + value + " damage.");
        if(hp <= 0){
            System.out.println("Bee killed");
            this.state = new Dead(this);
        }

    }

    @Override
    public void Tick() {
        boolean gettingNextMove = true;
        perceptor.PerceptorTick();
        while(gettingNextMove){
            Coordinate target = state.GetTarget();
            if(actuator.Move(target)){
                gettingNextMove = false;
            }
        }
    }

    public int AttackRoll(){
        return new Random().nextInt(attackPoints) + attackPoints;
    }


    public void PlacePheromone(int strength){
        Pheromone pheromone = new Pheromone(parent, this.location, strength, options.getPerceptionDistance()*1.25);
        parent.AddPheremone(pheromone);
        pheromoneSet = true;
    }

    public void PheromonePerceived(Pheromone pheromone){
        IncreaseAlertLevel(pheromone.getStrength());
        if(alertLevel > 100 && state.getClass() == Working.class){
            Guarding guarding = new Guarding(this);
            guarding.setTriggerPheromone(pheromone);
            this.state = guarding;
        }
    }

    public void IncreaseAlertLevel(double alertLevel) {
        this.alertLevel += alertLevel;
    }

    public boolean isPheromoneSet() {
        return pheromoneSet;
    }

    public double getAlertLevel() {
        return alertLevel;
    }


    public int getBeesKilled() {
        return beesKilled;
    }

    public void increaseBeesKilled() {
        this.beesKilled++;
    }

    public double getAggression() {
        return aggression;
    }

    public Simulation getParent() {
        return parent;
    }

    public void setParent(Simulation parent) {
        this.parent = parent;
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = location;
    }

    public Options getOptions(){
        return this.options;
    }

    public Perceptor getPerceptor(){
        return this.perceptor;
    }

    public int getHP(){
        return this.hp;
    }
    public void setState(State state){
        this.state = state;
    }

    public State getState(){
        return this.state;
    }

    public double getTemperature(){
        return this.temperature;
    }

    public void setTemperature(double temperature){
        this.temperature = temperature;
    }

}
