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
        this.strength -= 0.1d; //pheromone strength decay over time
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
