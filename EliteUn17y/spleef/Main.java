package EliteUn17y.spleef;

import EliteUn17y.spleef.Commands.Spleef;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {

    public String versionCheck = "https://pastebin.com/raw/pzaTAfGF";
    public String version = "1.0.0";

    @Override
    public void onEnable() {
        // TODO: Implement a version check.

        try {
            if (!download(versionCheck).equals(version)) {
                Bukkit.broadcast("There is a new update! Version " + download(versionCheck) + ". You are on version " + version + ". Please update for the best updates!", "Spleef.recieveUpdateNotifications");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("                     ");
        System.out.println("Spleef V1.0.0 By EliteUn17y");
        System.out.println("                      ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~");

        registerCmds();

        System.out.println("Commands registered.");
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
            public void run() {
                if (Spleef.started) {
                    if (isWon()) {
                        Player p = getPlayerWon();
                        Bukkit.broadcastMessage("[" + ChatColor.GREEN + "Spleef" + ChatColor.WHITE + "]" + ChatColor.AQUA + p.getName() + ChatColor.WHITE + "won!");
                        Spleef.started = false;
                    }
                }
            }

        }, 0L, 1L);
    }
    private void registerCmds() {
        this.getCommand("spleef").setExecutor(new Spleef(this));
    }

    @Override
    public void onDisable() {

    }


    public boolean isWon() {
        HashMap<UUID, String> alivePlayers = new HashMap<UUID, String>();
        alivePlayers = Spleef.getAlivePlayers();
        if (alivePlayers.size() == 1) {
            return true;
        }
        return false;
    }
    public Player getPlayerWon() {
        HashMap<UUID, String> alivePlayers = new HashMap<UUID, String>();
        alivePlayers = Spleef.getAlivePlayers();
        if (alivePlayers.size() == 1) {
            for (UUID i : alivePlayers.keySet()) {
                return Bukkit.getPlayer(i);
            }
        }
        return null;
    }

    public static String download(String urlString) throws IOException {
        URL url = new URL(urlString);
        try(
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                return line;
            }
        }
        return null;
    }

}
