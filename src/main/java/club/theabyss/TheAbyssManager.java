package club.theabyss;

import club.theabyss.client.TheAbyssClientManager;
import club.theabyss.global.registers.CommandRegistries;
import club.theabyss.server.TheAbyssServerManager;
import club.theabyss.global.data.adapters.LocalDateSerializer;
import club.theabyss.server.game.ServerGameManager;
import club.theabyss.server.game.bloodmoon.listeners.BloodMoonListeners;
import club.theabyss.server.game.entity.entities.AbyssEntityRegistries;
import club.theabyss.server.global.commands.arguments.SkillsArgumentType;
import club.theabyss.server.global.listeners.GlobalServerListeners;
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

	private TheAbyssServerManager serverManager;
	private TheAbyssClientManager clientManager;

	private static final Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
			.setPrettyPrinting()
			.serializeNulls()
			.create();

	@Override
	public void onInitialize() {
		instance = this;

		this.serverManager = new TheAbyssServerManager(this);
		this.clientManager = new TheAbyssClientManager();

		CommandRegistries.registerCommands();

		GlobalServerListeners.init();
		BloodMoonListeners.init();

		AbyssEntityRegistries.register();

		ArgumentTypes.register("theabyss2:skills", SkillsArgumentType.class, new ConstantArgumentSerializer<>(SkillsArgumentType::skills));

		logger.info("The mod has been enabled successfully.");
	}

	/**
	 * @return the mod's server game manager.
	 */
	public ServerGameManager serverGameManager() {
		return serverManager.serverGameManager();
	}

	/**
	 * @return the Gson constant.
	 */
	public static Gson gson() {
		return gson;
	}

	/**
	 * @return the mod's server manager.
	 */
	public TheAbyssServerManager serverManager() {
		return serverManager;
	}

	/**
	 * @return the mod's client manager.
	 */
	public TheAbyssClientManager clientManager() {
		return clientManager;
	}

}
