/** @page architecture 系统架构

# 图形渲染系统架构文档


## 系统概述
本系统提供跨平台的2D图形渲染能力，支持多种渲染后端(Swing/SVG/Legacy)，采用桥接模式将渲染接口与实现分离。

## 核心架构

### 1. 桥接模式 (渲染架构)
- **问题**：渲染接口与实现需要解耦，支持多种渲染后端
- **方案**：
  ```java
  public interface Renderer { /* 抽象接口 */ }
  public class SwingRenderer implements Renderer { /* 具体实现 */ }
  public class SVGRenderer implements Renderer { /* 具体实现 */ }
  public class LegacyRendererAdapter implements Renderer { /* 适配器 */ }
  ```
- **优点**：
  - 渲染接口与实现独立演化
  - 新增渲染器不影响客户端代码
  - 运行时切换渲染实现

### 5. 适配器模式 (Legacy兼容)
- **问题**：整合旧版渲染系统
- **方案**：
  ```java
  public class LegacyRendererAdapter implements Renderer {
      private LegacyRenderer legacyRenderer;
      // 适配方法调用...
  }
  ```
- **适配逻辑**：
  - 转换方法签名(drawCircle → drawLegacyCircle)
  - 添加新系统的参数校验
  - 异常转换处理

### 2. 工厂模式 (对象创建)
- **问题**：图形对象创建需要统一管理和参数校验
- **方案**：
  ```java
  public interface ShapeFactory {
      Circle createCircle(int x, int y, int radius);
      Rectangle createRectangle(int x, int y, int width, int height);
      // 其他工厂方法...
  }
  ```
- **实现特点**：
  - 内置参数校验(半径/宽度必须>0)
  - 统一的创建接口
  - 支持扩展新图形类型
- **优点**：
  - 封装复杂创建逻辑
  - 集中参数校验
  - 客户端与具体类解耦

### 3. 命令模式 (Undo/Redo实现)
- **问题**：需要支持图形操作的撤销/重做功能
- **方案**：
  ```java
  public interface Command {
      void execute();
      void undo();
      void redo();
  }
  
  public class UndoManager {
      private Stack<Command> undoStack;
      private Stack<Command> redoStack;
  }
  ```
- **典型实现类**：
  - `AddShapeCommand`: 添加图形命令
  - `DeleteShapeCommand`: 删除图形命令  
  - `MoveShapeCommand`: 移动图形命令
- **工作流程**：
  1. 执行命令时压入undo栈
  2. 撤销时从undo栈弹出并执行undo()
  3. 重做时从redo栈弹出并执行execute()
- **优点**：
  - 操作封装为对象
  - 支持无限级撤销/重做
  - 命令可组合

### 4. 观察者模式 (变更通知)
- **问题**：需要通知图形状态变化
- **方案**：
  ```java
  public interface ShapeObserver {
      void onShapeChanged();
  }
  
  public class LoggingShapeObserver implements ShapeObserver {
      // 记录带时间戳的日志
  }
  ```
- **通知机制**：
  - 图形变更时调用所有观察者
  - 支持多个观察者实例
  - 异步通知避免阻塞
- **应用场景**：
  - 日志记录
  - UI刷新
  - 持久化触发

### 5. 访问者模式 (Visitor Pattern)
- `ExportVisitor`接口支持不同格式导出
- `JSONExportVisitor`/`XMLExportVisitor`具体实现

## 核心组件

### 1. 渲染器接口 (Renderer)
```java
public interface Renderer {
    // 设置绘制样式
    void setStyle(String stroke, String fill, int width);
    
    // 获取渲染上下文
    Object getContext();
    
    // 帧控制
    void beginFrame();
    void endFrame();
    
    // 图形绘制方法
    void drawCircle(int x, int y, int radius);
    void drawRectangle(int x, int y, int width, int height);
    void drawEllipse(int x, int y, int width, int height);
    void drawTriangle(int x1, int y1, int x2, int y2, int x3, int y3);
}
```

### 2. Swing渲染器 (SwingRenderer)
- 特性：
  - 基于Java Swing的Graphics2D
  - 内置抗锯齿处理
  - 默认蓝色2px线条样式
- 使用示例：
```java
SwingRenderer renderer = new SwingRenderer();
renderer.setGraphics(graphics2D); // 必须设置绘图上下文
renderer.drawCircle(100, 100, 50);
```

### 3. SVG渲染器 (SVGRenderer)
- 特性：
  - 生成标准SVG XML输出
  - 轻量级无外部依赖
  - 支持控制台输出或字符串构建
- 使用示例：
```java
SVGRenderer renderer = new SVGRenderer();
renderer.beginFrame();
renderer.drawCircle(100, 100, 50);
renderer.endFrame(); // 输出SVG到控制台
```
- 输出格式：
```xml
<svg xmlns='http://www.w3.org/2000/svg'>
  <circle cx='100' cy='100' r='50'/>
</svg>
```

### 4. 旧版渲染适配器 (LegacyRendererAdapter)
- 适配逻辑：
  - 将新接口转换为旧版接口调用
  - 添加参数校验
  - 同步方法调用
- 使用示例：
```java
LegacyRenderer legacy = new LegacyRendererImpl();
Renderer adapter = new LegacyRendererAdapter(legacy);
adapter.drawCircle(100, 100, 50); // 调用旧版实现
```

### 5. 远程渲染代理 (RemoteRendererProxy)
- 特性：
  - 透明代理真实渲染器
  - 内置重试机制(默认3次)
  - 调用日志记录
- 使用示例：
```java
Renderer realRenderer = new SwingRenderer();
Renderer proxy = new RemoteRendererProxy(realRenderer);
proxy.drawCircle(100, 100, 50); // 通过代理调用
```

## 典型工作流程

1. 通过`RendererFactory`获取渲染器实例
2. 使用`ShapeFactory`创建图形对象
3. 执行`Command`操作图形
4. 渲染器将图形绘制到目标
5. 观察者记录变更
6. 访问者导出图形数据

*/
