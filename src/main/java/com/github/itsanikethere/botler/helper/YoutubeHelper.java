package com.github.itsanikethere.botler.helper;

import java.util.concurrent.TimeUnit;

public class YoutubeHelper {

    private YoutubeHelper() {
    }

    public static String preprocessQuery(String query) {
        return isUrl(query) ? query : "ytsearch:" + query;
    }

    public static String buildThumbnailUrl(String trackUri) {
        return "https://img.youtube.com/vi/" + trackUri.substring(trackUri.indexOf("v=") + 2) + "/0.jpg";
    }

    public static String formatDuration(long milliseconds) {
        long days = TimeUnit.MILLISECONDS.toDays(milliseconds);
        milliseconds -= TimeUnit.DAYS.toMillis(days);

        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
        milliseconds -= TimeUnit.HOURS.toMillis(hours);

        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes);

        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);

        if (days > 0) {
            return String.format("%02d:%02d:%02d:%02d", days, hours, minutes, seconds);
        } else if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%02d:%02d", minutes, seconds);
        } else {
            return String.format("%02d", seconds);
        }
    }

    private static boolean isUrl(String input) {
        return input.startsWith("http");
    }

}
