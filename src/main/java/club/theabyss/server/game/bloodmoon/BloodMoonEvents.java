package club.theabyss.server.game.bloodmoon;

import club.theabyss.server.game.bloodmoon.BloodMoonManager;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

public class BloodMoonEvents {

    public interface BloodMoonStarted {
        Event<BloodMoonStarted> EVENT = EventFactory.createArrayBacked(BloodMoonStarted.class,
                (listeners) -> (bloodMoon) -> {
            for (BloodMoonStarted listener : listeners) {
                ActionResult result = listener.start(bloodMoon);

                if (result != ActionResult.PASS) {
                    return result;
                }
            }
            return ActionResult.PASS;
        });

        ActionResult start(BloodMoonManager bloodMoonManager);
    }

    public interface BloodMoonEnded {
        Event<BloodMoonEnded> EVENT = EventFactory.createArrayBacked(BloodMoonEnded.class,
                (listeners) -> (bloodMoon) -> {
                    for (BloodMoonEnded listener : listeners) {
                        ActionResult result = listener.end(bloodMoon);

                        if (result != ActionResult.PASS) {
                            return result;
                        }
                    }
                    return ActionResult.PASS;
                });

        ActionResult end(BloodMoonManager bloodMoonManager);
    }

}
