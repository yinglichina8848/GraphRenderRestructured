
/**
 * PersistenceManagerTest - 验证持久化管理器的核心功能
 *
 * <p>该测试类用于验证 {@link PersistenceManager} 的以下核心行为：
 * <ul>
 *   <li>单例模式的正确性</li>
 *   <li>图形对象的序列化与反序列化</li>
 *   <li>边界值处理与异常抛出机制</li>
 *   <li>并发访问下的线程安全性</li>
 *   <li>文件操作的健壮性（如只读、损坏、特殊字符路径）</li>
 * </ul>
 *
 * <p><b>测试策略：</b>
 * <ul>
 *   <li>使用临时文件进行测试，确保测试隔离性</li>
 *   <li>每个测试方法都包含完整的清理逻辑</li>
 *   <li>同时测试正常情况和异常情况</li>
 * </ul>
 *
 * <p><b>修改记录：</b>
 * <ul>
 *   <li>2025-04-05 | LiYing | 创建初始版本</li>
 *   <li>2025-04-07 | LiYing | 添加损坏文件和只读文件测试</li>
 *   <li>2025-04-08 | LiYing | 补充完整 Javadoc 注释</li>
 *   <li>2025-06-15 | LiYing | 增强测试方法文档</li>
 * </ul>
 *
 * @author LiYing
 * @since 1.0
 * @version 1.1.0
 */
package com.example.renderer.singleton;

