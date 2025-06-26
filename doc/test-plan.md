# 图形渲染系统测试计划

## 1. 单元测试

### 1.1 渲染器测试
```java
// SwingRenderer测试
@Test
public void testSwingRendererDrawCircle() {
    SwingRenderer renderer = new SwingRenderer();
    MockGraphics2D mockGraphics = new MockGraphics2D();
    renderer.setGraphics(mockGraphics);
    
    renderer.drawCircle(100, 100, 50);
    assertEquals("draw:Ellipse2D", mockGraphics.lastDrawCall);
}

// SVGRenderer测试
@Test
public void testSVGRendererOutput() {
    SVGRenderer renderer = new SVGRenderer();
    renderer.beginFrame();
    renderer.drawCircle(100, 100, 50);
    renderer.endFrame();
    // 验证控制台输出或获取SVG字符串验证
}
```

### 1.2 图形工厂测试
```java
@Test
public void testCreateCircleWithInvalidRadius() {
    ShapeFactory factory = new ShapeFactoryImpl();
    assertThrows(IllegalArgumentException.class, 
        () -> factory.createCircle(0, 0, -1));
}

@Test
public void testCreateValidRectangle() {
    ShapeFactory factory = new ShapeFactoryImpl();
    Rectangle rect = factory.createRectangle(10, 10, 100, 50);
    assertNotNull(rect);
    assertEquals(100, rect.getWidth());
}
```

### 1.3 命令模式测试
```java
@Test
public void testAddShapeCommand() {
    List<Shape> shapes = new ArrayList<>();
    Circle circle = new Circle(10, 10, 5);
    Command cmd = new AddShapeCommand(shapes, circle);
    
    cmd.execute();
    assertEquals(1, shapes.size());
    
    cmd.undo();
    assertTrue(shapes.isEmpty());
}
```

## 2. 集成测试

### 2.1 渲染流程集成测试
```java
@Test
public void testFullRenderPipeline() {
    // 创建图形
    ShapeFactory factory = new ShapeFactoryImpl();
    Circle circle = factory.createCircle(100, 100, 50);
    
    // 创建渲染器
    SwingRenderer renderer = new SwingRenderer();
    MockGraphics2D mockGraphics = new MockGraphics2D();
    renderer.setGraphics(mockGraphics);
    
    // 渲染图形
    renderer.beginFrame();
    circle.draw(renderer);
    renderer.endFrame();
    
    // 验证
    assertEquals(1, mockGraphics.drawCallCount);
}
```

### 2.2 撤销重做集成测试
```java
@Test
public void testUndoRedoWorkflow() {
    UndoManager manager = new UndoManager();
    List<Shape> shapes = new ArrayList<>();
    Shape circle = new Circle(10, 10, 5);
    
    // 执行添加命令
    manager.executeCommand(new AddShapeCommand(shapes, circle));
    assertEquals(1, shapes.size());
    
    // 撤销
    manager.undo();
    assertTrue(shapes.isEmpty());
    
    // 重做
    manager.redo();
    assertEquals(1, shapes.size());
}
```

## 3. 性能测试

### 3.1 渲染性能测试
```java
@Test
public void performanceTestRender1000Circles() {
    SwingRenderer renderer = new SwingRenderer();
    renderer.setGraphics(new MockGraphics2D());
    
    long startTime = System.currentTimeMillis();
    renderer.beginFrame();
    for (int i = 0; i < 1000; i++) {
        renderer.drawCircle(i % 500, i % 300, 10);
    }
    renderer.endFrame();
    long duration = System.currentTimeMillis() - startTime;
    
    assertTrue(duration < 1000); // 应在1秒内完成
}
```

### 3.2 内存使用测试
```java
@Test
public void memoryUsageTest() {
    Runtime runtime = Runtime.getRuntime();
    long before = runtime.totalMemory() - runtime.freeMemory();
    
    List<Shape> shapes = new ArrayList<>();
    for (int i = 0; i < 10000; i++) {
        shapes.add(new Circle(i % 500, i % 300, 10));
    }
    
    long after = runtime.totalMemory() - runtime.freeMemory();
    long used = after - before;
    assertTrue(used < 10 * 1024 * 1024); // 应小于10MB
}
```

## 4. 测试覆盖率目标

| 模块           | 行覆盖率 | 分支覆盖率 |
|----------------|----------|------------|
| 渲染器实现     | ≥90%     | ≥80%       |
| 图形工厂       | 100%     | 100%       | 
| 命令模式       | ≥95%     | ≥90%       |
| 适配器/代理    | ≥85%     | ≥75%       |

## 5. 测试工具

1. JUnit 5 - 单元测试框架
2. Mockito - 模拟对象
3. JaCoCo - 代码覆盖率
4. JMH - 微基准测试(可选)

## 6. 测试执行

```bash
# 运行所有单元测试
mvn test

# 运行集成测试
mvn verify

# 生成覆盖率报告
mvn jacoco:report
```

## 7. 测试数据策略

1. 边界值测试：
   - 零值/负值参数
   - 最大允许值
   - 非法输入

2. 随机测试：
   - 使用随机生成的有效参数
   - 验证不变量

3. 黄金样本：
   - 保存已知正确的渲染输出
   - 用于回归测试
