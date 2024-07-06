package club.theabyss.server.game.bloodmoon.types;

import lombok.Getter;
import lombok.Setter;

/**
 * Class designed to hold all the data related to the BloodMoon event.
 */
public class BloodMoonData {

    private @Getter @Setter long endsIn;
    private @Getter @Setter long totalTime;
    private @Getter @Setter long naturalBloodMoonIn;

    public BloodMoonData() {
        this.endsIn = 0;
        this.totalTime = 0;
        this.naturalBloodMoonIn = 0;
    }

    public BloodMoonData(long endsIn, long totalTime, long naturalBloodMoonIn) {
        this.endsIn = endsIn;
        this.totalTime = totalTime;
        this.naturalBloodMoonIn = naturalBloodMoonIn;
    }

}
