package club.theabyss.server.global.commands;

import club.theabyss.TheAbyssManager;
import club.theabyss.global.utils.chat.ChatFormatter;
import club.theabyss.server.game.skilltree.enums.Skills;
import club.theabyss.server.global.commands.arguments.SkillsArgumentType;
import club.theabyss.server.global.events.GameDateEvents;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.LongArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.time.LocalDate;
import java.util.HashMap;

public class AbyssStaffCMD {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
        LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = CommandManager.literal("abyssStaff").requires((source) -> source.hasPermissionLevel(4));

        literalArgumentBuilder
                .then(CommandManager.literal("changeDay")
                        .then(CommandManager.argument("day", IntegerArgumentType.integer())
                                .executes(context -> changeDay(context, IntegerArgumentType.getInteger(context, "day")))))
                .then(CommandManager.literal("bloodmoon")
                        .then(CommandManager.literal("end")
                                .executes(AbyssStaffCMD::endBloodMoon))
                        .then(CommandManager.literal("start")
                                .then(CommandManager.argument("minutes", IntegerArgumentType.integer())
                                        .executes(context -> startBloodMoon(context, IntegerArgumentType.getInteger(context, "minutes"))))))
                .then(CommandManager.literal("skillTree")
                        .then(CommandManager.literal("setLevel")
                                .then(CommandManager.argument("player", EntityArgumentType.player())
                                        .then(CommandManager.argument("skill", SkillsArgumentType.skills())
                                                .then(CommandManager.argument("level", LongArgumentType.longArg())
                                                        .executes(context -> setSkillLevel(context, EntityArgumentType.getPlayer(context, "player"),
                                                                SkillsArgumentType.getSkill(context, "skill"),
                                                                LongArgumentType.getLong(context, "level")))))))
                        .then(CommandManager.literal("getLevel")
                                .then(CommandManager.argument("player", EntityArgumentType.player())
                                        .then(CommandManager.argument("skill", SkillsArgumentType.skills())
                                                .executes(context -> getSkillLevel(context, EntityArgumentType.getPlayer(context, "player"),
                                                        SkillsArgumentType.getSkill(context, "skill")))))));

        dispatcher.register(literalArgumentBuilder);
    }

    public static int changeDay(CommandContext<ServerCommandSource> commandContext, int day) {
        if (day < 0) {
            commandContext.getSource().sendFeedback(ChatFormatter.stringFormatWithPrefixToText("&c¡Introduce un valor igual o por encima de 0!"), false);
            return -1;
        }
        setDays(commandContext, day);
        GameDateEvents.DayHasElapsedEvent.EVENT.invoker().changeDay(day);
        return 1;
    }

    public static int endBloodMoon(CommandContext<ServerCommandSource> commandContext) {
        var bloodMoonManager = TheAbyssManager.getInstance().serverCore().serverGameManager().bloodMoonManager();

        if (bloodMoonManager.isActive()) {
            bloodMoonManager.end();
            commandContext.getSource().sendFeedback(ChatFormatter.stringFormatWithPrefixToText("&7La BloodMoon ha sido desactivada."), false);
            return 1;
        } else {
            commandContext.getSource().sendFeedback(ChatFormatter.stringFormatWithPrefixToText("&cNo hay una BloodMoon activa."), false);
            return -1;
        }
    }

    public static int startBloodMoon(CommandContext<ServerCommandSource> commandContext, int duration) {
        var bloodMoonManager = TheAbyssManager.getInstance().serverCore().serverGameManager().bloodMoonManager();

        bloodMoonManager.start(duration / 60f, !bloodMoonManager.isActive());
        commandContext.getSource().sendFeedback(ChatFormatter.stringFormatWithPrefixToText("&7La BloodMoon ha sido activada con una duración de &b" + duration + " &7minutos."), false);
        return 1;
    }

    public static int setSkillLevel(CommandContext<ServerCommandSource> commandContext, ServerPlayerEntity player, Skills skill, long level) {
        try {
            var playerSkills = Skills.skillData().getPlayerSkills();
            var uuid = player.getUuid();

            if (playerSkills.containsKey(uuid)) {
                var skillMap = playerSkills.get(uuid);

                skillMap.put(skill.name(), level);
            } else {
                var skillMap = new HashMap<String, Long>();

                skillMap.put(skill.name(), level);
                playerSkills.put(uuid, skillMap);
            }

            commandContext.getSource().sendFeedback(ChatFormatter.stringFormatWithPrefixToText("&7El nivel del jugador &6" + player.getName().asString() + " &7para la habilidad &6" + skill.name() + " &7ha sido actualizado a &6" + level + "&7."), false);

            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public static int getSkillLevel(CommandContext<ServerCommandSource> commandContext, ServerPlayerEntity player, Skills skill) {
        try {
            var playerSkills = Skills.skillData().getPlayerSkills();
            var uuid = player.getUuid();

            if (playerSkills.containsKey(uuid)) {
                var skillMap = playerSkills.get(player.getUuid());

                commandContext.getSource().sendFeedback(ChatFormatter.stringFormatWithPrefixToText("&7El nivel del jugador &6" + player.getName().asString()  + " &7para la habilidad &6" + skill.name() + " &7es &6" + skillMap.get(skill.name()) + "&7."), false);
            } else {
                commandContext.getSource().sendFeedback(ChatFormatter.stringFormatWithPrefixToText("&7El jugador &6" + player.getName().asString() + " &7no ha desbloqueado la habilidad &6" + skill.name() + "&7."), false);
            }

            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private static void setDays(CommandContext<ServerCommandSource> commandContext, int date) {
        int numberOfDay;

        try {
            numberOfDay = Math.max(0, Math.min(60, date));
        } catch (NumberFormatException ex) {
            commandContext.getSource().sendFeedback(ChatFormatter.stringFormatWithPrefixToText("&cNecesitas ingresar un número válido."), false);
            return;
        }

        var add = LocalDate.now().minusDays(numberOfDay);
        var month = add.getMonthValue();
        var day = add.getDayOfMonth();

        String s;

        if (month < 10) {
            s = add.getYear() + "-0" + month + "-";
        } else {
            s = add.getYear() + "-" + month + "-";
        }

        if (day < 10) {
            s = s + "0" + day;
        } else {
            s = s + day;
        }

        commandContext.getSource().sendFeedback(ChatFormatter.stringFormatWithPrefixToText("&7El día ha sido actualizado a &6" + numberOfDay + "&7."), false);
        TheAbyssManager.getInstance().serverCore().serverGameManager().gameData().setStartDate(LocalDate.parse(s));
    }

}
