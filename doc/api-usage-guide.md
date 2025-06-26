# 图形渲染系统API使用指南

## 1. 渲染器接口(Renderer)

### 基本用法
```java
// 获取渲染器实例
Renderer renderer = RendererFactory.getRenderer("swing");

// 设置绘图样式
renderer.setStyle("#FF0000", "#00FF00", 2); // 红色描边，绿色填充，2px线宽

// 绘制图形
renderer.beginFrame();
renderer.drawCircle(100, 100, 50);
renderer.drawRectangle(50, 50, 100, 80);
renderer.endFrame();
```

### 方法说明

#### setStyle(String stroke, String fill, int width)
- 功能：设置绘图样式
- 参数：
  - stroke: 描边颜色(十六进制或颜色名称)
  - fill: 填充颜色
  - width: 线宽(像素)
- 示例：
  ```java
  renderer.setStyle("blue", "yellow", 3);
  ```

#### drawCircle(int x, int y, int radius)
- 功能：绘制圆形
- 参数：
  - x,y: 圆心坐标
  - radius: 半径(必须>0)
- 异常：
  - IllegalArgumentException: 半径不合法
- 示例：
  ```java
  renderer.drawCircle(100, 100, 30);
  ```

## 2. 图形工厂(ShapeFactory)

### 基本用法
```java
ShapeFactory factory = new ShapeFactoryImpl();

// 创建图形
Circle circle = factory.createCircle(100, 100, 30);
Rectangle rect = factory.createRectangle(50, 50, 100, 80);

// 使用图形
circle.draw(renderer);
```

### 方法说明

#### createCircle(int x, int y, int radius)
- 功能：创建圆形
- 参数：
  - x,y: 圆心坐标
  - radius: 半径(必须>0)
- 返回：Circle实例
- 异常：
  - IllegalArgumentException: 半径不合法
- 示例：
  ```java
  Circle circle = factory.createCircle(100, 100, 30);
  ```

## 3. 命令模式(Command/UndoManager)

### 基本用法
```java
UndoManager undoManager = new UndoManager();
List<Shape> shapes = new ArrayList<>();

// 创建并执行命令
Command addCmd = new AddShapeCommand(shapes, circle);
undoManager.executeCommand(addCmd);

// 撤销操作
if (undoManager.canUndo()) {
    undoManager.undo();
}

// 重做操作  
if (undoManager.canRedo()) {
    undoManager.redo();
}
```

## 4. 渲染器实现类

### SwingRenderer
```java
SwingRenderer renderer = new SwingRenderer();
renderer.setGraphics(graphics2D); // 必须设置Graphics2D上下文
renderer.drawCircle(100, 100, 50);
```

### SVGRenderer
```java
SVGRenderer renderer = new SVGRenderer();
renderer.beginFrame();
renderer.drawCircle(100, 100, 50);
String svg = renderer.getSVG(); // 获取SVG字符串
```

### LegacyRendererAdapter
```java
LegacyRenderer legacy = new LegacyRendererImpl();
Renderer adapter = new LegacyRendererAdapter(legacy);
adapter.drawCircle(100, 100, 50); // 调用旧版实现
```

## 5. 常见问题

### Q: 如何切换渲染器？
```java
// 使用Swing渲染器
Renderer swingRenderer = RendererFactory.getRenderer("swing");

// 使用SVG渲染器 
Renderer svgRenderer = RendererFactory.getRenderer("svg");
```

### Q: 如何实现撤销/重做？
```java
UndoManager manager = new UndoManager();

// 执行命令
manager.executeCommand(new AddShapeCommand(shapes, circle));

// 撤销
manager.undo();

// 重做
manager.redo();
```

## 6. 最佳实践

1. 总是先调用`beginFrame()`再开始绘制
2. 使用工厂方法创建图形对象
3. 对用户输入参数进行校验
4. 使用命令模式实现撤销/重做功能
5. 通过观察者模式监听图形变化
