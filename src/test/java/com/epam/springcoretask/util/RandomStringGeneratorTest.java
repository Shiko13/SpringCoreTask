package com.epam.springcoretask.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RandomStringGeneratorTest {

    @Test
    public void generateRandomString_shouldOk() {
        String randomString = RandomStringGenerator.generateRandomString(10);

        assertEquals(10, randomString.length());
        assertTrue(randomString.matches("^[a-zA-Z0-9]+$"));

        randomString = RandomStringGenerator.generateRandomString(20);

        assertEquals(20, randomString.length());
        assertTrue(randomString.matches("^[a-zA-Z0-9]+$"));
    }
}

