package com.example.renderer.factory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class ShapeValidatorTest {
    @Test
    void testValidatePosition_validInput_noException() {
        assertDoesNotThrow(() -> {
            ShapeValidator.validatePosition(10, 20);
        });
    }
    
    @Test
    void testValidatePosition_zeroX_zeroY() {
        assertDoesNotThrow(() -> {
            ShapeValidator.validatePosition(0, 0);
        });
    }
    
    @Test
    void testValidatePosition_zeroX_positiveY() {
        assertDoesNotThrow(() -> {
            ShapeValidator.validatePosition(0, 20);
        });
    }
    
    @Test
    void testValidatePosition_positiveX_zeroY() {
        assertDoesNotThrow(() -> {
            ShapeValidator.validatePosition(10, 0);
        });
    }
    
    @Test
    void testValidatePosition_withNegativeX_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ShapeValidator.validatePosition(-1, 20);
        });
    }
    
    @Test
    void testValidatePosition_withNegativeY_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            ShapeValidator.validatePosition(10, -1);
        });
    }
}
