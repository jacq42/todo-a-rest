package de.jkrech.todo.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

class DescriptionTest {

    @ParameterizedTest(name = "invalid: \"{0}\"")
    @ValueSource(strings = {"", " "} )
    @NullSource
    void invalidValuesShouldThrowAnException(String value) {
        assertThrows(IllegalArgumentException.class, () -> Description.of(value));
    }

    @Test
    void createValidDescription() {
        // given
        String descriptionValue = "Lorem ipsum";

        // when
        Description description = Description.of(descriptionValue);

        // then
        assertEquals(descriptionValue, description.value());
    }

}
