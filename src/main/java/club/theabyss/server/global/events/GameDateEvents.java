package club.theabyss.server.global.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public class GameDateEvents {

    @FunctionalInterface
    public interface DayHasElapsedEvent {
        Event<GameDateEvents.DayHasElapsedEvent> EVENT = EventFactory.createArrayBacked(GameDateEvents.DayHasElapsedEvent.class,
                (listeners) -> (day) -> {
                    for (GameDateEvents.DayHasElapsedEvent listener : listeners) {
                        ActionResult actionResult = listener.changeDay(day);

                        if (actionResult != ActionResult.PASS) {
                            return actionResult;
                        }
                    }
                    return ActionResult.PASS;
                });
        ActionResult changeDay(long day);
    }

}
