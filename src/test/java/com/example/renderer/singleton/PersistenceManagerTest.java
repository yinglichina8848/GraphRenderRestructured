package com.example.renderer.singleton;

import com.example.renderer.factory.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PersistenceManagerTest {
    private static final String TEST_FILE = "test_shapes.json";

    @Test
    public void testSaveAndLoadShapes() throws IOException {
        // Setup
        List<Shape> shapes = List.of(
            new Circle(10, 20, 30),
            new Rectangle(40, 50, 60, 70),
            new Triangle(1, 2, 3, 4, 5, 6)
        );
        
        // Test save
        PersistenceManager.getInstance().saveShapesToFile(shapes, TEST_FILE);
        
        // Test load
        List<Shape> loadedShapes = PersistenceManager.getInstance().loadShapesFromFile(TEST_FILE);
        
        // Verify
        assertEquals(3, loadedShapes.size());
        assertTrue(loadedShapes.get(0) instanceof Circle);
        assertTrue(loadedShapes.get(1) instanceof Rectangle);
        assertTrue(loadedShapes.get(2) instanceof Triangle);
        
        // Cleanup
        new File(TEST_FILE).delete();
    }

    @Test
    public void testSingleton() {
        PersistenceManager instance1 = PersistenceManager.getInstance();
        PersistenceManager instance2 = PersistenceManager.getInstance();
        assertSame(instance1, instance2);
    }
}
