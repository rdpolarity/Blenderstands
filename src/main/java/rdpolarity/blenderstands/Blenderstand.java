package rdpolarity.blenderstands;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.io.*;
import java.util.ArrayList;

public class Blenderstand {

    public ArmourstandObject[] armourstands;
    public ArrayList<ArmourstandEntity> armourstandEntities = new ArrayList<>();
    private BlenderstandManager manager = Blenderstands.getPlugin(Blenderstands.class).blenderstandsManager;
    private int currentFrame = 0;

    public Blenderstand(String data) {
        armourstands = GetObjects(data);
        manager.Add(this);
    }

    public ArmourstandObject[] GetObjects(String name) {
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

    public void Spawn(Location loc) {
        for (ArmourstandObject armourstand : armourstands) {
            ArmourstandEntity newEntity = new ArmourstandEntity(armourstand, loc);
            armourstandEntities.add(newEntity);
        }
    }

    public void SetFrame(int frame) {
        for (ArmourstandEntity armourstandEntity : armourstandEntities) {
            armourstandEntity.SetFrame(frame);
        }
    }


    public void Run() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Blenderstands.getPlugin(Blenderstands.class), () -> {
            if (currentFrame >= armourstands[0].keyframes.length) {
                currentFrame = 0;
            }
            this.SetFrame(currentFrame);
            currentFrame++;
        }, 0, 5);
    }

    public void Kill() {
        armourstandEntities.forEach(e -> e.entity.setHealth(0));
    }
}
