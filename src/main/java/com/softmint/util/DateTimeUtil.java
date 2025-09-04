package com.softmint.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Parses a date string to LocalDateTime at the start of the day (00:00:00).
     */
    public static LocalDateTime parseStartOfDay(String date) {
        if (date == null || date.isBlank()) return null;
        return LocalDate.parse(date, DATE_FORMATTER).atStartOfDay();
    }

    /**
     * Parses a date string to LocalDateTime at the end of the day (23:59:59.999999999).
     */
    public static LocalDateTime parseEndOfDay(String date) {
        if (date == null || date.isBlank()) return null;
        LocalDate localDate = LocalDate.parse(date, DATE_FORMATTER);
        return localDate.atTime(LocalTime.of(23, 59, 59, 999_999_999));
    }
}

