package sim.agent;

import sim.Coordinate;
import sim.agent.listener.TickListener;

public class Pheromone implements TickListener{

    Coordinate location;
    int strength;
    double radius;

    public Pheromone(Coordinate location, int strength, double radius){
        this.location = location;
        this.strength = strength;
        this.radius = radius;
    }

    @Override
    public void Tick() {

    }

    public Coordinate getLocation(){
        return this.location;
    }

    public double getRadius(){
        return this.radius;
    }

    public int getStrength(){
        return this.strength;
    }
}
