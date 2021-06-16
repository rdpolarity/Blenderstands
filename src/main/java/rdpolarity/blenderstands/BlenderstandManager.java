package rdpolarity.blenderstands;

import rdpolarity.blenderstands.patterns.Singleton;

import java.util.ArrayList;

/**
 * This class keeps track of all active armourstand entities
 */
public final class BlenderstandManager extends Singleton {
    private static BlenderstandManager instance;
    private BlenderstandManager() {}

    private final ArrayList<Blenderstand> blenderstands = new ArrayList<>();

    public void Add(Blenderstand blenderstand) {
        blenderstands.add(blenderstand);
    }

    public void Remove(Blenderstand blenderstand) {
        blenderstands.remove(blenderstand);
    }

    public void Clear() {
        blenderstands.forEach(Blenderstand::Kill);
        blenderstands.clear();
    }

    // TODO implement save feature
    public void Save() {

    }

    public static synchronized BlenderstandManager GetInstance() {
        if (instance == null) {
            instance = new BlenderstandManager();
        }
        return instance;
    }
}
