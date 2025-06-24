/**
 * PersistenceManager 是图形对象的持久化管理器，使用单例模式确保全局唯一实例。
 * 负责图形的序列化和反序列化，使用GSON库实现JSON格式的持久化。
 *
 * <p>主要功能：
 * <ul>
 *   <li>将图形列表保存到JSON文件</li>
 *   <li>从JSON文件加载图形列表</li>
 *   <li>处理多态类型的序列化/反序列化</li>
 * </ul>
 * </p>
 *
 * <p>使用示例：
 * <pre>{@code
 * List<Shape> shapes = ...;
 * PersistenceManager manager = PersistenceManager.getInstance();
 * manager.saveShapesToFile(shapes, "shapes.json");
 * List<Shape> loaded = manager.loadShapesFromFile("shapes.json");
 * }</pre>
 * </p>
 *
 * <p>线程安全说明：Gson实例是线程安全的，可以并发调用序列化/反序列化方法。</p>
 *
 * @see RuntimeTypeAdapterFactory 用于处理多态类型序列化
 * @see Gson Google的JSON处理库
 * @author liying
 * @since 1.0
 * @version 1.0
 */
package com.example.renderer.singleton;

import com.example.renderer.factory.*;
import com.example.renderer.util.RuntimeTypeAdapterFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

public class PersistenceManager {
    private static final PersistenceManager INSTANCE = new PersistenceManager();
    private final Gson gson;

    private PersistenceManager() {
        // 创建支持多态类型的Gson适配器工厂
        RuntimeTypeAdapterFactory<com.example.renderer.factory.Shape> adapterFactory =
                RuntimeTypeAdapterFactory.of(Shape.class, "type")
                        .registerSubtype(Circle.class, "Circle")
                        .registerSubtype(Rectangle.class, "Rectangle")
                        .registerSubtype(Ellipse.class, "Ellipse")
                        .registerSubtype(Triangle.class, "Triangle");

        // 配置Gson实例
        gson = new GsonBuilder()
                .registerTypeAdapterFactory(adapterFactory) // 注册类型适配器
                .setPrettyPrinting() // 美化输出
                .create();
    }

    /**
     * 获取PersistenceManager单例实例
     * 
     * <p>该方法线程安全，始终返回同一个实例。</p>
     * 
     * @return 全局唯一的PersistenceManager实例(永不为null)
     * @see #PersistenceManager() 私有构造函数
     */
    /**
     * 获取线程安全的PersistenceManager单例实例
     * 
     * <p>该方法使用类初始化保证线程安全，无需同步</p>
     * 
     * @return 全局唯一的PersistenceManager实例
     */
    public static PersistenceManager getInstance() {
        return INSTANCE;
    }

    /**
     * 将图形列表序列化为JSON格式并保存到指定文件
     * 
     * <p>该方法会覆盖目标文件的所有内容，使用UTF-8编码写入。</p>
     * 
     * @param shapes 要保存的图形列表(非null)
     * @param filePath 目标文件路径(非null或空)
     * @throws NullPointerException 如果shapes或filePath为null
     * @throws IllegalArgumentException 如果filePath为空字符串
     * @throws IOException 如果文件写入失败或路径不可访问
     * @throws JsonIOException 如果JSON序列化过程中发生错误
     */
    public void saveShapesToFile(List<com.example.renderer.factory.Shape> shapes, String filePath) throws IOException {
        Objects.requireNonNull(shapes, "Shapes list cannot be null");
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(shapes, writer);
        }
    }

    /**
     * 从JSON文件加载图形列表
     * 
     * <p>该方法会读取指定JSON文件并反序列化为图形对象列表。
     * 支持处理多态类型的图形对象。</p>
     * 
     * @param filePath 要读取的文件路径(非null或空)
     * @return 包含所有反序列化图形对象的不可修改列表
     * @throws NullPointerException 如果filePath为null
     * @throws IllegalArgumentException 如果filePath为空字符串
     * @throws FileNotFoundException 如果指定文件不存在
     * @throws IOException 如果文件读取失败
     * @throws JsonParseException 如果JSON格式不合法
     * @throws JsonSyntaxException 如果JSON与目标类型不匹配
     * @throws IllegalStateException 如果反序列化后的数组为null
     */
    public List<com.example.renderer.factory.Shape> loadShapesFromFile(String filePath) throws IOException {
        Objects.requireNonNull(filePath, "File path cannot be null");
        if (filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be empty");
        }
        try (FileReader reader = new FileReader(filePath)) {
            // 使用数组形式反序列化为 List
            Shape[] shapesArray = gson.fromJson(reader, Shape[].class);
            return List.of(shapesArray);
        }
    }
}
