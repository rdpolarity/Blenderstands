package rdpolarity.blenderstands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.annotation.command.Command;

@CommandAlias("blenderstands|bs")
@Command(
        name = "Blenderstands",
        desc = "Core command for blenderstands plugin",
        aliases = {"blenderstands", "bs"},
        usage = "/<command>"
)
public class Commands extends BaseCommand {

    Blenderstands plugin = Blenderstands.getPlugin(Blenderstands.class);

    @Default
    @Subcommand("spawn")
    public void onSpawn(Player player) {
        Blenderstand bs = new Blenderstand("untitled");
        bs.Spawn(player.getLocation());
    }

    @Default
    @Subcommand("spawn")
    public void onSpawn(Player player, String name) {
        Blenderstand bs = new Blenderstand(name);
        bs.Spawn(player.getLocation());
    }

    @Default
    @Subcommand("spawnanimation")
    public void onSpawnAnimation(Player player) {
        Blenderstand bs = new Blenderstand("untitled");
        bs.Spawn(player.getLocation());
        bs.Run();
    }

    @Subcommand("spawnat")
    public void onSpawn(Player player, int frame) {
        Blenderstand bs = new Blenderstand("untitled");
        bs.Spawn(player.getLocation());
        bs.SetFrame(frame);
    }


    @Subcommand("clear")
    public void onClear() {
        plugin.blenderstandsManager.Clear();
    }
}
