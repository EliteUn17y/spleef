package EliteUn17y.spleef.Commands;

import EliteUn17y.spleef.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class Spleef implements CommandExecutor {

    static Main plugin;
    static HashMap<UUID, String> alivePlayers = new HashMap<UUID, String>();
    public CommandSender cmdsender;
    public static boolean started = false;
    public Spleef(Main instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        cmdsender = sender;
        if (!sender.hasPermission("spleef.spleef")) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return false;
        }
        if(label.equalsIgnoreCase("spleef") && sender.hasPermission("spleef.spleef")) {
            setAlivePlayers();
            if (!(sender instanceof Player)) {
                sender.sendMessage("Sorry, this command can only be executed by a player.");
                return false;
            }
            Bukkit.getServer().broadcastMessage("[" + ChatColor.GREEN + "Spleef" + ChatColor.WHITE + "] " + "Spleef starting in 5.....");
            Bukkit.getScheduler ().runTaskLater (plugin, () -> Bukkit.broadcastMessage ("[" + ChatColor.GREEN + "Spleef" + ChatColor.WHITE + "] Spleef starting in 4...."), 20);
            Bukkit.getScheduler ().runTaskLater (plugin, () -> Bukkit.broadcastMessage ("[" + ChatColor.GREEN + "Spleef" + ChatColor.WHITE + "] " + "Spleef starting in 3..."), 40);
            Bukkit.getScheduler ().runTaskLater (plugin, () -> Bukkit.broadcastMessage ("[" + ChatColor.GREEN + "Spleef" + ChatColor.WHITE + "] " + "Spleef starting in 2.."), 60);
            Bukkit.getScheduler ().runTaskLater (plugin, () -> Bukkit.broadcastMessage ("[" + ChatColor.GREEN + "Spleef" + ChatColor.WHITE + "] " + "Spleef starting in 1."), 80);
            Bukkit.getScheduler ().runTaskLater (plugin, this::spleefStart, 100);


        }
        return false;
    }
    public void spleefStart() {
        started = true;
        for (Player player : Bukkit.getOnlinePlayers()) {
            alivePlayers.put(player.getUniqueId(), player.getName());
            player.teleport(Bukkit.getPlayer(((Player) cmdsender).getUniqueId()).getLocation());
            player.getInventory().clear();
            player.getInventory().addItem(new ItemStack(Material.DIAMOND_SPADE, 1));
            player.setGameMode(GameMode.SURVIVAL);
        }
    }
    public static HashMap<UUID, String> getAlivePlayers() {
        return alivePlayers;
    }
    public void setAlivePlayers() {
        int id = plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.isDead()) {
                        if (alivePlayers.containsKey(player.getUniqueId())) {
                            alivePlayers.remove(player.getUniqueId());
                            return;
                        } else {
                            // System.out.println("Almost ran into a error!");
                        }

                    }
                }
            }}, 0, 1);
    }
}
