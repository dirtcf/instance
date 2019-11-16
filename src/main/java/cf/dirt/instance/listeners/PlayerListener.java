package cf.dirt.instance.listeners;

import cf.dirt.instance.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public final class PlayerListener implements Listener {

    private static final int FADE_IN = 20;
    private static final int STAY = 200;
    private static final int FADE_OUT = 75;

    private final Plugin plugin;
    private final String joinMessage;

    public PlayerListener(Plugin plugin, String joinMessage) {
        this.plugin = plugin;
        this.joinMessage = joinMessage;
    }

    /*
        This temporary code will be replaced by 'announcements' plugin functionality in the future
     */
    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.sendTitle("Welcome", event.getPlayer().getDisplayName(), FADE_IN, STAY, FADE_OUT);
        player.sendMessage(joinMessage);

        if (!player.hasPlayedBefore()) {
            player.sendMessage(
                    ChatColor.GOLD + "use /manual to see instructions"
            );
        }
    }

    @EventHandler
    public void onPlayerSpawnLocation(PlayerSpawnLocationEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            World world = event.getSpawnLocation().getWorld();
            event.setSpawnLocation(world.getSpawnLocation());
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (player.getBedSpawnLocation() == null) {
            Location spawnLocation = player.getWorld().getSpawnLocation().add(
                    new Vector(0.5, 0, 0.5)
            );
            event.setRespawnLocation(spawnLocation);
        }

        final Location deathLocation = player.getLocation();

        new BukkitRunnable() {
            @Override
            public void run() {
                player.setCompassTarget(deathLocation);
            }
        }.runTaskLater(plugin, 1);
    }
}
