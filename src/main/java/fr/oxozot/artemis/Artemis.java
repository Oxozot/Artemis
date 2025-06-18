package fr.oxozot.artemis;

import fr.oxozot.artemis.Commands.CommandTest;
import fr.oxozot.artemis.Commands.SwitchModeCommand;
import fr.oxozot.artemis.listeners.ArtemisListener;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Artemis extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().sendMessage(Component.empty().content("§aLe serv va bien"));
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new ArtemisListener(this), this);

        getCommand("test").setExecutor(new CommandTest(this));
        getCommand("switchMode").setExecutor(new SwitchModeCommand(this));

    }

    @Override
    public void onDisable() {
        getServer().sendMessage(Component.empty().content("§cLe serv va mal"));
    }
}
