package rdpolarity.blenderstands;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import rdpolarity.blenderstands.data.ArmourstandObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Blenderstand implements Listener {

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
     *
     * @param data file name of JSON
     */
    public Blenderstand(String data) {
        armourstands = GetObjects(data);
        Blenderstands manager = Blenderstands.getPlugin(Blenderstands.class);
        manager.getServer().getPluginManager().registerEvents(this, manager);
        BlenderstandManager.GetInstance().Add(this);
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
    private void Hit() {
        if (destroyable) {
            Kill();
        }
    }

    @EventHandler
    private void OnHit(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.ARMOR_STAND) {
            ArmorStand armourstand = (ArmorStand) event.getEntity();
            if (event.getDamager().getType() == EntityType.PLAYER) {
                Player player = (((Player) event.getDamager()).getPlayer());
                if (HasEntity(armourstand)) {
                    Hit();
                }
            }
        }
    }

    private boolean HasEntity(ArmorStand armourstand) {
        for (ArmourstandEntity e : armourstandEntities) {
            if (armourstand.equals(e.entity)) {
                return true;
            }
        }
        return false;
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
        BlenderstandManager.GetInstance().Remove(this);
    }
}
