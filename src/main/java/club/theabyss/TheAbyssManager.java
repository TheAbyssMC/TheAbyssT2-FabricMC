package club.theabyss;

import club.theabyss.client.TheAbyssClientManager;
import club.theabyss.global.registers.CommandRegister;
import club.theabyss.server.TheAbyssServerManager;
import club.theabyss.global.data.adapters.LocalDateSerializer;
import club.theabyss.server.game.ServerGameManager;
import club.theabyss.server.global.commands.arguments.SkillsArgumentType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class TheAbyssManager implements ModInitializer {

	private static @Getter TheAbyssManager instance;

	private static final @Getter Logger logger = LoggerFactory.getLogger("theabyss2");

	private TheAbyssServerManager serverCore;
	private TheAbyssClientManager clientManager;

	private static final Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
			.setPrettyPrinting()
			.serializeNulls()
			.create();

	@Override
	public void onInitialize() {
		instance = this;

		this.serverCore = new TheAbyssServerManager(this);
		this.clientManager = new TheAbyssClientManager();

		CommandRegister.registerCommands();

		ArgumentTypes.register("theabyss2:skills", SkillsArgumentType.class, new ConstantArgumentSerializer<>(SkillsArgumentType::skills));

		getLogger().info("The mod has been enabled successfully.");
	}

	public ServerGameManager serverGameManager() {
		return serverCore.serverGameManager();
	}

	/**
	 * @return the Gson.
	 */
	public static Gson gson() {
		return gson;
	}

	/**
	 * @return the server core.
	 */
	public TheAbyssServerManager serverCore() {
		return serverCore;
	}

}
