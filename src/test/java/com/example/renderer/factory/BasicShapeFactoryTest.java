package com.example.renderer.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BasicShapeFactoryTest {
    private BasicShapeFactory factory;
    private static final int TEST_X = 10;
    private static final int TEST_Y = 20;
    private static final int TEST_DIMENSION = 30;

    @BeforeEach
    void setUp() {
        factory = new BasicShapeFactory();
    }

    @Test
    void testCreateCircle() {
        Circle circle = factory.createCircle(TEST_X, TEST_Y, TEST_DIMENSION);
        assertNotNull(circle);
        assertEquals(Circle.class, circle.getClass());
        assertEquals(TEST_DIMENSION, circle.getRadius());
    }

    @Test
    void testCreateCircle_withInvalidRadius_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createCircle(TEST_X, TEST_Y, -1);
        });
    }

    @Test
    void testCreateRectangle() {
        Rectangle rectangle = factory.createRectangle(TEST_X, TEST_Y, TEST_DIMENSION, TEST_DIMENSION);
        assertNotNull(rectangle);
        assertEquals(Rectangle.class, rectangle.getClass());
        assertEquals(TEST_DIMENSION, rectangle.getWidth());
        assertEquals(TEST_DIMENSION, rectangle.getHeight());
    }
    
    @Test
    void testCreateRectangle_withNegativeWidth_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createRectangle(TEST_X, TEST_Y, -1, TEST_DIMENSION);
        });
    }
    
    @Test
    void testCreateRectangle_withNegativeHeight_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createRectangle(TEST_X, TEST_Y, TEST_DIMENSION, -1);
        });
    }
}
