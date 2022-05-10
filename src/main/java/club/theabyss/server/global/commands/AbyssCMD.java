package club.theabyss.server.global.commands;

import club.theabyss.TheAbyssManager;
import club.theabyss.global.utils.chat.ChatFormatter;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
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
                .then(CommandManager.literal("deathMessage")
                        .then(CommandManager.literal("set")
                                .then(CommandManager.argument("deathMessage", StringArgumentType.string())
                                        .executes(context -> setDeathMessage(context, StringArgumentType.getString(context, "deathMessage")))))
                        .executes(AbyssCMD::getDeathMessage))
        );
    }

    private static int runRemainBloodmoon(CommandContext<ServerCommandSource> commandContext) throws CommandSyntaxException {
        var player = commandContext.getSource().getPlayer();
        var bloodMoonManager = TheAbyssManager.getInstance().serverCore().serverGameManager().bloodMoonManager();

        if (bloodMoonManager.isActive()) {
            player.sendMessage(ChatFormatter.stringFormatWithPrefixToText("&7Queda " + bloodMoonManager.getFormattedRemainingTime() + " de BloodMoon."), false);
        } else {
            player.sendMessage(ChatFormatter.stringFormatWithPrefixToText("&cNo hay una BloodMoon activa."), false);
        }
        return 1;
    }

    private static int runDay(CommandContext<ServerCommandSource> commandContext) throws CommandSyntaxException {
        var player = commandContext.getSource().getPlayer();
        player.sendMessage(ChatFormatter.stringFormatWithPrefixToText("&7El día actual es &6" + TheAbyssManager.getInstance().serverCore().serverGameManager().day() + "&7."), false);

        return 1;
    }

    private static int getDeathMessage(CommandContext<ServerCommandSource> commandContext) {
        var deathMessages = TheAbyssManager.getInstance().serverCore().deathMessagesManager().deathMessages().deathMessages();

        try {
            var player = commandContext.getSource().getPlayer();
            player.sendMessage(ChatFormatter.stringFormatWithPrefixToText("&7Tu mensaje actual es: &8" + deathMessages.get(player.getUuid()) + "&7."), false);
            return 1;
        } catch (CommandSyntaxException e) {
            commandContext.getSource().sendFeedback(ChatFormatter.stringFormatWithPrefixToText("&cEl command source no es un jugador."), false);
            return -1;
        }
    }

    private static int setDeathMessage(CommandContext<ServerCommandSource> commandContext, String message) {
        var deathMessages = TheAbyssManager.getInstance().serverCore().deathMessagesManager().deathMessages().deathMessages();

        try {
            deathMessages.put(commandContext.getSource().getPlayer().getUuid(), message);
            commandContext.getSource().getPlayer().sendMessage(ChatFormatter.stringFormatWithPrefixToText("&7Tu mensaje de muerte ha sido actualizado a: &8" + message + "&7."), false);
            return 1;
        } catch (CommandSyntaxException e) {
            commandContext.getSource().sendFeedback(ChatFormatter.stringFormatWithPrefixToText("&cEl command source no es un jugador."), false);
            return -1;
        }
    }

}
