package sim.agent;

import sim.Coordinate;
import sim.Simulation;
import sim.agent.listener.TickListener;
import sim.agent.module.Actuator;
import sim.agent.module.Perceptor;
import sim.agent.state.Dead;
import sim.agent.state.State;
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
    protected double alertLevel;
    protected boolean pheromoneSet;

    public Agent(Simulation parent, Coordinate location){
        this.parent = parent;
        this.location = location;
        this.options = parent.getOptions();
        this.perceptor = new Perceptor(this);
        this.actuator = new Actuator(this);
    }

    public void setPheremone(){
        Pheromone pheromone = new Pheromone(this.location, 100, options.getPerceptionDistance()/1.5);
        parent.AddPheremone(pheromone);
        pheromoneSet = true;
    }

    public boolean isPheromoneSet() {
        return pheromoneSet;
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

    public void Damage(int value){
        this.hp -= value;
        System.out.println("Attacked for " + value + " damage.");
        if(hp <= 0){
            System.out.println("Bee killed");
            this.state = new Dead(this);
        }

    }

    public int AttackRoll(){
        return new Random().nextInt(attackPoints) + attackPoints;
    }


    public double getAlertLevel() {
        return alertLevel;
    }

    public void increaseAlertLevel(double alertLevel) {
        this.alertLevel += alertLevel;
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
}
