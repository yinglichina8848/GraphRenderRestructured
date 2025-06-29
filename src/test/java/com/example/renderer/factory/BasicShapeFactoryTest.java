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
    private static final int TEST_X1 = 10, TEST_Y1 = 20;
    private static final int TEST_X2 = 30, TEST_Y2 = 40;
    private static final int TEST_X3 = 50, TEST_Y3 = 60;

    @BeforeEach
    void setUp() {
        factory = new BasicShapeFactory();
    }

    @Test
    void testCreateCircle() {
        Circle circle = factory.createCircle(TEST_X, TEST_Y, TEST_DIMENSION);
        assertNotNull(circle);
        assertEquals(Circle.class, circle.getClass());
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
    }

    @Test
    void testCreateTriangle() {
        Triangle triangle = factory.createTriangle(TEST_X1, TEST_Y1, TEST_X2, TEST_Y2, TEST_X3, TEST_Y3);
        assertNotNull(triangle);
        assertEquals(Triangle.class, triangle.getClass());
    }

    @Test
    void testCreateTriangle_withNegativeCoordinate_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createTriangle(-1, TEST_Y1, TEST_X2, TEST_Y2, TEST_X3, TEST_Y3);
        });
    }

    @Test
    void testCreateEllipse() {
        Ellipse ellipse = factory.createEllipse(TEST_X, TEST_Y, TEST_DIMENSION, TEST_DIMENSION);
        assertNotNull(ellipse);
        assertEquals(Ellipse.class, ellipse.getClass());
    }
}
