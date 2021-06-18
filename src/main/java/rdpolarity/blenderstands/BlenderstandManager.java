package rdpolarity.blenderstands;

import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Optional;

/**
 * This class keeps track of all active armourstand entities
 */
@Singleton
public class BlenderstandManager implements Listener {
    @Inject
    public BlenderstandManager() {
        Bukkit.broadcastMessage(this.toString());
    }

    private final ArrayList<Blenderstand> blenderstands = new ArrayList<>();

    public void Add(Blenderstand blenderstand) {
        blenderstands.add(blenderstand);
    }

    public void Remove(Blenderstand blenderstand) {
        blenderstands.remove(blenderstand);
    }

    @EventHandler
    private void OnHit(EntityDamageByEntityEvent event) {
        if (event.getEntityType() == EntityType.ARMOR_STAND) {
            ArmorStand armourstand = (ArmorStand) event.getEntity();
            if (event.getDamager().getType() == EntityType.PLAYER) {
                Player player = (((Player) event.getDamager()).getPlayer());
                Optional<Blenderstand> blenderstand = FindArmourstandEntity(armourstand);
                blenderstand.ifPresent(Blenderstand::Hit);
                blenderstand.ifPresent(this::Remove);
            }
        }
    }

    private Optional<Blenderstand> FindArmourstandEntity(ArmorStand armourstand) {
        return blenderstands.stream().filter(blenderstand ->
                blenderstand.armourstandEntities.stream().anyMatch(group ->
                        armourstand.equals(group.entity)
                )
        ).findFirst();
    }

    public void Clear() {
        Bukkit.broadcastMessage("Cleared");
        blenderstands.forEach(Blenderstand::Kill);
        blenderstands.clear();
    }
}
