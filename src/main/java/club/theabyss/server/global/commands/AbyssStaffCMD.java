package club.theabyss.server.global.commands;

import club.theabyss.TheAbyssManager;
import club.theabyss.global.utils.chat.ChatFormatter;
import club.theabyss.server.global.events.GameDateEvents;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.spongepowered.asm.mixin.injection.selectors.TargetSelector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.stream.Stream;

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
                                        .executes(context -> startBloodMoon(context, IntegerArgumentType.getInteger(context, "minutes"))))));

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
