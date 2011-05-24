package com.zones.commands.general;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.zones.Zones;
import com.zones.commands.ZoneCommand;
import com.zones.model.ZoneBase;

/**
 * 
 * @author Meaglin
 *
 */
public class ZSelectCommand extends ZoneCommand {

    public ZSelectCommand(Zones plugin) {
        super("zselect", plugin);
    }

    @Override
    public boolean run(Player player, String[] vars) {
        if(vars.length == 1){
            if(vars[0].equalsIgnoreCase("reset")) {
                getZoneManager().removeSelected(player.getEntityId());
                player.sendMessage(ChatColor.GREEN + "Selected zone removed.");
                return true;
            }
            List<ZoneBase> zoneslist = new ArrayList<ZoneBase>();
            try {
                int index = Integer.parseInt(vars[0]);
                ZoneBase b = getZoneManager().getZone(index);
                if(b != null) zoneslist.add(b);
            } catch(NumberFormatException e) {
                String var = vars[0].toLowerCase();
                for(ZoneBase b : getZoneManager().getAllZones())
                    if(b != null && b.getName().toLowerCase().contains(var) && b.canAdministrate(player))
                        zoneslist.add(b);
            }
            if(zoneslist.size() < 1)
                player.sendMessage(ChatColor.YELLOW + "No zones found with key '" + vars[0] + "'(which you can modify).");
            else if(zoneslist.size() == 1){
                getZoneManager().setSelected(player.getEntityId(), zoneslist.get(0).getId());
                player.sendMessage(ChatColor.GREEN.toString() + "Selected zone '" + zoneslist.get(0).getName() + "' .");
            } else {
                player.sendMessage(ChatColor.YELLOW +  "Too many zones found, please be more specific.");
                String temp = "";
                int delta = Integer.MAX_VALUE;
                ZoneBase closest = null;
                for (ZoneBase zone : zoneslist) {
                    if(closest == null || closest.getName().length()-vars[0].length() < delta) {
                        closest = zone;
                        delta = closest.getName().length()-vars[0].length();
                    }
                    temp += zone.getName() + "[" + zone.getId() + "]";
                }
                player.sendMessage(ChatColor.DARK_GREEN + "Zones found: " + temp);
                player.sendMessage(ChatColor.GOLD + "Selected closest match '" + closest.getName() +"' .");
                getZoneManager().setSelected(player.getEntityId(), closest.getId());
            }
        }else{
            List<ZoneBase> zoneslist = getWorldManager(player).getAdminZones(player);
            if(zoneslist.size() < 1) {
                player.sendMessage(ChatColor.YELLOW + "No zones found in your current area(which you can modify).");
                player.sendMessage(ChatColor.YELLOW + "Please select a zone by specifying a zone id.");
            } else if(zoneslist.size() == 1){
                getZoneManager().setSelected(player.getEntityId(), zoneslist.get(0).getId());
                player.sendMessage(ChatColor.GREEN + "Selected zone '" + zoneslist.get(0).getName() + "' .");
            } else {
                player.sendMessage(ChatColor.YELLOW +  "Too much zones found, please specify a zone id.(/zselect <id>)");
                String temp = "";
                for (ZoneBase zone : zoneslist)
                    temp += zone.getName() + "[" + zone.getId() + "]";
                player.sendMessage("Zones found: " + temp);
            }
        }
        return true;
    }

}
