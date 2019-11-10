package cf.dirt.commands.stats;

import com.sun.management.OperatingSystemMXBean;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.lang.management.ManagementFactory;

public final class MemoryCommand implements CommandExecutor {

    private static final long MEGA = 1024 * 1024;

    private static long ofBytes(long kiloBytes) {
        return kiloBytes / MEGA;
    }

    private static long getMax() {
        return ofBytes(Runtime.getRuntime().maxMemory());
    }

    private static long getTotal() {
        return ofBytes(Runtime.getRuntime().totalMemory());
    }

    private static long getUsed() {
        return getTotal() - getFree();
    }

    private static long getFree() {
        return ofBytes(Runtime.getRuntime().freeMemory());
    }

    private final OperatingSystemMXBean bean;

    public MemoryCommand() {
        this.bean = ManagementFactory.getPlatformMXBean(
                OperatingSystemMXBean.class
        );
    }

    private long getTotalPhysical() {
        return ofBytes(bean.getTotalPhysicalMemorySize());
    }

    private long getUsedPhysical() {
        return getTotalPhysical() - getFreePhysical();
    }

    private long getFreePhysical() {
        return ofBytes(bean.getFreePhysicalMemorySize());
    }

    private long getTotalSwap() {
        return ofBytes(bean.getTotalSwapSpaceSize());
    }

    private long getUsedSwap() {
        return getTotalSwap() - getFreeSwap();
    }

    private long getFreeSwap() {
        return ofBytes(bean.getFreeSwapSpaceSize());
    }

    private long getVirtual() {
        return ofBytes(bean.getCommittedVirtualMemorySize());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length > 0) {
            return false;
        }

        commandSender.sendMessage(
                String.format(
                        "total GC: %d\n" + "used GC: %d\n" + "free GC: %d\n" + "max GC: %d\n" +
                        "total PHYS: %d\n" + "used PHYS: %d\n" + "free PHYS: %d\n" +
                        "total SWAP: %d\n" + "used SWAP: %d\n" + "free SWAP: %d\n" +
                        "total VIRT: %d\n",
                        getTotal(), getUsed(), getFree(), getMax(),
                        getTotalPhysical(), getUsedPhysical(), getFreePhysical(),
                        getTotalSwap(), getUsedSwap(), getFreeSwap(),
                        getVirtual()
                )
        );

        return true;
    }
}
