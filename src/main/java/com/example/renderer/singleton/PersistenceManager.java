/**
 * PersistenceManager
 *
 * @author liying
 * @date 2025-06-14
 * @lastModified 2025-06-14
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

    public List<com.example.renderer.factory.Shape> loadShapesFromFile(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            // 使用数组形式反序列化为 List
            Shape[] shapesArray = gson.fromJson(reader, Shape[].class);
            return List.of(shapesArray);
        }
    }
}
