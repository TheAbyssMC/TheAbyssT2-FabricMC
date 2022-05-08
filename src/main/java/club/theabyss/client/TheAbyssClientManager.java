package club.theabyss.client;

import club.theabyss.global.utils.customGlyphs.Animation;
import club.theabyss.global.utils.customGlyphs.NoFramesException;
import net.fabricmc.api.ClientModInitializer;

public class TheAbyssClientManager implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        for (String animation : new String[] {
                "bloodmoonanimation.json"
        }) {
            try {
                Animation.load(animation);
            } catch (NoFramesException e) {
                e.printStackTrace();
            }
        }
    }

}
