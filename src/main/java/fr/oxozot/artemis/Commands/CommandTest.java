package fr.oxozot.artemis.Commands;

import fr.oxozot.artemis.Artemis;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandTest implements CommandExecutor {

    private static Artemis main;
    public CommandTest(Artemis main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String name, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage(Component.text("Tu dois etre un joueur pour executer cette commande").color(TextColor.color(255, 0, 0)));
        } else {

            Player player = (Player) sender;

            main.getServer().sendMessage(Component.text(player.getName().toString() + " vient de faire la commande de Test").color(TextColor.color(0, 255, 0)));

        }

        return true;
    }
}
