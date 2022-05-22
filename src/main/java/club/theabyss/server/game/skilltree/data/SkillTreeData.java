package club.theabyss.server.game.skilltree.data;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillTreeData {

    private final @Getter Map<UUID, Map<String, Long>> playerSkills;

    public SkillTreeData(Map<UUID, Map<String, Long>> playerSkills) {
        this.playerSkills = playerSkills;
    }

    public SkillTreeData() {
        this(new HashMap<>());
    }

}
