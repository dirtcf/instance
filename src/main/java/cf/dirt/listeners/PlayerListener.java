package cf.dirt.listeners;

import cf.dirt.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public final class PlayerListener implements Listener {

    private static final int FADE_IN = 20;
    private static final int STAY = 200;
    private static final int FADE_OUT = 75;

    private final Plugin plugin;

    private final String motd;
    private final String totd;
    private final String sotd;

    public PlayerListener(Plugin plugin, String motd, String totd, String sotd) {
        this.plugin = plugin;

        this.motd = motd;
        this.totd = totd;
        this.sotd = sotd;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendTitle(totd, sotd, FADE_IN, STAY, FADE_OUT);
        player.sendMessage(motd);

        if (!player.hasPlayedBefore()) {
            player.sendMessage(
                    ChatColor.GOLD + "use /manual to see instructions"
            );
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        final Location location = player.getLocation();

        new BukkitRunnable() {
            @Override
            public void run() {
                player.setCompassTarget(location);
                player.sendMessage("Compass target set to your latest death location");
            }
        }.runTaskLater(plugin, 1);
    }
}
