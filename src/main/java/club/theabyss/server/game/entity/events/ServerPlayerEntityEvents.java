package club.theabyss.server.game.entity.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public class ServerPlayerEntityEvents {

    @FunctionalInterface
    public interface PlayerDeath {
        Event<PlayerDeath> EVENT = EventFactory.createArrayBacked(PlayerDeath.class,
                (listeners) -> (player, source) -> {
                    for (PlayerDeath listener : listeners) {
                        ActionResult actionResult = listener.die(player, source);

                        if (actionResult != ActionResult.PASS) {
                            return actionResult;
                        }
                    }
                    return ActionResult.PASS;
                });
        ActionResult die(ServerPlayerEntity entity, DamageSource source);
    }

}
