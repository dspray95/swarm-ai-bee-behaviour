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
import sim.agent.state.hornet.Attacked;
import sim.config.Defaults;
import sim.config.Options;

import java.util.Random;

/**
 * The agent class is an abstract base class for all implementations of
 * Agents in the simulation (Bee and Hornet are child classes)
 * Universal functions applicable to all agents are available here including:
 * <ul>
 *     <li>Damage, the processing of the agent taking HP damage</li>
 *     <li>Tick, the process all agents perfom when 'tick' is called by the simulation</li>
 *     <li>AttackRoll</li>
 *     <li>Place Pheromone</li>
 *     <li>Detect Pheromone</li>
 *     <li>Management of alert level</li>
 * </ul>
 */
public abstract class Agent implements TickListener {


    protected Simulation parent;
    protected Coordinate location;
    protected Options options;
    protected State state;
    protected Perceptor perceptor;
    protected Actuator actuator;
    protected int heatThreshold;
    protected int attackPoints;
    protected int beesKilled;
    protected double hp;
    protected double temperature;
    protected double aggression;
    protected double alertLevel;
    protected boolean pheromoneSet;

    /**
     * Clas constructor
     * Assigns the parent simulation and a location.
     * Additionally, acquires options from the parent for use in defining the cohesion rate of
     * the swarm. Creates a perceptor and actuator object for agent use in interacting with the world
     * space. Uses the default base temperature option from the Default static class
     * @param parent The simulation object to which the agent belongs
     * @param location A location in the world space at whic the agent currently exists
     */
    public Agent(Simulation parent, Coordinate location){
        this.parent = parent;
        this.location = location;
        this.options = parent.getOptions();
        this.perceptor = new Perceptor(this);
        this.actuator = new Actuator(this);
        this.beesKilled = 0;
        this.temperature = Defaults.BASE_TEMPERATURE;
    }

    /**
     * Performs damage claculations on the agent. This function can be called from anywhere,
     * but usually it is called by another agent attacking this agent.
     * Takes a damage value and sets the agents state accordingly;
     * <ul>
     *     <li>If the agent is a hornet and it has been damaged, the agents state is set to attacked</li>
     *     <li>If the agents health <= 0 th agent is 'Dead'</li>
     * </ul>
     * @param value The amount of damage being dealt to the agent
     */
    public void Damage(double value){
        if(hp > -1) {
            this.hp -= value;
            if(this.getClass() == Hornet.class){
                this.state = new Attacked(this);
            }
            if (hp <= 0) {
                if(this.getClass() == Bee.class) {
                    System.out.println("Bee killed");
                }
                else if(this.getClass() == Hornet.class){
                    System.out.println("Hornet killed");
                }
                this.state = new Dead(this);
            }
        }
    }

    /**
     * Overrides the Tick function from the inherited TickListener class
     * Tries to find a valid next move using percepts from the agent's perceptor.
     * If there is a valid target we move to the target.
     */
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

    /**
     * Add stochasticity to the agents attack strength based on the
     * attackPoints variable of the agent.
     * @return int, a random value based on the attack points of the agent
     */
    public int AttackRoll(){
        return new Random().nextInt(attackPoints) + attackPoints;
    }

    /**
     * Creates and places a pheromone object in the worldspace
     * @param strength The strength of the pheromone, usually set by the Simulation.options object
     */
    public void PlacePheromone(int strength){
        Pheromone pheromone = new Pheromone(parent, this.location, strength, options.getPerceptionDistance()*1.25);
        parent.AddPheremone(pheromone);
        pheromoneSet = true;
    }

    /**
     * Triggers when the perceptor notices a pheromone.
     * Adjusts the agents alert level according to the strength of the pheromone perceived.
     * If the alertLevel is adgested to be over 100 and the state of the agent is Working then
     * we move the agent into a Guarding state
     * @param pheromone The percieved pheromone object
     */
    public void PheromonePerceived(Pheromone pheromone){
        IncreaseAlertLevel(pheromone.getStrength());
        if(alertLevel > 100 && state.getClass() == Working.class){
            Guarding guarding = new Guarding(this);
            guarding.setTriggerPheromone(pheromone);
            this.state = guarding;
        }
    }

    /**
     * Increases the alert level by the specified value
     * @param alertLevel Amount to increase the alert level
     */
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

    public double getHP(){
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
