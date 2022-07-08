package club.theabyss.client;

import club.theabyss.client.global.listeners.GlobalClientListeners;
import net.fabricmc.api.ClientModInitializer;

public class TheAbyssClientManager implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        GlobalClientListeners.init();
    }

}
