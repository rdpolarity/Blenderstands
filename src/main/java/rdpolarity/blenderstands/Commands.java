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
public final class Commands extends BaseCommand {

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
        bs.RunAnimation();
    }

    @Subcommand("spawnat")
    public void onSpawn(Player player, int frame) {
        Blenderstand bs = new Blenderstand("untitled");
        bs.Spawn(player.getLocation());
        bs.SetFrame(frame);
    }

    @Subcommand("give")
    public void onGive(Player player, String file) {
        BlenderEgg.Give(player, file);
    }

    @Subcommand("clear")
    public void onClear() {
        BlenderstandManager.GetInstance().Clear();
    }

    @Subcommand("equip")
    public void onEquip(Player player, String file) {
        Blenderstand bs = new Blenderstand(file);
    }
}
