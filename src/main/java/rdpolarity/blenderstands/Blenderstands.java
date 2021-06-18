package rdpolarity.blenderstands;

import co.aikar.commands.BukkitCommandManager;
import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import rdpolarity.blenderstands.modules.BlenderstandsModule;

import javax.inject.Inject;

@Plugin(name="Blenderstands", version = "1.0")
@ApiVersion(ApiVersion.Target.v1_16)
@Description("Convert blender animations to armourstand animations and models")
@Author("RDPolarity")

public final class Blenderstands extends JavaPlugin {

    @Inject private BlenderstandManager blenderstandManager;
    @Inject private Commands commands;
    @Inject private BlenderEgg blenderEgg;
    @Inject private Injector injector;

    @Override
    public void onEnable() {
        // Dependency Injection
        BlenderstandsModule module = new BlenderstandsModule(this);
        Injector injector = module.createInjector();
        injector.injectMembers(this);

        BukkitCommandManager manager = new BukkitCommandManager(this);
        manager.registerCommand(this.commands);
        getServer().getPluginManager().registerEvents(blenderEgg, this);
        getServer().getPluginManager().registerEvents(blenderstandManager, this);
        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic;
        blenderstandManager.Clear();
    }
}
