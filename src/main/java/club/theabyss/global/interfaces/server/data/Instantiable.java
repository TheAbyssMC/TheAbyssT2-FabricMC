package club.theabyss.global.interfaces.server.data;

/**
 * A class that adds utility method to all managers classes.
 */
public abstract class Instantiable<T> {
    private final T instance;

    /**
     * Default constructor for bukkit instantiation.
     * 
     * @param instance The plugin instance.
     */
    public Instantiable(T instance) {
        this.instance = instance;
    }

    /**
     * A getter that returns the plugin instance.
     * 
     * @return The plugin instance.
     */
    protected T instance() {
        return instance;
    }

}
