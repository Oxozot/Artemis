package fr.oxozot.artemis.listeners;

import fr.oxozot.artemis.Artemis;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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
    public static void onBreakBlock(BlockBreakEvent e){
        Player player = e.getPlayer();

        Block block = e.getBlock();

        Location blockLoc = block.getLocation();

        double blockLocX = blockLoc.getBlockX();
        double blockLocY = blockLoc.getBlockY();
        double blockLocZ = blockLoc.getBlockZ();


        ItemStack item = player.getInventory().getItem(player.getInventory().getHeldItemSlot());
        assert item != null;
        if (item.getType() == Material.DIAMOND_PICKAXE){

            if (!item.hasItemMeta()){
                player.sendMessage(Component.text("pas d'item M"));
            } else {

                player.sendMessage(Component.text("il y a item M"));

                ItemMeta itM = item.getItemMeta();

                NamespacedKey typePickaxe = new NamespacedKey(main, "typePickaxe");

                PersistentDataContainer itemContainer = itM.getPersistentDataContainer();

                if (itemContainer.has(typePickaxe, PersistentDataType.BOOLEAN)){

                    player.sendMessage(Component.text("a persistent Data Type de type Boolean"));


                    Boolean value = itemContainer.get(typePickaxe, PersistentDataType.BOOLEAN);
                    if (Boolean.FALSE.equals(value)){
                        player.sendMessage(Component.text("Boolean False"));

                    } else {
                        player.sendMessage(Component.text("Boolean True"));
                        player.getWorld().getBlockAt((int) (blockLocX + 1), (int) blockLocY, (int) blockLocZ).setType(Material.AIR);

                    }

                }

            }

        }

    }
}
