package club.theabyss.server.game.deathmessages;

import club.theabyss.TheAbyss;
import club.theabyss.global.data.util.JsonConfig;
import club.theabyss.global.interfaces.server.data.Restorable;
import club.theabyss.server.TheAbyssServer;
import club.theabyss.server.data.storage.DeathMessages;

public class DeathMessagesManager implements Restorable {

    private final TheAbyssServer theAbyssServer;
    private DeathMessages deathMessages;

    public DeathMessagesManager(final TheAbyssServer theAbyssServer) {
        this.theAbyssServer = theAbyssServer;

        this.restore(theAbyssServer.dataManager().deathMessages());
    }

    @Override
    public void restore(JsonConfig jsonConfig) {
        if (jsonConfig.getJsonObject().entrySet().isEmpty()) {
            this.deathMessages = new DeathMessages();
        } else {
            this.deathMessages = TheAbyss.gson().fromJson(jsonConfig.getJsonObject(), DeathMessages.class);
        }
    }

    @Override
    public void save(JsonConfig jsonConfig) {
        jsonConfig.setJsonObject(TheAbyss.gson().toJsonTree(deathMessages).getAsJsonObject());
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
     * @return the mod's server manager.
     */
    public TheAbyssServer theAbyssServer() {
        return theAbyssServer;
    }

}
