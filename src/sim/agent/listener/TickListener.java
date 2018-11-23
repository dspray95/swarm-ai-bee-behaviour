package sim.agent.listener;

/**
 * TickListener is an interface used for every object that must perform some function at every instance (or 'tick')
 * of time.
 */
public interface TickListener {
    /**
     * Implementations of TickListener should override this function and perform any necessary operations inside it.
     * This function is called for every implementation of TickListener registered by a Simulation class.
     */
    public void Tick();

}
