package org.kauazs.utils;

import java.time.Duration;

public class TimeFormat {
    public static String formatTime(long start, long end) {
        Duration duration = Duration.ofMillis(end - start);

        long minutes = duration.toMinutes();
        long seconds = duration.minusMinutes(minutes).getSeconds();

        return String.format("%d Minutos %d segundos", minutes, seconds);
    }

}
