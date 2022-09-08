package club.theabyss.server.data.storage;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DeathMessages {

    private final Map<UUID, String> deathMessages;

    public DeathMessages() {
        this.deathMessages = new HashMap<>();
    }

    public DeathMessages(HashMap<UUID, String> deathMessages) {
        this.deathMessages = deathMessages;
    }

    /**
     * @return the death messages.
     */
    public Map<UUID, String> deathMessages() {
        return deathMessages;
    }

    public String deathMessage(ServerPlayerEntity player) {
        return deathMessages.containsKey(player.getUuid()) ? deathMessages.get(player.getUuid()) : defaultMessage();
    }

    /**
     * @return the default death message.
     */
    public String defaultMessage() {
        return "Nessuno puo sfuggire al destino scelto...";
    }

}
