package me.scyphers.scycore.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static me.scyphers.scycore.util.StringUtil.toRoman;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StringUtilTests {

    @Test
    public void toRomanTest() {

        assertEquals("0", toRoman(0));
        assertEquals("I", toRoman(1));
        assertEquals("II", toRoman(2));
        assertEquals("III", toRoman(3));
        assertEquals("IV", toRoman(4));
        assertEquals("V", toRoman(5));
        assertEquals("VI", toRoman(6));
        assertEquals("VII", toRoman(7));
        assertEquals("VIII", toRoman(8));
        assertEquals("IX", toRoman(9));
        assertEquals("X", toRoman(10));
        assertEquals("XI", toRoman(11));
        assertEquals("XII", toRoman(12));
        assertEquals("XIII", toRoman(13));
        assertEquals("XIV", toRoman(14));
        assertEquals("XV", toRoman(15));

    }

    @Test
    public void asReadableTimeTests() {

        // 0 Duration
        assertEquals("0d 00:00:00", StringUtil.asReadableTime(0));

        // Just Seconds
        assertEquals("0d 00:00:01", StringUtil.asReadableTime(1000));
        assertEquals("0d 00:00:05", StringUtil.asReadableTime(5000));
        assertEquals("0d 00:00:10", StringUtil.asReadableTime(10000));
        assertEquals("0d 00:00:30", StringUtil.asReadableTime(30000));

        // Just Minutes
        assertEquals("0d 00:01:00", StringUtil.asReadableTime(60000));
        assertEquals("0d 00:05:00", StringUtil.asReadableTime(300000));
        assertEquals("0d 00:10:00", StringUtil.asReadableTime(600000));
        assertEquals("0d 00:30:00", StringUtil.asReadableTime(1800000));

        // Just Hours
        assertEquals("0d 01:00:00", StringUtil.asReadableTime(3600000));
        assertEquals("0d 05:00:00", StringUtil.asReadableTime(18000000));
        assertEquals("0d 10:00:00", StringUtil.asReadableTime(36000000));
        assertEquals("0d 23:00:00", StringUtil.asReadableTime(82800000));

        // Just Days
        assertEquals("1d 00:00:00", StringUtil.asReadableTime(86400000));
        assertEquals("2d 00:00:00", StringUtil.asReadableTime(172800000));
        assertEquals("5d 00:00:00", StringUtil.asReadableTime(432000000));
        assertEquals("10d 00:00:00", StringUtil.asReadableTime(864000000));

        // All time units
        assertEquals("1d 01:01:01", StringUtil.asReadableTime(90061000)); // 1 day 1 hour 1 minute 1 second
        assertEquals("2d 05:05:05", StringUtil.asReadableTime(191105000)); // 2 days 5 hours 5 minutes 5 seconds
        assertEquals("5d 10:10:10", StringUtil.asReadableTime(468610000)); // 5 days 10 hours 10 minutes 10 seconds
        assertEquals("10d 23:30:30", StringUtil.asReadableTime(948630000)); // 10 days 23 hours 30 minutes 30 seconds

        // Expected Exceptions
        assertThrows(IllegalArgumentException.class, () -> StringUtil.asReadableTime(-1));

    }

    @Test
    public void capitaliseTests() {

        assertEquals("", StringUtil.capitalise(""));
        assertEquals("Hello World", StringUtil.capitalise("hElLo wOrLd"));
        assertEquals("Hello World", StringUtil.capitalise("HELLO WORLD"));

    }

    @Test
    public void joinTests() {

        assertEquals("", StringUtil.join(Collections.emptyList(), ""));
        assertEquals("1, 2, 3, 4", StringUtil.join(Arrays.asList("1", "2", "3", "4"), ", "));
        assertEquals("1234", StringUtil.join(Arrays.asList("1", "2", "3", "4"), ""));
        assertEquals("1-2-3-4", StringUtil.join(Arrays.asList("1", "2", "3", "4"), "-"));

    }

    

}
