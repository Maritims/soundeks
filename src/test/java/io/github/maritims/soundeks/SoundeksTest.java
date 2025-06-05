package io.github.maritims.soundeks;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SoundeksTest {

    public static Stream<Arguments> transform() {
        return Stream.of(
                Arguments.of("Robert", "R163"),
                Arguments.of("Rupert", "R163"),
                Arguments.of("Rubin", "R150"),
                Arguments.of("Ashcraft", "A261"),
                Arguments.of("Ashcroft", "A261"),
                Arguments.of("Tymzcak", "T522"),
                Arguments.of("Pfister", "P236"),
                Arguments.of("Honeyman", "H555")
        );
    }

    @ParameterizedTest
    @MethodSource
    void transform(String original, String expectedResult) {
        // arrange
        var sut = new Soundeks();

        // act
        var result = sut.transform(original);

        // assert
        assertEquals(expectedResult, result);
    }
}