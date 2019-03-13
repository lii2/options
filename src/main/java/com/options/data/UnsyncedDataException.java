package com.options.data;

public class UnsyncedDataException extends RuntimeException {

    public UnsyncedDataException() {
        super();
    }

    public UnsyncedDataException(String message) {
        super(message);
    }
}
