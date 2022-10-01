package Summiner.ServerPatches.Listeners.Spigot;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class PhysicsEvent implements Listener {

    /*
    In development please ignore
    Working on a custom physics event
     */
    @EventHandler
    public void physicsUpdate(BlockPhysicsEvent event) {
        event.setCancelled(true);
        Block block = event.getBlock();
        Location loc = block.getLocation();
        Location last = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
        while(true) {
            if(loc.getBlock().getType() == Material.AIR) {break;}
            last = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ());
            loc = new Location(loc.getWorld(), loc.getX(), loc.getY() - 1, loc.getZ());
        }
        last.getBlock().setType(event.getBlock().getType());
        event.getBlock().setType(Material.AIR);
    }

}
