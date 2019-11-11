package cf.dirt.instance.commands.statistics;

import com.sun.management.OperatingSystemMXBean;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.lang.management.ManagementFactory;

public final class ProcessorCommand implements CommandExecutor {

    private final OperatingSystemMXBean bean;

    public ProcessorCommand() {
        this.bean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class
        );
    }

    private double getProcessLoad() {
        return bean.getProcessCpuLoad();
    }

    private double getSystemLoad() {
        return bean.getSystemCpuLoad();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length > 0) {
            return false;
        }

        commandSender.sendMessage(
                String.format(
                        "process load: %f\n" + "system load: %f",
                        getProcessLoad(), getSystemLoad()
                )
        );

        return true;
    }
}
