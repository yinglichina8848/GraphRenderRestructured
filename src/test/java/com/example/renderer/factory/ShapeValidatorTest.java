package com.example.renderer.factory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.awt.Point;

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
    
    @ParameterizedTest
    @ValueSource(ints = {0, -1, -10})
    void testValidateCircleRadius_invalid(int radius) {
        assertThrows(IllegalArgumentException.class, 
            () -> ShapeValidator.validateCircle(radius)
        );
    }
    
    @Test
    void testValidateRectangleDimensions() {
        assertDoesNotThrow(() -> 
            ShapeValidator.validateRectangle(10, 20)
        );
        assertThrows(IllegalArgumentException.class, () -> 
            ShapeValidator.validateRectangle(-10, 20)
        );
        assertThrows(IllegalArgumentException.class, () -> 
            ShapeValidator.validateRectangle(10, -20)
        );
    }
    
    @Test
    void testValidateEllipseAxes() {
        assertThrows(IllegalArgumentException.class, () -> 
            ShapeValidator.validateEllipse(-10, 20)
        );
        assertThrows(IllegalArgumentException.class, () -> 
            ShapeValidator.validateEllipse(10, -20)
        );
    }
    
    @Test
    void testValidateTrianglePoints() {
        Point[] validPoints = {
            new Point(0,0), new Point(10,0), new Point(5,10)
        };
        assertDoesNotThrow(() -> 
            ShapeValidator.validateTriangle(validPoints)
        );
        
        Point[] colinearPoints = {
            new Point(0,0), new Point(5,5), new Point(10,10)
        };
        assertThrows(IllegalArgumentException.class, () -> 
            ShapeValidator.validateTriangle(colinearPoints)
        );
    }
}
