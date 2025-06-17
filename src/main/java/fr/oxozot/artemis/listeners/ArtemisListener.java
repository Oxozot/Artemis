package fr.oxozot.artemis.listeners;

import fr.oxozot.artemis.Artemis;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ArtemisListener implements Listener {

    private static Artemis main;
    public ArtemisListener(Artemis main) {
        this.main = main;
    }

    @EventHandler
    public static void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        e.joinMessage(Component.text(""));
        main.getServer().sendMessage(Component.text("Coucou sale negre").color(TextColor.color(0, 255, 0)));

    }
    @EventHandler
    public static void onInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        ItemStack item = e.getItem();

        if (item != null && item.getType() == Material.DIAMOND.DIAMOND_PICKAXE){
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){
                player.sendMessage(Component.text("click droit avc pioche en diams").color(TextColor.color(255, 0, 255)));


                ItemMeta itemMeta = item.getItemMeta();
                if (itemMeta != null) {
                    // Vérifier si la lore est nulle
                    if (itemMeta.lore() != null && !Objects.requireNonNull(itemMeta.lore()).isEmpty()) {
                        return;
                    }
                    List<Component> loreItem = new ArrayList<>();

                    // Ajouter un nouvel élément à la lore
                    loreItem.add(Component.text("Caca"));


                    // Appliquer la nouvelle lore
                    itemMeta.lore(loreItem);
                    item.setItemMeta(itemMeta);  // Appliquer les changements à l'objet
                    player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);

                }

            }
        }
    }
}
