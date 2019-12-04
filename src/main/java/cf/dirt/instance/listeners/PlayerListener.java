package cf.dirt.instance.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerListener implements Listener {

    private static final int FADE_IN = 20;
    private static final int STAY = 75;
    private static final int FADE_OUT = 50;

    /*
        This temporary code will be replaced by 'announcements' plugin functionality in the future
     */
    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.sendTitle(
                String.format("%sWelcome", ChatColor.GOLD),
                player.getDisplayName(),
                FADE_IN, STAY, FADE_OUT
        );
    }
}
