package club.theabyss.server.data.storage;

import club.theabyss.server.game.bloodmoon.types.BloodMoonData;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class GameData {

    private @Setter LocalDate startDate;

    private final BloodMoonData bloodMoonData;

    public GameData(BloodMoonData bloodMoonData, LocalDate startDate) {
        this.bloodMoonData = bloodMoonData;
        this.startDate = startDate;
    }

    public GameData(BloodMoonData bloodMoonData) {
        this(bloodMoonData, LocalDate.now());
    }

    /**
     * @return the day of the Game.
     */
    public long day() {
        return ChronoUnit.DAYS.between(startDate, LocalDate.now());
    }

    /**
     * @return the BloodMoonData instance.
     */
    public BloodMoonData bloodMoonData() {
        return this.bloodMoonData;
    }

}
