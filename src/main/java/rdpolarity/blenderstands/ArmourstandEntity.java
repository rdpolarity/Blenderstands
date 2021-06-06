package rdpolarity.blenderstands;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public class ArmourstandEntity {

    private double asHeight = 1.375;
    private double blockDimensions = 0.625;
    private ArmourstandObject armourstand;
    private Location spawnLoc;
    public ArmorStand entity;

    public ArmourstandEntity(ArmourstandObject armourstand, Location loc) {
        loc.setPitch(0);
        loc.setYaw(0);
        spawnLoc = loc;
        this.armourstand = armourstand;
        SpawnEntity();
    }

    private void SpawnEntity() {
        System.out.println(armourstand.location);
        Location loc = spawnLoc.clone().add(armourstand.location);
        loc.setY(loc.getY() - asHeight);
//        loc.setYaw((float) armourstand.rotation.getY());
//        loc.setPitch(45);
        ArmorStand as = loc.getWorld().spawn(loc, ArmorStand.class);

//        Why tf doesn't this rotate perfectly
        as.setHeadPose(new EulerAngle(
                armourstand.rotation.getX() * Math.PI / 180,
                armourstand.rotation.getY() * Math.PI / 180,
                armourstand.rotation.getZ() * Math.PI / 180
        ));

        as.setGravity(false);
        as.setCanPickupItems(false);
        as.setVisible(false);
        as.getEquipment().setHelmet(new ItemStack(Material.STONE));
        entity = as;
    }

    public void SetFrame(int frame) {
        if (armourstand.keyframes.length != 0) {
            Keyframe keyframe = armourstand.keyframes[frame];
            Location newLoc = spawnLoc.clone().add(keyframe.location);
            newLoc.setY(newLoc.getY() - asHeight);
            entity.teleport(newLoc);
            entity.setHeadPose(new EulerAngle(
                    keyframe.rotation.getX() * Math.PI / 180,
                    keyframe.rotation.getY() * Math.PI / 180,
                    keyframe.rotation.getZ() * Math.PI / 180
            ));
        }
    }
}
