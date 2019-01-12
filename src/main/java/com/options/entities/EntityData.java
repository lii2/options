package com.options.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

abstract class EntityData {
    private transient static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd");

    protected LocalDate parseDate(String dateString) {
        if (dateString.length() > 10) {
            return LocalDate.parse(dateString.substring(0, 10), DATE_TIME_FORMATTER);
        } else
            return LocalDate.parse(dateString, DATE_TIME_FORMATTER);
    }
}
