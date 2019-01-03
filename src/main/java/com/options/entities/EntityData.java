package com.options.entities;

import java.time.format.DateTimeFormatter;

abstract class EntityData {
    transient static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd");
}
