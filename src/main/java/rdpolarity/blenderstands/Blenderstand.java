package rdpolarity.blenderstands;

import com.google.gson.Gson;
import com.google.inject.assistedinject.Assisted;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import rdpolarity.blenderstands.data.ArmourstandObject;

import javax.inject.Inject;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Blenderstand {

    public ArmourstandObject[] armourstands; // TODO: Encapsulate
    public ArrayList<ArmourstandEntity> armourstandEntities = new ArrayList<>(); // TODO: Encapsulate
    private int currentFrame = 0;
    private boolean destroyable = true;

    public boolean isDestroyable() {
        return destroyable;
    }
    public void setDestroyable(boolean destroyable) {
        this.destroyable = destroyable;
    }

    /**
     * Initialises Blenderstand Object
     */
    @Inject
    private Blenderstand(@Assisted String name, BlenderstandManager blenderstandManager) {
        armourstands = GetObjects(name);
        blenderstandManager.Add(this);
    }

    public void FromFile(String name) {
    }

    /**
     * Converts JSON data to [ArmourstandObject] array
     *
     * @param name file name
     * @return if successful a list of armourstand objects else null
     */
    private ArmourstandObject[] GetObjects(String name) {
        String path = Blenderstands.getPlugin(Blenderstands.class).getDataFolder() + File.separator + "/armourstands/" + name + ".json";
        File file = new File(path);
        try {
            Gson g = new Gson();
            ArmourstandObject[] ao = g.fromJson(new FileReader(path), ArmourstandObject[].class);
            return ao;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Spawns all entities tied to blenderstand at location
     *
     * @param loc spawn location
     */
    public void Spawn(Location loc) {
        for (ArmourstandObject armourstand : armourstands) {
            ArmourstandEntity newEntity = new ArmourstandEntity(armourstand, loc);
            armourstandEntities.add(newEntity);
        }
    }

    /**
     * Spawns armourstand at a specific frame
     *
     * @param frame keyframe number
     */
    public void SetFrame(int frame) {
        for (ArmourstandEntity armourstandEntity : armourstandEntities) {
            armourstandEntity.SetFrame(frame);
        }
    }

    @Inject private BlenderstandManager blenderstandManager;

    /**
     * This is triggered when an armourstand is hit by the player
     */
    public void Hit() {
        if (destroyable) {
            Kill();
        }
    }

    /**
     * Starts the blenderstand animation
     */
    public void RunAnimation() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Blenderstands.getPlugin(Blenderstands.class), () -> {
            if (currentFrame >= armourstands[0].keyframes.length) {
                currentFrame = 0;
            }
            this.SetFrame(currentFrame);
            currentFrame++;
        }, 10, 0);
    }

    /**
     * Destroys all entities tied to blenderstand
     */
    public void Kill() {
        armourstandEntities.forEach(e -> e.entity.remove());
    }
}
