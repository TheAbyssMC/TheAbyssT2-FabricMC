package club.theabyss.global.registers;

import club.theabyss.TheAbyssManager;
import club.theabyss.server.global.commands.AbyssCMD;
import club.theabyss.server.global.commands.AbyssStaffCMD;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class CommandRegister {

    public static void registerCommands() {
        CommandRegistrationCallback.EVENT.register(AbyssCMD::register);
        CommandRegistrationCallback.EVENT.register(AbyssStaffCMD::register);

        TheAbyssManager.getLogger().info("The commands have been registered successfully.");
    }

}
