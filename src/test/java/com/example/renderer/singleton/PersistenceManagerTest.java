package com.example.renderer.singleton;

import com.example.renderer.factory.*;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.function.ThrowingSupplier;

public class PersistenceManagerTest {
    private static final String TEST_FILE = "test_shapes.json";
    private final File testFile = new File(TEST_FILE);

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

    // 边界值测试
    @Test
    public void testSaveShapes_NullList() {
        assertThrows(NullPointerException.class, () -> 
            PersistenceManager.getInstance().saveShapesToFile(null, "test.json"));
    }

    @Test
    public void testLoadShapes_InvalidPath() {
        assertThrows(IOException.class, () -> 
            PersistenceManager.getInstance().loadShapesFromFile("/invalid/path"));
    }

    @Test
    public void testSaveShapes_WithSpecialCharacters() throws IOException {
        File tempFile = File.createTempFile("test$#@!", ".json");
        List<Shape> shapes = List.of(new Circle(10, 10, 5));
        PersistenceManager.getInstance().saveShapesToFile(shapes, tempFile.getAbsolutePath());
        assertTrue(tempFile.exists());
        tempFile.delete();
    }

    @Test
    public void testSaveAndLoad_MixedShapes() throws IOException {
        List<Shape> shapes = List.of(
            new Circle(10, 10, 5),
            new Rectangle(20, 20, 10, 10),
            new Triangle(0, 0, 10, 0, 5, 10)
        );
        PersistenceManager.getInstance().saveShapesToFile(shapes, TEST_FILE);
        List<Shape> loaded = PersistenceManager.getInstance().loadShapesFromFile(TEST_FILE);
        assertEquals(3, loaded.size());
        assertTrue(loaded.get(0) instanceof Circle);
        assertTrue(loaded.get(1) instanceof Rectangle);
        assertTrue(loaded.get(2) instanceof Triangle);
        
        // Cleanup
        new File(TEST_FILE).delete();
    }

    @Test
    public void testSave_EmptyFileName() {
        assertThrows(IllegalArgumentException.class, () -> {
            PersistenceManager.getInstance().saveShapesToFile(List.of(), "");
        });
    }

    @Test
    public void testConcurrentAccess() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            futures.add(executor.submit(() -> {
                PersistenceManager pm = PersistenceManager.getInstance();
                assertDoesNotThrow((org.junit.jupiter.api.function.ThrowingSupplier<Void>) () -> {
                    pm.saveShapesToFile(List.of(new Circle(0,0,1)), "concurrent_test.json");
                    return null;
                });
            }));
        }

        for (Future<?> future : futures) {
            //assertDoesNotThrow(future::get);
            assertDoesNotThrow((ThrowingSupplier<Void>) () -> {
                future.get();
                return null;
            });



        }
        executor.shutdown();
        new File("concurrent_test.json").delete();
    }

    @Test
    public void testSaveShapes_EmptyPath() {
        assertThrows(IllegalArgumentException.class, () -> 
            PersistenceManager.getInstance().saveShapesToFile(List.of(), ""));
    }
}
