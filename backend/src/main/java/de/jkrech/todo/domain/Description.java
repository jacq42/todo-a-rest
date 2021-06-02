package de.jkrech.todo.domain;

import static java.util.Optional.ofNullable;

public class Description {

    private String value;

    public static Description of(String value) {
        return new Description(value);
    }

    private Description(String value) {
        String validDescription = ofNullable(value)
                .filter(v -> !v.isBlank())
                .orElseThrow(() -> new IllegalArgumentException("Value can't be empty"));
        this.value = validDescription;
    }

    public String value() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value();
    }
 }
