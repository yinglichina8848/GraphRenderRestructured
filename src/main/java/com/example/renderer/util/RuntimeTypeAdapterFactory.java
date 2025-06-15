package com.example.renderer.util;

/**
 * RuntimeTypeAdapterFactory是Gson的类型适配器工厂，支持运行时多态类型的序列化/反序列化。
 * 解决Gson默认不支持多态类型的问题，通过类型字段(typeFieldName)来区分具体子类。
 *
 * <p>工作原理：
 * <ol>
 *   <li>序列化时添加类型标识字段</li>
 *   <li>反序列化时根据类型标识字段创建对应子类实例</li>
 * </ol>
 * </p>
 *
 * <p>典型用法：
 * <pre>{@code
 * RuntimeTypeAdapterFactory<Shape> adapterFactory = 
 *     RuntimeTypeAdapterFactory.of(Shape.class, "type")
 *         .registerSubtype(Circle.class, "circle")
 *         .registerSubtype(Rectangle.class, "rectangle");
 *
 * Gson gson = new GsonBuilder()
 *     .registerTypeAdapterFactory(adapterFactory)
 *     .create();
 * }</pre>
 * </p>
 *
 * <p>注意事项：
 * <ul>
 *   <li>类型字段名称(typeFieldName)必须在JSON中唯一</li>
 *   <li>所有子类必须提前注册</li>
 *   <li>子类需要有无参构造函数</li>
 * </ul>
 * </p>
 *
 * @see <a href="https://github.com/google/gson/blob/master/extras/src/main/java/com/google/gson/typeadapters/RuntimeTypeAdapterFactory.java">
 *     Gson官方类似实现</a>
 * @see Gson Google的JSON处理库
 * @author liying
 * @since 1.0
 * @version 1.0
 */

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.internal.Streams;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RuntimeTypeAdapterFactory<T> implements TypeAdapterFactory {
    private final Class<?> baseType;
    private final String typeFieldName;
    private final Map<String, Class<?>> labelToSubtype = new HashMap<>();
    private final Map<Class<?>, String> subtypeToLabel = new HashMap<>();

    private RuntimeTypeAdapterFactory(Class<?> baseType, String typeFieldName) {
        this.baseType = baseType;
        this.typeFieldName = typeFieldName;
    }

    public static <T> RuntimeTypeAdapterFactory<T> of(Class<T> baseType, String typeFieldName) {
        return new RuntimeTypeAdapterFactory<>(baseType, typeFieldName);
    }

    public RuntimeTypeAdapterFactory<T> registerSubtype(Class<? extends T> type, String label) {
        labelToSubtype.put(label, type);
        subtypeToLabel.put(type, label);
        return this;
    }

    @Override
    public <R> TypeAdapter<R> create(Gson gson, TypeToken<R> type) {
        if (!baseType.isAssignableFrom(type.getRawType())) {
            return null;
        }

        final Map<String, TypeAdapter<?>> labelToDelegate = new HashMap<>();
        final Map<Class<?>, TypeAdapter<?>> subtypeToDelegate = new HashMap<>();

        for (Map.Entry<String, Class<?>> entry : labelToSubtype.entrySet()) {
            TypeAdapter<?> delegate = gson.getDelegateAdapter(this, TypeToken.get(entry.getValue()));
            labelToDelegate.put(entry.getKey(), delegate);
            subtypeToDelegate.put(entry.getValue(), delegate);
        }

        return new TypeAdapter<R>() {
            @Override
            public void write(JsonWriter out, R value) throws IOException {
                Class<?> srcType = value.getClass();
                String label = subtypeToLabel.get(srcType);
                if (label == null) {
                    throw new JsonParseException("Cannot serialize " + srcType.getName());
                }
                TypeAdapter<R> delegate = (TypeAdapter<R>) subtypeToDelegate.get(srcType);
                JsonObject jsonObject = delegate.toJsonTree(value).getAsJsonObject();
                jsonObject.addProperty(typeFieldName, label);
                Streams.write(jsonObject, out);
            }

            @Override
            public R read(JsonReader in) throws IOException {
                JsonElement jsonElement = Streams.parse(in);
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                JsonElement labelJsonElement = jsonObject.remove(typeFieldName);
                if (labelJsonElement == null) {
                    throw new JsonParseException("Cannot deserialize missing type field: " + typeFieldName);
                }
                String label = labelJsonElement.getAsString();
                TypeAdapter<?> delegate = labelToDelegate.get(label);
                if (delegate == null) {
                    throw new JsonParseException("Unknown label: " + label);
                }
                return (R) delegate.fromJsonTree(jsonObject);
            }
        };
    }
}
