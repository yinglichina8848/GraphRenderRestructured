# 类与方法参考文档

## 渲染相关

### Renderer 接口
```java
public interface Renderer
```
核心渲染接口，定义所有渲染器必须实现的方法。

#### 主要方法
- `setStyle(String stroke, String fill, int width)`: 设置绘制样式
- `drawCircle(int x, int y, int radius)`: 绘制圆形
- `drawRectangle(int x, int y, int width, int height)`: 绘制矩形  
- `drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3)`: 绘制三角形
- `drawEllipse(int x, int y, int width, int height)`: 绘制椭圆
- `getContext()`: 获取渲染上下文

### SwingRenderer
```java
public class SwingRenderer implements Renderer
```
基于Swing的渲染器实现。

#### 特点
- 需要先调用`setGraphics()`设置绘图上下文
- 默认使用蓝色线条和2像素线宽
- 自动开启抗锯齿

### SVGRenderer 
```java
public class SVGRenderer implements Renderer
```
SVG格式输出渲染器。

#### 特点
- 输出SVG标记到控制台
- 轻量级无依赖
- 主要用于调试目的

## 图形相关

### Shape 接口
```java
public interface Shape
```
图形对象基础接口。

#### 主要方法
- `render(Renderer renderer)`: 使用指定渲染器绘制
- `move(int dx, int dy)`: 移动图形
- `accept(ExportVisitor visitor)`: 访问者模式支持

## 工具类

### GlobalConfig
```java
public class GlobalConfig
```
全局配置管理单例，使用双重检查锁定保证线程安全。

#### 主要方法
- `getInstance()`: 获取单例实例
- `getRenderMode()`: 获取当前渲染模式("swing"/"svg"/"legacy")
- `setRenderMode(String mode)`: 设置渲染模式
- `loadConfig()`: 从文件/系统属性加载配置

### UndoManager
```java
public class UndoManager 
```
撤销/重做管理器，使用栈结构保存命令历史。

#### 主要方法
- `executeCommand(Command cmd)`: 执行并记录命令
- `undo()`: 撤销上一步操作  
- `redo()`: 重做上一步撤销
- `setMaxHistorySize(int)`: 设置最大历史记录数

### PersistenceManager
```java
public class PersistenceManager
```
图形持久化管理单例，使用Gson处理JSON序列化。

#### 主要方法
- `saveShapesToFile(List<Shape>, String)`: 保存图形列表到文件
- `loadShapesFromFile(String)`: 从文件加载图形列表
- 内置多态类型处理

## 适配器与代理

### LegacyRendererAdapter
```java
public class LegacyRendererAdapter implements Renderer
```
旧版渲染器适配器。

#### 特点
- 桥接新旧渲染接口
- 线程安全的synchronized方法
- 参数验证和异常转换

### RemoteRendererProxy
```java 
public class RemoteRendererProxy implements Renderer
```
远程渲染代理。

#### 特点
- 实现重试机制(默认3次)
- 记录远程调用日志
- 透明代理模式
