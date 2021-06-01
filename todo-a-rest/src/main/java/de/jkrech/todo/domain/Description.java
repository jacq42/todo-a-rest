package de.jkrech.todo.domain;

public class Description {

    private String value;

    public static Description of(String value) {
        return new Description(value);
    }

    private Description(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("Value can't be empty");
        }
        this.value = value;
    }

    public String value() {
        return this.value;
    }
 }
