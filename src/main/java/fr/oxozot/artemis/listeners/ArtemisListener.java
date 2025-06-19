package fr.oxozot.artemis.listeners;

import fr.oxozot.artemis.Artemis;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
import org.bukkit.util.Vector;

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
    public static void onBreakBlock(BlockBreakEvent e) {
        Player player = e.getPlayer();

        Block block = e.getBlock();


        Location blockLoc = block.getLocation();

        double blockLocX = blockLoc.getBlockX();
        double blockLocY = blockLoc.getBlockY();
        double blockLocZ = blockLoc.getBlockZ();

        Location playerEyeLoc = player.getEyeLocation();
        Location EyeBlockLoc = block.getLocation().add(0.5, 0.5, 0.5); // Centre du bloc

        Vector direction = blockLoc.toVector().subtract(playerEyeLoc.toVector()).normalize();

        BlockFace face = getNearestBlockFace(direction);
        float pitch = player.getLocation().getPitch();

        ItemStack item = player.getInventory().getItem(player.getInventory().getHeldItemSlot());
        if (item != null) {
            item.getType();
            if (item.getType() != Material.AIR && item.getType() == Material.DIAMOND_PICKAXE) {

                if (!item.hasItemMeta()) {
                } else {
                    ItemMeta itM = item.getItemMeta();

                    NamespacedKey typePickaxe = new NamespacedKey(main, "typePickaxe");

                    PersistentDataContainer itemContainer = itM.getPersistentDataContainer();

                    if (itemContainer.has(typePickaxe, PersistentDataType.BOOLEAN)) {

                        Boolean value = itemContainer.get(typePickaxe, PersistentDataType.BOOLEAN);
                        if (Boolean.FALSE.equals(value)) {
                        } else {
                            // drop l'item casse autour du block casse (a appliquer pour les autres blocks)


                            // casse un block autour du block casse
                            // verfier si le block qu'on casse autour ne soit pas un block incassable comme la bedrock ou portail de l'end
                            // player.getWorld().getBlockAt((int) (blockLocX + 1), (int) blockLocY, (int) blockLocZ).setType(Material.AIR);

                            ArrayList<Material> IllegalBlockMaterial = new ArrayList<>();

                            IllegalBlockMaterial.add(Material.BEDROCK);
                            IllegalBlockMaterial.add(Material.COMMAND_BLOCK);
                            IllegalBlockMaterial.add(Material.REPEATING_COMMAND_BLOCK);
                            IllegalBlockMaterial.add(Material.CHAIN_COMMAND_BLOCK);
                            IllegalBlockMaterial.add(Material.BARRIER);
                            IllegalBlockMaterial.add(Material.STRUCTURE_BLOCK);
                            IllegalBlockMaterial.add(Material.END_PORTAL_FRAME);

                            for (Material material: IllegalBlockMaterial){

                            }


                            if (face == BlockFace.SOUTH || face == BlockFace.NORTH) {
                                for (int i = (int) blockLocX - 1; i <= blockLocX + 1; i++) {

                                    for (int j = (int) (blockLocY - 1); j <= blockLocY + 1; j++) {


                                        Material tempMat = player.getWorld().getBlockAt(i, j, (int) blockLocZ).getType();

                                        Location locTemp = player.getWorld().getBlockAt(i, j, (int) blockLocZ).getLocation();

                                        ItemStack itTemp = new ItemStack(tempMat, 1);

                                        player.getWorld().dropItem(locTemp, itTemp);

                                        player.getWorld().getBlockAt(i, j, (int) blockLocZ).setType(Material.AIR);


                                    }

                                }
                            } else if (face == BlockFace.WEST || face == BlockFace.EAST) {

                                for (int i = (int) blockLocZ - 1; i <= blockLocZ + 1; i++) {

                                    for (int j = (int) (blockLocY - 1); j <= blockLocY + 1; j++) {


                                        Material tempMat = player.getWorld().getBlockAt((int) blockLocX, j, i).getType();

                                        Location locTemp = player.getWorld().getBlockAt((int) blockLocX, j, i).getLocation();

                                        ItemStack itTemp = new ItemStack(tempMat, 1);

                                        player.getWorld().dropItem(locTemp, itTemp);

                                        player.getWorld().getBlockAt((int) blockLocX, j, i).setType(Material.AIR);


                                    }

                                }
                            } else if (face == BlockFace.UP || face == BlockFace.DOWN) {

                                for (int i = (int) blockLocX - 1; i <= blockLocX + 1; i++) {

                                    for (int j = (int) (blockLocZ - 1); j <= blockLocZ + 1; j++) {

                                        Material tempMat = player.getWorld().getBlockAt(i, (int) blockLocY, j).getType();

                                        Location locTemp = player.getWorld().getBlockAt(i, (int) blockLocY, j).getLocation();

                                        ItemStack itTemp = new ItemStack(tempMat, 1);

                                        player.getWorld().dropItem(locTemp, itTemp);

                                        player.getWorld().getBlockAt(i, (int) blockLocY, j).setType(Material.AIR);


                                    }

                                }
                            }


                        }

                    }

                }

            }
        }

    }

    private static BlockFace getNearestBlockFace(Vector direction) {
        BlockFace closest = BlockFace.NORTH;
        double closestDot = -1.0;

        for (BlockFace face : new BlockFace[]{
                BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH,
                BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST
        }) {
            Vector faceVec = face.getDirection();
            double dot = direction.dot(faceVec);
            if (dot > closestDot) {
                closestDot = dot;
                closest = face;
            }
        }
        return closest;
    }

}
