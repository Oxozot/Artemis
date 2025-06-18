package fr.oxozot.artemis.Commands;

import fr.oxozot.artemis.Artemis;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class SwitchModeCommand implements CommandExecutor {

    private Artemis main;

    public SwitchModeCommand(Artemis main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String @NotNull [] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage(Component.text("Tu dois etre un joueur pour execute cette commande").color(TextColor.color(255, 0, 0)));
        } else {

            Player player = (Player) sender;

            ItemStack item = player.getInventory().getItemInMainHand();

            if (item != null && item.getType() != null && item.getType() != Material.AIR && item.getType() == Material.DIAMOND_PICKAXE) {
                player.sendActionBar(Component.text("Changement de mode effectue").color(TextColor.color(0, 255, 0)));

                ItemMeta itM = item.getItemMeta();

                NamespacedKey typePickaxe = new NamespacedKey(main, "typePickaxe");

                PersistentDataContainer itemContainer = itM.getPersistentDataContainer();

                if (itemContainer.has(typePickaxe, PersistentDataType.BOOLEAN)) {

                    player.sendMessage(Component.text("a un PersistentDataContainer et de type boolean"));

                    Boolean value = itemContainer.get(typePickaxe, PersistentDataType.BOOLEAN);

                    if (Boolean.FALSE.equals(value)) {
                        itemContainer.set(typePickaxe, PersistentDataType.BOOLEAN, true); // true si c'est un Hammer et false une pioche

                        itM.customName(Component.text("Hammer").color(TextColor.color(0,255, 0)));


                        item.setItemMeta(itM); // 💥 OBLIGATOIRE pour que les changements soient appliqués
                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
                        player.sendMessage(Component.text(Objects.requireNonNull(itemContainer.get(typePickaxe, PersistentDataType.BOOLEAN)).toString() + " Mode de la pioche"));

                    } else {
                        itemContainer.set(typePickaxe, PersistentDataType.BOOLEAN, false); // true si c'est un Hammer et false une pioche

                        itM.customName(Component.text("Pioche").color(TextColor.color(0,255, 0)));

                        item.setItemMeta(itM); // 💥 OBLIGATOIRE pour que les changements soient appliqués
                        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
                        player.sendMessage(Component.text(Objects.requireNonNull(itemContainer.get(typePickaxe, PersistentDataType.BOOLEAN)).toString() + " Mode de la pioche"));

                    }

                } else {
                    itemContainer.set(typePickaxe, PersistentDataType.BOOLEAN, true); // true si c'est un Hammer et false une pioche
                    itM.customName(Component.text("Hammer").color(TextColor.color(0,255, 0)));

                    item.setItemMeta(itM); // 💥 OBLIGATOIRE pour que les changements soient appliqués
                    player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
                }

            }


        }
        return true;
    }
}