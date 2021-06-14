package rdpolarity.blenderstands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import rdpolarity.blenderstands.data.ArmourstandObject;
import rdpolarity.blenderstands.data.Keyframe;

public class ArmourstandEntity {

    private final double headHeight = 1.375; // 1.375;
    private final double headDimensions = 0.625;
    private final double headRadius = -0.25;
    private ArmourstandObject armourstand;
    private Location spawnLoc;
    public ArmorStand entity;


    public ArmourstandEntity(ArmourstandObject armourstand, Location loc) {
        loc.setPitch(0);
        loc.setYaw(0);
        spawnLoc = loc;
        this.armourstand = armourstand;
        SpawnEntity(spawnLoc.clone().add(armourstand.location), armourstand.rotation);
    }

    private void SpawnEntity(Location loc, Vector rotation) {
        loc.setY(loc.getY() - headHeight);
        ArmorStand as = loc.getWorld().spawn(loc, ArmorStand.class);
        as.setHeadPose(new EulerAngle(rotation.getX(), rotation.getY(), rotation.getZ()));
        as.setGravity(false);
        as.setCanPickupItems(false);
        as.setVisible(false);
        as.setCustomNameVisible(false);
        as.getEquipment().setHelmet(new ItemStack(Material.STONE));
        as.teleport(loc);
        entity = as;
    }

    public void SetFrame(int frame) {
        if (armourstand.keyframes.length != 0) {
            Keyframe keyframe = armourstand.keyframes[frame];
            Location newLoc = spawnLoc.clone().add(keyframe.location);
            newLoc.subtract(0, headHeight, 0);
            entity.teleport(newLoc, PlayerTeleportEvent.TeleportCause.COMMAND);
            entity.setHeadPose(new EulerAngle(
                    keyframe.rotation.getX(),
                    keyframe.rotation.getY(),
                    keyframe.rotation.getZ()
            ));
        }
    }
}
