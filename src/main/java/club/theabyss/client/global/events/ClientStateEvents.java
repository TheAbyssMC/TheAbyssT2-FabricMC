package club.theabyss.client.global.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.ActionResult;

public interface ClientStateEvents {

    ActionResult action(MinecraftClient client);

    @FunctionalInterface
    interface OnClientPause extends ClientStateEvents {

        Event<OnClientPause> EVENT = EventFactory.createArrayBacked(OnClientPause.class,
                (listeners) -> (minecraftClient) -> {
                    for (OnClientPause listener : listeners) {
                        ActionResult result = listener.action(minecraftClient);

                        if (result != ActionResult.PASS) {
                            return result;
                        }
                    }
                    return ActionResult.PASS;
                });
    }

    @FunctionalInterface
    interface OnClientResume extends ClientStateEvents {

        Event<OnClientResume> EVENT = EventFactory.createArrayBacked(OnClientResume.class,
                (listeners) -> (minecraftClient) -> {
                    for (OnClientResume listener : listeners) {
                        ActionResult result = listener.action(minecraftClient);

                        if (result != ActionResult.PASS) {
                            return result;
                        }
                    }
                    return ActionResult.PASS;
                });
    }

}
