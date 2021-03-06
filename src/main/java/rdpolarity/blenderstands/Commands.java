package rdpolarity.blenderstands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.google.inject.Injector;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.annotation.command.Command;
import redis.clients.jedis.Jedis;

import javax.inject.Inject;

@CommandAlias("blenderstands|bs")
@Command(
        name = "Blenderstands",
        desc = "Core command for blenderstands plugin",
        aliases = {"blenderstands", "bs"},
        usage = "/<command>"
)
public final class Commands extends BaseCommand {

    @Inject
    private Blenderstands plugin;
    @Inject
    private BlenderstandManager blenderstandManager;
    @Inject
    private BlenderstandFactory blenderstandFactory;

    @Default
    @Subcommand("spawn")
    public void onSpawn(Player player) {
        Blenderstand bs = blenderstandFactory.create("untitled");
        bs.Spawn(player.getLocation());
    }

    @Default
    @Subcommand("spawn")
    public void onSpawn(Player player, String name) {
        Blenderstand bs = blenderstandFactory.create(name);
        bs.Spawn(player.getLocation());
    }

    @Default
    @Subcommand("spawnanimation")
    public void onSpawnAnimation(Player player) {
        Blenderstand bs = blenderstandFactory.create("untitled");
        bs.Spawn(player.getLocation());
        bs.RunAnimation();
    }

    @Subcommand("spawnat")
    public void onSpawn(Player player, int frame) {
        Blenderstand bs = blenderstandFactory.create("untitled");
        bs.Spawn(player.getLocation());
        bs.SetFrame(frame);
    }

    @Subcommand("give")
    public void onGive(Player player, String file) {
        BlenderEgg.Give(player, file);
    }

    @Subcommand("clear")
    public void onClear() {
        blenderstandManager.Clear();
    }

    @Subcommand("equip")
    public void onEquip(Player player, String file) {
//        Blenderstand bs = new Blenderstand(file, blenderstandManager);
    }

    @Subcommand("test")
    public void onTest(Player player) {
        player.sendMessage("testing a new response");
    }
}