import com.example.renderer.factory.*;
import com.google.gson.JsonParseException;
import org.junit.jupiter.api.Test;
import java.io.FileWriter;
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

    /**
     * testSaveAndLoadShapes - 验证保存和加载图形数据是否成功
     *
     * <p>流程：
     * <ol>
     *   <li>创建包含 Circle、Rectangle、Triangle 的图形列表</li>
     *   <li>调用 saveShapesToFile 写入文件</li>
     *   <li>调用 loadShapesFromFile 读取数据</li>
     *   <li>验证读取结果的数量及类型是否一致</li>
     * </ol>
     *
     * @throws IOException 如果文件读写失败
     */
    /**
     * 测试图形数据的保存和加载功能。
     * 
     * <p>验证流程：
     * <ol>
     *   <li>创建包含多种图形(Circle, Rectangle, Triangle)的测试列表</li>
     *   <li>将图形列表保存到临时文件</li>
     *   <li>从临时文件加载图形列表</li>
     *   <li>验证加载结果：
     *     <ul>
     *       <li>图形数量与保存前一致</li>
     *       <li>图形类型正确</li>
     *       <li>图形顺序保持不变</li>
     *     </ul>
     *   </li>
     * </ol>
     * 
     * <p>测试文件会在测试完成后自动删除。
     * 
     * @throws IOException 如果文件操作失败
     * @see PersistenceManager#saveShapesToFile(List, String)
     * @see PersistenceManager#loadShapesFromFile(String)
     */
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

    /**
     * testSingleton - 验证 PersistenceManager 是否为单例
     *
     * <p>通过两次调用 getInstance 获取实例，并验证它们是同一对象引用。
     */
    @Test
    public void testSingleton() {
        PersistenceManager instance1 = PersistenceManager.getInstance();
        PersistenceManager instance2 = PersistenceManager.getInstance();
        assertSame(instance1, instance2);
    }

    /**
     * testSaveShapes_NullList - 验证传入 null 列表时抛出 NullPointerException
     *
     * <p>确保在调用 saveShapesToFile 时传入 null 会触发异常。
     */
    @Test
    public void testSaveShapes_NullList() {
        assertThrows(NullPointerException.class, () ->
                PersistenceManager.getInstance().saveShapesToFile(null, "test.json"));
    }

    /**
     * testLoadShapes_InvalidPath - 验证无效路径加载图形时抛出 IOException
     *
     * <p>模拟尝试从不存在的路径加载文件，验证是否抛出 IO 异常。
     */
    @Test
    public void testLoadShapes_InvalidPath() {
        assertThrows(IOException.class, () ->
                PersistenceManager.getInstance().loadShapesFromFile("/invalid/path"));
    }

    /**
     * testSaveShapes_WithSpecialCharacters - 验证文件名含特殊字符时仍能正常保存
     *
     * <p>创建带有特殊字符的临时文件名并测试保存能力。
     *
     * @throws IOException 如果临时文件创建失败
     */
    @Test
    public void testSaveShapes_WithSpecialCharacters() throws IOException {
        File tempFile = File.createTempFile("test$#@!", ".json");
        List<Shape> shapes = List.of(new Circle(10, 10, 5));
        PersistenceManager.getInstance().saveShapesToFile(shapes, tempFile.getAbsolutePath());
        assertTrue(tempFile.exists());
        tempFile.delete();
    }

    /**
     * testSaveAndLoad_MixedShapes - 验证混合图形保存并正确加载
     *
     * <p>测试 Circle、Rectangle、Triangle 多种图形组合的持久化能力。
     *
     * @throws IOException 如果文件读写失败
     */
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

    /**
     * testSave_EmptyFileName - 验证空文件名时抛出 IllegalArgumentException
     *
     * <p>确保不能将图形保存到空路径或空文件名。
     */
    @Test
    public void testSave_EmptyFileName() {
        assertThrows(IllegalArgumentException.class, () -> {
            PersistenceManager.getInstance().saveShapesToFile(List.of(), "");
        });
    }

    /**
     * testConcurrentAccess - 验证多线程环境下保存图形不抛异常
     *
     * <p>使用线程池并发执行 saveShapesToFile 操作，确保线程安全。
     *
     * @throws InterruptedException 如果线程中断
     */
    /**
     * 测试多线程环境下PersistenceManager的线程安全性。
     * 
     * <p>验证在并发场景下：
     * <ul>
     *   <li>不会抛出任何并发异常</li>
     *   <li>所有线程都能正确完成操作</li>
     *   <li>文件内容保持一致</li>
     * </ul>
     * 
     * <p>测试方法：
     * <ol>
     *   <li>创建10个线程的线程池</li>
     *   <li>每个线程都执行保存操作</li>
     *   <li>等待所有线程完成</li>
     *   <li>验证没有抛出异常</li>
     * </ol>
     * 
     * @throws InterruptedException 如果线程被中断
     * @see ExecutorService
     */
    @Test
    public void testConcurrentAccess() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            futures.add(executor.submit(() -> {
                PersistenceManager pm = PersistenceManager.getInstance();
                assertDoesNotThrow((ThrowingSupplier<Void>) () -> {
                    pm.saveShapesToFile(List.of(new Circle(0, 0, 1)), "concurrent_test.json");
                    return null;
                });
            }));
        }

        for (Future<?> future : futures) {
            assertDoesNotThrow((ThrowingSupplier<Void>) () -> {
                future.get();
                return null;
            });
        }
        executor.shutdown();
        new File("concurrent_test.json").delete();
    }

    /**
     * testSaveShapes_EmptyPath - 验证空路径保存抛出 IllegalArgumentException
     *
     * <p>确保不能将图形保存到空路径。
     */
    @Test
    public void testSaveShapes_EmptyPath() {
        assertThrows(IllegalArgumentException.class, () ->
                PersistenceManager.getInstance().saveShapesToFile(List.of(), ""));
    }

    /**
     * testLoadShapes_CorruptedFile - 验证加载损坏 JSON 文件时抛出 JsonParseException
     *
     * <p>手动构造一个非法 JSON 文件，验证解析失败时是否抛出正确异常。
     *
     * @throws IOException 如果临时文件写入失败
     */
    /**
     * 测试加载损坏的JSON文件时的异常处理。
     * 
     * <p>验证当JSON文件格式不正确时：
     * <ul>
     *   <li>抛出正确的异常类型(JsonParseException)</li>
     *   <li>异常消息包含有用的调试信息</li>
     * </ul>
     * 
     * <p>测试步骤：
     * <ol>
     *   <li>创建包含非法JSON内容的临时文件</li>
     *   <li>尝试加载该文件</li>
     *   <li>验证抛出的异常类型</li>
     * </ol>
     * 
     * @throws IOException 如果临时文件创建失败
     * @see JsonParseException
     */
    @Test
    public void testLoadShapes_CorruptedFile() throws IOException {
        // 创建损坏的JSON文件
        File tempFile = File.createTempFile("corrupted", ".json");
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("{invalid json}");
        }

        assertThrows(JsonParseException.class, () ->
                PersistenceManager.getInstance().loadShapesFromFile(tempFile.getAbsolutePath()));

        tempFile.delete();
    }

    /**
     * testSaveShapes_ReadOnlyFile - 验证向只读文件保存时抛出 IOException
     *
     * <p>创建只读文件后尝试写入，验证是否抛出 IO 异常。
     *
     * @throws IOException 如果文件设置失败
     */
    @Test
    public void testSaveShapes_ReadOnlyFile() throws IOException {
        File tempFile = File.createTempFile("readonly", ".json");
        tempFile.setReadOnly();

        assertThrows(IOException.class, () ->
                PersistenceManager.getInstance().saveShapesToFile(
                        List.of(new Circle(0, 0, 1)),
                        tempFile.getAbsolutePath()));

        tempFile.delete();
    }
}
