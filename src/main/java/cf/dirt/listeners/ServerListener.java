package cf.dirt.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public final class ServerListener implements Listener {

    private final String motd;

    public ServerListener(String motd) {
        this.motd = motd;
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onServerListPing(ServerListPingEvent event) {
        event.setMotd(motd);
    }
}
