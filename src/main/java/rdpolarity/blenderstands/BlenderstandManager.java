package rdpolarity.blenderstands;

import java.util.ArrayList;

/**
 * This class keeps track of all active armourstand entities
 */
public class BlenderstandManager {
    ArrayList<Blenderstand> blenderstands = new ArrayList<>();

    public void Add(Blenderstand blenderstand) {
        blenderstands.add(blenderstand);
    }

    public void Clear() {
        blenderstands.forEach(Blenderstand::Kill);
    }
}
