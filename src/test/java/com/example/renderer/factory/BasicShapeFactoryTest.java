package com.example.renderer.factory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.awt.Point;

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
        assertEquals(new Point(TEST_X, TEST_Y), circle.getPosition());
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
        assertEquals(new Point(TEST_X, TEST_Y), rectangle.getPosition());
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

    @Test
    void testCreateTriangle() {
        Triangle triangle = factory.createTriangle(TEST_X1, TEST_Y1, TEST_X2, TEST_Y2, TEST_X3, TEST_Y3);
        assertNotNull(triangle);
        assertEquals(Triangle.class, triangle.getClass());
        List<Point> expected = Arrays.asList(
            new Point(TEST_X1, TEST_Y1),
            new Point(TEST_X2, TEST_Y2),
            new Point(TEST_X3, TEST_Y3)
        );
        assertIterableEquals(expected, triangle.getPoints());
    }

    @Test
    void testCreateTriangle_withNegativeCoordinate_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createTriangle(-1, TEST_Y1, TEST_X2, TEST_Y2, TEST_X3, TEST_Y3);
        });
    }
    
    @Test
    void testCreateTriangle_withDuplicatePoints() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createTriangle(10, 10, 20, 20, 10, 10);
        });
    }

    @Test
    void testCreateEllipse() {
        Ellipse ellipse = factory.createEllipse(TEST_X, TEST_Y, TEST_DIMENSION, TEST_DIMENSION);
        assertNotNull(ellipse);
        assertEquals(Ellipse.class, ellipse.getClass());
        assertEquals(new Point(TEST_X, TEST_Y), ellipse.getPosition());
        assertEquals(TEST_DIMENSION, ellipse.getXRadius());
        assertEquals(TEST_DIMENSION, ellipse.getYRadius());
    }
    
    @Test
    void testCreateEllipse_withNegativeWidth_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createEllipse(TEST_X, TEST_Y, -1, TEST_DIMENSION);
        });
    }
    
    @Test
    void testCreateEllipse_withNegativeHeight_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createEllipse(TEST_X, TEST_Y, TEST_DIMENSION, -1);
        });
    }
    
    @Test
    void testCreateShape_UnsupportedType() {
        assertThrows(IllegalArgumentException.class, () -> {
            factory.createShape("Pentagon", new Object[]{});
        });
    }
}
