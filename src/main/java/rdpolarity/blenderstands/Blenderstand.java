package rdpolarity.blenderstands;

import co.aikar.commands.annotation.Dependency;
import com.google.gson.Gson;
import com.google.inject.Injector;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.omg.CORBA.Object;
import rdpolarity.blenderstands.data.ArmourstandObject;

import javax.inject.Inject;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Blenderstand {

    @Inject Injector injector;
    @Inject BlenderstandManager blenderstandManager;

    public ArmourstandObject[] armourstands; // TODO: Encapsulate
    public ArrayList<ArmourstandEntity> armourstandEntities = new ArrayList<>(); // TODO: Encapsulate
    private int currentFrame = 0;
    private boolean destroyable = false;

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
    public Blenderstand() {
        blenderstandManager.Add(this);
    }

    public void FromFile(String name) {
        armourstands = GetObjects(name);
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
