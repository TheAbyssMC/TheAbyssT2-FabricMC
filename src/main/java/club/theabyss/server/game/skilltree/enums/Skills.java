package club.theabyss.server.game.skilltree.enums;

import club.theabyss.TheAbyssManager;
import club.theabyss.server.game.skilltree.data.SkillTreeData;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

public enum Skills {

    Health(3),
    Resistance(3),
    Strength(3);

    public final int day;
    public final List<String> lore;

    Skills(int day, List<String> lore) {
        this.day = day;
        this.lore = lore;
    }

    Skills(int day) {
        this(day, new ArrayList<>());
    }

    public long getPrice(ServerPlayerEntity player) {
        return getPrice(getLevel(player));
    }

    public static long getPrice(long level) {
        return (level * 2) + 1;
    }

    public long getLevel(ServerPlayerEntity player) {
        var uuid = player.getUuid();
        var playerSkills = skillData().getPlayerSkills();

        return (playerSkills.containsKey(uuid) && playerSkills.get(uuid).containsKey(this.toString())) ? playerSkills.get(player.getUuid()).get(this.toString()) : 0;
    }

    public boolean isAvailable() {
        return TheAbyssManager.getInstance().serverGameManager().day() >= day;
    }

    public static SkillTreeData skillData() {
        return TheAbyssManager.getInstance().serverCore().skillTreeManager().skillTreeData();
    }

}
