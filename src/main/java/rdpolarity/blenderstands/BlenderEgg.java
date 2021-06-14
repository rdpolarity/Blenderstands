package rdpolarity.blenderstands;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BlenderEgg implements Listener {

    public static void Give(Player player, String fileName) {
        ItemStack egg = new ItemStack(Material.BLAZE_SPAWN_EGG);
        // Item Meta
        ItemMeta stickMeta = egg.getItemMeta();
        stickMeta.setDisplayName(fileName);
        List<String> lore = new ArrayList<>();
        lore.add("Blender Egg");
        lore.add("Spawning from file: " + fileName);
        stickMeta.setLore(lore);
        egg.setItemMeta(stickMeta);
        // NBT Data
        net.minecraft.server.v1_16_R3.ItemStack itemNMS = CraftItemStack.asNMSCopy(egg);
        NBTTagCompound tag = itemNMS.getTag() != null ? itemNMS.getTag() : new NBTTagCompound();
        tag.setString("BlenderstandsFile", fileName);
        itemNMS.setTag(tag);
        egg = CraftItemStack.asCraftMirror(itemNMS);
        player.getInventory().addItem(egg);
    }

    private boolean isEgg(ItemStack item) {
        net.minecraft.server.v1_16_R3.ItemStack itemNMS = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = itemNMS.getTag() != null ? itemNMS.getTag() : null;
        if (tag != null) {
            return tag.hasKey("BlenderstandsFile");
        }
        return false;
    }

    private void Spawn(ItemStack item, Location loc) {
        net.minecraft.server.v1_16_R3.ItemStack itemNMS = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = itemNMS.getTag() != null ? itemNMS.getTag() : null;
        if (tag != null) {
            String file = tag.getString("BlenderstandsFile");
            Blenderstand stand = new Blenderstand(file);
            stand.setDestroyable(true);
            loc.add(0.5, 1, 0.5);
            stand.Spawn(loc);
        }
    }

    @EventHandler
    private void OnSpawn(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (isEgg(item)) {
                Spawn(item, event.getClickedBlock().getLocation());
                event.setCancelled(true);
            }
        }
    }
}
