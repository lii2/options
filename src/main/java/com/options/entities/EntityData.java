package com.options.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

abstract class EntityData {
    private transient static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd");

    protected LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString.substring(0, 10), DATE_TIME_FORMATTER);
    }
}
