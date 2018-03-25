package sim.agent;

import sim.Coordinate;
import sim.agent.listener.TickListener;

public class Pheremone implements TickListener{

    Coordinate location;
    int strength;

    public Pheremone(Coordinate location){
        this.location = location;
    }

    @Override
    public void Tick() {

    }
}
