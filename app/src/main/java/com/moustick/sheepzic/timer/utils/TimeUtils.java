package com.moustick.sheepzic.timer.utils;

public class TimeUtils {

    public static int toMillis(int hours, int minutes, int seconds) {
        return seconds * 1000 + minutes * 60 * 1000 + hours * 60 * 60 * 1000;
    }

    public static int toHours(long millis) {
        return (int) ((millis / (1000 * 60 * 60)) % 24);
    }

    public static int toMinutes(long millis) {
        return (int) ((millis / (1000 * 60)) % 60);
    }

    public static int toSeconds(long millis) {
        return (int) ((millis / 1000) % 60);
    }

    public static String format(int hours, int minutes, int seconds) {
        String res = "";
        if (hours > 0)
            res += hours + " h ";
        if (minutes > 0)
            res += minutes + " min";
        return res;
    }

    public static String format(long millis) {
        return format(toHours(millis), toMinutes(millis), toSeconds(millis));
    }

}
