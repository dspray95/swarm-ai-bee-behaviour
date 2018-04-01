package sim.agent;

import sim.Coordinate;
import sim.Simulation;
import sim.agent.listener.TickListener;

public class Pheromone implements TickListener{

    Coordinate location;
    int strength;
    double radius;
    Simulation parent;
    boolean removed = false;

    public Pheromone(Simulation parent, Coordinate location, int strength, double radius){
        this.location = location;
        this.strength = strength;
        this.radius = radius;
        this.parent = parent;
    }

    @Override
    public void Tick() {
        if(!removed) {
            this.strength -= 0.1d; //pheromone strength decay over time
            if (this.strength <= 0) {
                System.out.println("removing pheremone...");
                parent.RemovePheromone(this);
                removed = true;
            }
        }
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
