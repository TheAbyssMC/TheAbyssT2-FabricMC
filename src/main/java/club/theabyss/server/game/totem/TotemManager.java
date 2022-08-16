package club.theabyss.server.game.totem;

import club.theabyss.TheAbyssManager;
import club.theabyss.global.data.util.JsonConfig;
import club.theabyss.global.interfaces.server.data.Restorable;
import club.theabyss.server.game.ServerGameManager;
import club.theabyss.server.game.totem.data.TotemData;

public class TotemManager implements Restorable {

    public final ServerGameManager gameManager;

    public TotemData totemData;


    public TotemManager(final ServerGameManager gameManager) {
        this.gameManager = gameManager;

        this.restore(gameManager.serverManager().dataManager().totems());
    }

    @Override
    public void restore(JsonConfig jsonConfig) {
        if (jsonConfig.getJsonObject().entrySet().isEmpty()) {
            this.totemData = new TotemData();
        } else {
            this.totemData = TheAbyssManager.gson().fromJson(jsonConfig.getJsonObject(), TotemData.class);
        }
    }

    @Override
    public void save(JsonConfig jsonConfig) {
        jsonConfig.setJsonObject(TheAbyssManager.gson().toJsonTree(totemData).getAsJsonObject());
        try {
            jsonConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the mod's totem data class instance.
     */
    public TotemData totemData() {
        return totemData;
    }

}
