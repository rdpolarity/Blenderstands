package rdpolarity.blenderstands;

import co.aikar.commands.BukkitCommandManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name="Blenderstands", version = "1.0")
@Description("Convert blender animations to armourstand animations and models")
@Author("RDPolarity")
public final class Blenderstands extends JavaPlugin {

    public BlenderstandManager blenderstandsManager = new BlenderstandManager();

    @Override
    public void onEnable() {
        // Plugin startup logic
        BukkitCommandManager manager = new BukkitCommandManager(this);
        manager.registerCommand(new Commands());
        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        blenderstandsManager.Clear();
        // Plugin shutdown logic
    }
}
