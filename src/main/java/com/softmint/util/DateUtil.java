package com.softmint.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    /**
     * Converts an ISO 8601 date string to a java.util.Date object.
     *
     * @param isoDateString the ISO 8601 date string (e.g., "2024-10-07T10:30:00Z")
     * @return the corresponding Date object
     * @throws IllegalArgumentException if the input string is not a valid ISO date
     */
    public static Date convertIsoStringToDate(String isoDateString) {
        try {
            if (isoDateString == null) return null;
            // Parse the ISO date string into a ZonedDateTime
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(isoDateString, DateTimeFormatter.ISO_DATE_TIME);
            // Convert ZonedDateTime to java.util.Date
            return Date.from(zonedDateTime.toInstant());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid ISO date string: " + isoDateString, e);
        }
    }

    // Alternative method for local date without time zone
    public static Date convertIsoStringToDateWithoutTimezone(String isoDateString) {
        try {
            // Parse the ISO date string into a Date
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(isoDateString + "Z", DateTimeFormatter.ISO_DATE_TIME);
            // Convert ZonedDateTime to java.util.Date
            return Date.from(zonedDateTime.toInstant());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid ISO date string: " + isoDateString, e);
        }
    }

    void getDatePart(Date date) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String datePart = sdf.format(now);
        System.out.println("Date Part: " + datePart);
    }

    public static Date getDateFromString(String dateStr) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(dateStr);
            System.out.println("Date: " + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * parses a date string, adds 1 day, then converts back to string
     */
    public static String addOneDay(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return dateStr;
        }
        LocalDate parsed = LocalDate.parse(dateStr, DATE_FORMAT);
        LocalDate plusOne = parsed.plusDays(1);
        return plusOne.format(DATE_FORMAT);
    }
}

