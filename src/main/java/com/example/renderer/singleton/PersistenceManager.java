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
import java.io.IOException;
import java.util.List;

public class PersistenceManager {
    private static final PersistenceManager INSTANCE = new PersistenceManager();
    private final Gson gson;

    private PersistenceManager() {
        RuntimeTypeAdapterFactory<com.example.renderer.factory.Shape> adapterFactory =
                RuntimeTypeAdapterFactory.of(Shape.class, "type")
                        .registerSubtype(Circle.class, "Circle")
                        .registerSubtype(Rectangle.class, "Rectangle")
                        .registerSubtype(Ellipse.class, "Ellipse")
                        .registerSubtype(Triangle.class, "Triangle");

        gson = new GsonBuilder()
                .registerTypeAdapterFactory(adapterFactory)
                .setPrettyPrinting()
                .create();
    }

    /**
     * 获取PersistenceManager单例实例
     * @return 唯一的PersistenceManager实例
     */
    public static PersistenceManager getInstance() {
        return INSTANCE;
    }

    /**
     * 将图形列表保存到指定文件
     * @param shapes 要保存的图形列表
     * @param filePath 目标文件路径
     * @throws IOException 如果文件写入失败
     */
    public void saveShapesToFile(List<com.example.renderer.factory.Shape> shapes, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(shapes, writer);
        }
    }

    /**
     * 从指定文件加载图形列表
     * @param filePath 文件路径(非null)
     * @return 加载的图形列表
     * @throws IOException 如果文件读取失败
     * @throws JsonParseException 如果JSON解析失败
     */
    public List<com.example.renderer.factory.Shape> loadShapesFromFile(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            // 使用数组形式反序列化为 List
            Shape[] shapesArray = gson.fromJson(reader, Shape[].class);
            return List.of(shapesArray);
        }
    }
}
