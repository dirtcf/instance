package cf.dirt;

import cf.dirt.commands.MessageCommand;
import cf.dirt.commands.stats.MemoryCommand;
import cf.dirt.commands.stats.ProcessorCommand;
import cf.dirt.listeners.PlayerListener;
import cf.dirt.listeners.ServerListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

@SuppressWarnings("unused")
public final class Plugin extends JavaPlugin {

    private static String translateColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private String getConfigString(String path) {
        return translateColors(Objects.requireNonNull(getConfig().getString(path))); // TODO check if objects needed
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        try {
            getCommand("memory").setExecutor(
                    new MemoryCommand()
            );
            getCommand("processor").setExecutor(
                    new ProcessorCommand()
            );

            getCommand("manual").setExecutor(
                    new MessageCommand(
                            getConfigString("manual.description"),
                            getConfigString("manual.message")
                    )
            );
            getCommand("statute").setExecutor(
                    new MessageCommand(
                            getConfigString("statute.description"),
                            getConfigString("statute.message")

                    )
            );

            PluginManager manager = Bukkit.getPluginManager();

            manager.registerEvents(new PlayerListener(this,
                    getConfigString("motd"),
                    getConfigString("totd"),
                    getConfigString("sotd")
            ), this);

            manager.registerEvents(new ServerListener(
                    translateColors(getServer().getMotd())
            ), this);
        }
        catch (NullPointerException exception) {
            StringWriter writer = new StringWriter();
            exception.printStackTrace(new PrintWriter(writer));
            getLogger().severe(
                    String.format(
                            "Failed to load configuration: \n%s",
                            writer.toString()
                    )
            );
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }
}
