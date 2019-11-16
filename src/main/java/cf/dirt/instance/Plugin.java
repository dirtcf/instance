package cf.dirt.instance;

import cf.dirt.instance.commands.MessageCommand;
import cf.dirt.instance.commands.statistics.MemoryCommand;
import cf.dirt.instance.commands.statistics.ProcessorCommand;
import cf.dirt.instance.listeners.PlayerListener;
import cf.dirt.instance.listeners.ServerListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.PrintWriter;
import java.io.StringWriter;

@SuppressWarnings("unused")
public final class Plugin extends JavaPlugin {

    private static String translateColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private String getConfigString(String path) {
        return translateColors(getConfig().getString(path));
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
                    getConfigString("join_message")
            ), this);

            manager.registerEvents(new ServerListener(
                    translateColors(getServer().getMotd())
            ), this);
        }
        catch (NullPointerException | IllegalArgumentException exception) {
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
