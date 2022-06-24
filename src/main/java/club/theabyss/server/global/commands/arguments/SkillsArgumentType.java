package club.theabyss.server.global.commands.arguments;

import club.theabyss.server.game.skilltree.enums.Skills;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.util.Arrays;
import java.util.Collection;

public class SkillsArgumentType implements ArgumentType<Skills> {

    private static final Collection<String> EXAMPLES = Arrays.asList(Skills.Health.name(), Skills.Resistance.name(), Skills.Strength.name());

    public static SkillsArgumentType skills() {
        return new SkillsArgumentType();
    }

    @Override
    public Skills parse(StringReader reader) throws CommandSyntaxException {
        int argBeginning = reader.getCursor();
        if (!reader.canRead()) {
            reader.skip();
        }

        while (reader.canRead() && reader.peek() != ' ') {
            reader.skip();
        }

        String substring = reader.getString().substring(argBeginning, reader.getCursor());
        try {
            return Skills.valueOf(substring);
        } catch (Exception ex) {
            throw new SimpleCommandExceptionType(new LiteralText(ex.getMessage())).createWithContext(reader);
        }
    }

    public static Skills getSkill(CommandContext<ServerCommandSource> context, String name) {
        return context.getArgument(name, Skills.class);
    }

    @Override
    public Collection<String> getExamples() {
        return EXAMPLES;
    }

}