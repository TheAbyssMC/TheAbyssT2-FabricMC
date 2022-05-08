package club.theabyss.global.utils.timedTitle;

public class InvalidTitleTimings extends Exception {
    InvalidTitleTimings(TimedTitle.Title title) {
        super("The timings of the title \"" + title.title + "\" (with a subtitle of \"" + title.subtitle + "\") are invalid. Timings:\nFade in: " + title.fadeIn + "\nStay time: " + title.stayTime + "\nMinimum stay time: " + title.minimumStayTime + "\nFade out: " + title.fadeOut);
    }
}
