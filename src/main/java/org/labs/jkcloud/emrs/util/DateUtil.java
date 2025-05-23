package org.labs.jkcloud.emrs.util;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class DateUtil {


    public static String formatWithSuffix(LocalDate date) {
        int day = date.getDayOfMonth();
        String suffix = getDaySuffix(day);
        String month = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        return String.format("%d%s %s %d", day, suffix, month, date.getYear());
    }

    private static String getDaySuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        return switch (day % 10) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };
    }
}
