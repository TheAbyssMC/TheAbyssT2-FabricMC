package club.theabyss.server.global.utils.timedTitle;

public class InvalidTitleTimings extends Exception {
    InvalidTitleTimings(TimedTitle.Title title) {
        super("The timings of the title \"" + title.title + "\" (with a subtitle of \"" + title.subtitle + "\") are invalid. Timings:\nFade in: " + title.fadeIn + "\nStay time: " + title.stayTime + "\nMinimum stay time: " + title.minimumStayTime + "\nFade out: " + title.fadeOut);
    }

    InvalidTitleTimings(TimedActionBar.ActionBar actionBar) {
        super("The timings of the action bar \"" + actionBar.text + "\" are invalid. Timings:\nFade in: 0 (Minecraft default, can't be changed)\nStay time: 50 (Minecraft default, can't be changed)\nMinimum stay time: " + actionBar.stayTime + "\nFade out: 10 (Minecraft default, can't be changed)");
    }
}
