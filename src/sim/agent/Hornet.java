package sim.agent;

import sim.Coordinate;
import sim.Simulation;
import sim.agent.state.bee.Working;
import sim.agent.state.hornet.Hunting;

public class Hornet extends Agent {

    public Hornet(Simulation parent, Coordinate location) {
        super(parent, location);
//        this.state = new Working(this, options.getCohesionRate());
        this.state = new Hunting(this);
        this.hp = 25;
        this.heatThreshold = 47;
        this.attackPoints = 3;
    }

    public boolean IsEscaped()
    {
        /*if hornet position is:
           x || y < 20
           x || y > environment size - 20
           true
         */
        int edgeBuffer = 20;
        int envSize = parent.getOptions().getEnvironmentSize();
        if(hp > 0) {
            if (location.X() < edgeBuffer || location.Y() < edgeBuffer
                    || location.X() > envSize - edgeBuffer || location.Y() > envSize - edgeBuffer) {
                return true;
            } else return false;

        } else return false;
    }
}
