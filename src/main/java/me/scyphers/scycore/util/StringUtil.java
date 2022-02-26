package me.scyphers.scycore.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Locale;
import java.util.TreeMap;

public class StringUtil {

    private final static TreeMap<Integer, String> map = new TreeMap<>();

    static {
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }

    /**
     * Converts a given integer to its roman form.
     * @author Ben-Hur Langoni Junior - https://stackoverflow.com/questions/12967896/converting-integers-to-roman-numerals-java
     */
    public static String toRoman(int number) {
        if (number == 0) {
            return "0";
        }

        int l =  map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number-l);
    }

    /**
     * Creates a human-readable form of a given duration.<br><br>
     * Format is DAYSd HOURS:MINUTES:SECONDS<br>
     * @param durationMillis the duration to format, in milliseconds
     * @return the formatted duration in a human-readable time format
     */
    @Contract(pure = true)
    public static @NotNull String asReadableTime(@Range(from = 0, to = Long.MAX_VALUE) long durationMillis) {

        // Calculate seconds
        long seconds = durationMillis / 1000;
        long limitedSeconds = seconds % 60;

        // Calculate minutes
        long minutes = seconds / 60;
        long limitedMinutes = minutes % 60;

        // Calculate hours
        long hours = minutes / 60;
        long limitedHours = hours % 24;

        // Calculate days
        long days = hours / 24;

        // No further calculations required, compose the string
        String secondsFormatted = limitedSeconds - 10 >= 0 ? "" + limitedSeconds : "0" + limitedSeconds;
        String minutesFormatted = limitedMinutes - 10 >= 0 ? "" + limitedMinutes : "0" + limitedMinutes;
        String hoursFormatted = limitedHours - 10 >= 0 ? "" + limitedHours : "0" + limitedHours;
        String daysFormatted = "" + days;

        return daysFormatted + "d " + hoursFormatted + ":" + minutesFormatted + ":" + secondsFormatted;

    }

    public static @NotNull String capitalise(@NotNull String s) {

        StringBuilder builder = new StringBuilder();

        String[] words = s.split(" ");
        for (String word : words) {
            if (word.length() == 0) continue;
            builder.append(Character.toUpperCase(word.charAt(0)));
            if (word.length() <= 1) continue;
            builder.append(word.substring(1).toLowerCase(Locale.ROOT)).append(" ");
        }

        if (builder.length() != 0) builder.delete(builder.length() - 1, builder.length());
        return builder.toString();
    }

    public static @NotNull String join(@NotNull Iterable<String> strings, @NotNull String joiner) {

        StringBuilder builder = new StringBuilder();

        for (String s : strings) {
            builder.append(s).append(joiner);
        }

        builder.delete(builder.length() - joiner.length(), builder.length());

        return builder.toString();

    }

}
