package rdpolarity.blenderstands;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import rdpolarity.blenderstands.utillities.ItemBuilder;

import javax.inject.Inject;

public class BlenderEgg implements Listener {

    private enum EggTypes {
        BLAZE(Material.BLAZE_SPAWN_EGG),
        WOLF(Material.WOLF_SPAWN_EGG),
        CREEPER(Material.CREEPER_SPAWN_EGG),
        BAT(Material.BAT_SPAWN_EGG);
        private final Material material;
        EggTypes(Material material) { this.material = material; }
    }
    @Inject
    private Blenderstands plugin;
    @Inject
    private BlenderstandManager blenderstandManager;
    @Inject
    private BlenderstandFactory blenderstandFactory;

    private static final String EGG_FILE_KEY = "BlenderstandsFile";

    public static void Give(Player player, String fileName) {
        Material eggMat = EggTypes.values()[(int) (Math.random() * EggTypes.values().length)].material;
        ItemStack egg = new ItemBuilder(eggMat)
                .name(fileName)
                .lore("Blender Egg")
                .lore("Spawning from file: " + fileName)
                .make();
        // NBT Data
        NBTItem nbti = new NBTItem(egg);
        nbti.setString(EGG_FILE_KEY, fileName);
        player.getInventory().addItem(nbti.getItem());
    }

    private boolean isEgg(ItemStack item) {
        NBTItem nbti = new NBTItem(item);
        return nbti.hasKey(EGG_FILE_KEY);
    }

    private void Spawn(ItemStack item, Location loc) {
        NBTItem nbti = new NBTItem(item);
        String file = nbti.getString(EGG_FILE_KEY);
        Blenderstand bs = blenderstandFactory.create(file);
        bs.Spawn(loc.add(0.5, 1, 0.5));
    }

    @EventHandler
    private void OnSpawn(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item == null || item.getType() == Material.AIR) return;
            if (isEgg(item)) {
                Spawn(item, event.getClickedBlock().getLocation());
                event.setCancelled(true);
            }
        }
    }

}
