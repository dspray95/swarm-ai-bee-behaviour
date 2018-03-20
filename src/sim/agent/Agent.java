package sim.agent;

import sim.Coordinate;
import sim.Simulation;
import sim.agent.listener.TickListener;
import sim.agent.module.Actuator;
import sim.agent.module.Perceptor;
import sim.agent.state.State;
import sim.config.Options;

import java.util.Random;

public abstract class Agent implements TickListener {


    Simulation parent;
    Coordinate location;
    Options options;
    State state;
    Perceptor perceptor;
    Actuator actuator;
    int hp;
    int heatThreshold;
    int attackPoints;

    public Agent(Simulation parent, Coordinate location){
        this.parent = parent;
        this.location = location;
        this.options = parent.getOptions();
        this.perceptor = new Perceptor(this);
        this.actuator = new Actuator(this);
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

    public void Damage(int value){
        this.hp -= value;
        if(hp <= 0){
            //die...
        }
    }

    public int AttackRoll(){
        return new Random().nextInt(attackPoints) + attackPoints;

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
