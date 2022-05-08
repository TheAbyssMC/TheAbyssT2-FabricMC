package club.theabyss.global.utils.timedTitle;

public class MinimumStayTimeIsGreaterThatStayTime extends Exception {
    MinimumStayTimeIsGreaterThatStayTime(TimedTitle.Title title) {
        super("The title \"" + title.title + "\" (with a subtitle of \"" + title.subtitle + "\", a fadeIn of " + title.fadeIn + " and a fadeOut of " + title.fadeOut + ") has been initialized with a stayTime of " + title.stayTime + " ticks and a minimumStayTime of " + title.minimumStayTime);
    }
}
