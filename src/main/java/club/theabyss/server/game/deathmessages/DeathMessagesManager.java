package club.theabyss.server.game.deathmessages;

import club.theabyss.TheAbyssManager;
import club.theabyss.global.data.util.JsonConfig;
import club.theabyss.global.interfaces.Restorable;
import club.theabyss.server.TheAbyssServerManager;
import club.theabyss.server.data.types.DeathMessages;

public class DeathMessagesManager implements Restorable {

    private final TheAbyssServerManager serverCore;
    private DeathMessages deathMessages;

    public DeathMessagesManager(final TheAbyssServerManager serverCore) {
        this.serverCore = serverCore;

        this.restore(serverCore.dataManager().deathMessages());
    }

    @Override
    public void restore(JsonConfig jsonConfig) {
        if (jsonConfig.getJsonObject().entrySet().isEmpty()) {
            this.deathMessages = new DeathMessages();
        } else {
            this.deathMessages = TheAbyssManager.gson().fromJson(jsonConfig.getJsonObject(), DeathMessages.class);
        }
    }

    @Override
    public void save(JsonConfig jsonConfig) {
        jsonConfig.setJsonObject(TheAbyssManager.gson().toJsonTree(deathMessages).getAsJsonObject());
        try {
            jsonConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return a list of the death messages.
     */
    public DeathMessages deathMessages() {
        return deathMessages;
    }

    /**
     * @return the server manager.
     */
    public TheAbyssServerManager serverManager() {
        return serverCore;
    }

}
