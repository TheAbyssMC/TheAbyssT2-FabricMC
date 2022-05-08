package club.theabyss.server.global.commands;

import club.theabyss.TheAbyssManager;
import club.theabyss.global.utils.chat.ChatFormatter;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class AbyssCMD {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        dispatcher.register(CommandManager.literal("abyss")
                .then(CommandManager.literal("day").executes(AbyssCMD::runDay))
                .then(CommandManager.literal("bloodmoon")
                        .then(CommandManager.literal("remain")
                                .executes(AbyssCMD::runRemainBloodmoon)))
        );
    }

    private static int runRemainBloodmoon(CommandContext<ServerCommandSource> commandContext) throws CommandSyntaxException {
        var player = commandContext.getSource().getPlayer();
        var bloodMoonManager = TheAbyssManager.getInstance().serverCore().serverGameManager().bloodMoonManager();

        if (bloodMoonManager.isActive()) {
            player.sendMessage(ChatFormatter.textFormatWithPrefix("&7Quedan " + bloodMoonManager.getFormattedRemainingTime() + " de BloodMoon."), false);
        } else {
            player.sendMessage(ChatFormatter.textFormatWithPrefix("&cNo hay una BloodMoon activa."), false);
        }
        return 1;
    }

    private static int runDay(CommandContext<ServerCommandSource> commandContext) throws CommandSyntaxException {
        var player = commandContext.getSource().getPlayer();
        player.sendMessage(ChatFormatter.textFormatWithPrefix("&7El día actual es &6" + TheAbyssManager.getInstance().serverCore().serverGameManager().day() + "&7."), false);

        return 1;
    }

}
