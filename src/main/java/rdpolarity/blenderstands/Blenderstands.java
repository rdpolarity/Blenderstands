package rdpolarity.blenderstands;

import co.aikar.commands.BukkitCommandManager;
import com.google.inject.Injector;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import rdpolarity.blenderstands.modules.BlenderstandsModule;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import javax.inject.Inject;
import java.net.URI;

@Plugin(name="Blenderstands", version = "1.0")
@ApiVersion(ApiVersion.Target.v1_16)
@Description("Convert blender animations to armourstand animations and models")
@Author("RDPolarity")

public final class Blenderstands extends JavaPlugin {

    @Inject private BlenderstandManager blenderstandManager;
    @Inject private Commands commands;
    @Inject private BlenderEgg blenderEgg;
    @Inject private Injector injector;

    private Jedis jedis;

    @Override
    public void onEnable() {
        // Dependency Injection
        BlenderstandsModule module = new BlenderstandsModule(this);
        Injector injector = module.createInjector();
        initSockets();
        injector.injectMembers(this);
        BukkitCommandManager manager = new BukkitCommandManager(this);
        manager.registerCommand(this.commands);
        getServer().getPluginManager().registerEvents(blenderEgg, this);
        getServer().getPluginManager().registerEvents(blenderstandManager, this);
        this.saveDefaultConfig();
        Bukkit.broadcastMessage("[Blenderstands] Loaded");
    }

    @Override
    public void onDisable() {
        blenderstandManager.Clear();
        try {
            if (jedis != null) jedis.close();
        } catch (Exception ex) {
            System.out.println("Exception : " + ex.getMessage());
        }
    }

    private void initSockets() {
        this.getServer().getScheduler().runTaskAsynchronously(this, () -> {
            try {
                jedis = new Jedis("localhost", 6379, 5000);
                JedisPubSub jedisPubSub = new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        if (channel.equals("redis.reload")) {
                            Bukkit.broadcastMessage(ChatColor.BOLD + "Gradle Build Finished!");
                            Bukkit.broadcastMessage(ChatColor.YELLOW + "Reloading Plugin...");
                            Bukkit.reload();
                            Bukkit.broadcastMessage(ChatColor.GREEN + "Reload Complete");
                        }
                    }

                    @Override
                    public void onSubscribe(String channel, int subscribedChannels) {
                        Bukkit.broadcastMessage("Message Subscribed");
                    }

                    @Override
                    public void onUnsubscribe(String channel, int subscribedChannels) {
                        Bukkit.broadcastMessage("Message UnSubscribed");
                    }
                };
                jedis.subscribe(jedisPubSub, "redis.reload");
            } catch (Exception ex) {
                System.out.println("Exception : " + ex.getMessage());
            } finally {
                if (jedis != null) jedis.close();
            }
        });
    }
}
