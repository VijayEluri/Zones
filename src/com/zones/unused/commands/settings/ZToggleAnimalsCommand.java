package com.zones.unused.commands.settings;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.zones.Zones;
import com.zones.model.ZoneBase;
import com.zones.model.settings.ZoneVar;
import com.zones.unused.commands.ZoneCommand;

/**
 * 
 * @author Meaglin
 *
 */
public class ZToggleAnimalsCommand extends ZoneCommand {

    public ZToggleAnimalsCommand(Zones plugin) {
        super("ztoggleanimals", plugin);
        this.setRequiresSelected(true);
        this.setRequiredAccess("zones.toggle.animals");
    }

    @Override
    public void run(Player player, String[] vars) {
        ZoneBase z = getSelectedZone(player);
        if(z.setSetting(ZoneVar.SPAWN_ANIMALS, !z.getSettings().getBool(ZoneVar.SPAWN_ANIMALS, z.getWorldManager().getConfig().ANIMAL_SPAWNING_ENABLED)))
            player.sendMessage(ChatColor.GREEN.toString() + "Animal spawning is now "+(z.getSettings().getBool(ZoneVar.SPAWN_ANIMALS, z.getWorldManager().getConfig().ANIMAL_SPAWNING_ENABLED) ? "enabled" : "disabled" )+".");
        else
            player.sendMessage(ChatColor.RED.toString() + "Unable to change animals flag, please contact a admin.");
    }
}