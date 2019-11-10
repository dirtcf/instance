package cf.dirt.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public final class MessageCommand implements CommandExecutor {

    private static final int WORDS_PER_PAGE = 25;

    private final String description;
    private final List<String> pages;

    private static List<String> split(String message) {
        String[] words = message.split("\\s+|\\t+");
        List<String> pages = new ArrayList<>(
                (int) Math.ceil(words.length / (double) WORDS_PER_PAGE)
        );

        for (int i = 0; i < words.length;) {
            StringJoiner joiner = new StringJoiner(" ");
            for (int j = i; i - j <= WORDS_PER_PAGE && i < words.length; i++) {
                joiner.add(words[i]);
            }
            String page = joiner.toString();
            pages.add(page);
        }

        return pages;
    }

    public MessageCommand(String description, String message) {
        this.description = description;
        this.pages = split(message);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        switch (strings.length) {
            case 0 : {
                commandSender.sendMessage(
                        String.format(
                                "description: %s\npages: %d",
                                description, pages.size()
                        )
                );
            } break;
            case 1 : {
                try {
                    final int pageIndex = Integer.parseInt(strings[0]) - 1;
                    commandSender.sendMessage(pages.get(pageIndex));
                }
                catch (IndexOutOfBoundsException | NumberFormatException exception) {
                    commandSender.sendMessage(
                            String.format(
                                    "No such page in %s: %s",
                                    command.getName(), strings[0]
                            )
                    );
                }
            } break;
            default : return false;
        }

        return true;
    }
}
