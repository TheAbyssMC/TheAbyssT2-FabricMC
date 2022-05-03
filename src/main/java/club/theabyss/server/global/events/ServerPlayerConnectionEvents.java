package club.theabyss.server.global.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public class ServerPlayerConnectionEvents {

    public interface OnServerPlayerConnect {
        Event<OnServerPlayerConnect> EVENT = EventFactory.createArrayBacked(OnServerPlayerConnect.class,
                (listeners) -> (player, server) -> {

                    for (OnServerPlayerConnect listener : listeners) {
                        ActionResult actionResult = listener.connect(player, server);

                        if (actionResult != ActionResult.PASS) {
                            return actionResult;
                        }
                    }
                    return ActionResult.PASS;
                });
        ActionResult connect(ServerPlayerEntity player, MinecraftServer server);
    }

    public interface OnServerPlayerDisconnect {
        Event<OnServerPlayerDisconnect> EVENT = EventFactory.createArrayBacked(OnServerPlayerDisconnect.class,
                (listeners) -> (player, server) -> {

            for (OnServerPlayerDisconnect listener : listeners) {
                ActionResult actionResult = listener.disconnect(player, server);

                if (actionResult != ActionResult.PASS) {
                    return actionResult;
                }
            }
            return ActionResult.PASS;
        });
        ActionResult disconnect(ServerPlayerEntity player, MinecraftServer server);
    }

}
