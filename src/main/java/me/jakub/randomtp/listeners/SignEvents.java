package me.jakub.randomtp.listeners;

import me.jakub.randomtp.utils.TeleportUtils;
import me.jakub.randomtp.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignEvents implements Listener {



    @EventHandler
    public void onSignChange(SignChangeEvent e){
        Player player = e.getPlayer();
        Sign sign = (Sign) e.getBlock().getState();
        if (Utils.getEnabledSigns()) {
            if (e.getLine(0).equalsIgnoreCase("[RandomTP]") || e.getLine(0).equalsIgnoreCase("[RTP]")) {
                if (player.hasPermission("randomTp.sign.create")) {
                    e.setCancelled(true);
                    sign.setLine(0, ChatColor.GREEN + "[RandomTP]");
                    sign.setLine(1, ChatColor.AQUA + "Click to RTP!");
                    sign.update();
                    player.sendMessage(Utils.getSignCreateMessage());
                } else {
                    player.sendMessage(Utils.getNoPermission());
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if (Utils.getEnabledSigns()) {
                if (e.getClickedBlock().getState() instanceof Sign) {
                    Sign sign = (Sign) e.getClickedBlock().getState();

                    if (sign.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "[RandomTP]") &&
                            sign.getLine(1).equalsIgnoreCase(ChatColor.AQUA + "Click to RTP!")) {
                        Player player = e.getPlayer();
                        if (player.hasPermission("randomTp.sign.use")) {
                            Location loc = TeleportUtils.generateLocation(player);
                            TeleportUtils.tp(player, loc);
                        } else {
                            player.sendMessage(Utils.getNoPermission());
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if (Utils.getEnabledSigns()) {
            if (e.getBlock().getState() instanceof Sign) {
                Sign sign = (Sign) e.getBlock().getState();
                if (sign.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "[RandomTP]") &&
                        sign.getLine(1).equalsIgnoreCase(ChatColor.AQUA + "Click to RTP!")) {
                    Player player = e.getPlayer();
                    if (player.hasPermission("randomTp.sign.break")) {
                        player.sendMessage(Utils.getSignRemoveMessage());
                    } else {
                        e.setCancelled(true);
                        player.sendMessage(Utils.getNoPermission());
                    }
                }
            }
        }
    }

    //finally
}
