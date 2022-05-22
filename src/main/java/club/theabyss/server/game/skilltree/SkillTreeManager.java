package club.theabyss.server.game.skilltree;

import club.theabyss.TheAbyssManager;
import club.theabyss.global.data.util.JsonConfig;
import club.theabyss.global.interfaces.Restorable;
import club.theabyss.server.TheAbyssServerManager;
import club.theabyss.server.game.skilltree.data.SkillTreeData;

public class SkillTreeManager implements Restorable {

    private final TheAbyssServerManager serverManager;

    private SkillTreeData skillTreeData;

    public SkillTreeManager(final TheAbyssServerManager serverManager) {
        this.serverManager = serverManager;

        restore(serverManager.dataManager().skillTree());
    }

    @Override
    public void restore(JsonConfig jsonConfig) {
        if (jsonConfig.getJsonObject().entrySet().isEmpty()) {
            this.skillTreeData = new SkillTreeData();
        } else {
            this.skillTreeData = TheAbyssManager.gson().fromJson(jsonConfig.getJsonObject(), SkillTreeData.class);
        }
    }

    @Override
    public void save(JsonConfig jsonConfig) {
        jsonConfig.setJsonObject(TheAbyssManager.gson().toJsonTree(skillTreeData).getAsJsonObject());
        try {
            jsonConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the skill tree data.
     */
    public SkillTreeData skillTreeData() {
        return skillTreeData;
    }

}
