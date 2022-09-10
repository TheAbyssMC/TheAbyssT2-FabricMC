package club.theabyss;

import club.theabyss.client.TheAbyssClient;
import club.theabyss.global.data.adapters.LocalDateSerializer;
import club.theabyss.global.registers.CommandRegistries;
import club.theabyss.global.sounds.TheAbyssSoundEvents;
import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.TheAbyssServer;
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

public class TheAbyss implements ModInitializer {

	private static @Getter TheAbyss instance;

	private static final @Getter Logger logger = LoggerFactory.getLogger(TheAbyssConstants.MOD_ID);

	private TheAbyssServer serverManager;
	private TheAbyssClient clientManager;

	private static final Gson gson = new GsonBuilder()
			.registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
			.setPrettyPrinting()
			.serializeNulls()
			.create();

	@Override
	public void onInitialize() {
		instance = this;

		this.serverManager = new TheAbyssServer(this);
		this.clientManager = new TheAbyssClient();

		CommandRegistries.registerCommands();

		GlobalServerListeners.init();
		BloodMoonListeners.init();

		TheAbyssSoundEvents.register();
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
	public TheAbyssServer serverManager() {
		return serverManager;
	}

	/**
	 * @return the mod's client manager.
	 */
	public TheAbyssClient clientManager() {
		return clientManager;
	}

}
