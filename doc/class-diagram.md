# 系统类图

```plantuml
@startuml

' 渲染器相关类
interface Renderer {
  +setStyle(stroke, fill, width)
  +getContext(): Object
  +beginFrame()
  +endFrame()
  +drawCircle(x, y, radius)
  +drawRectangle(x, y, width, height)
  +drawEllipse(x, y, width, height)
  +drawTriangle(x1, y1, x2, y2, x3, y3)
}

class SwingRenderer {
  -Graphics2D g
  +setGraphics(g)
}

class SVGRenderer {
  -StringBuilder svgBuilder
  +getSVG(): String
}

class LegacyRendererAdapter {
  -LegacyRenderer legacyRenderer
}

class RemoteRendererProxy {
  -Renderer realRenderer
  +RemoteRendererProxy(realRenderer)
}

' 图形相关类
interface Shape {
  +draw(renderer)
}

class Circle {
  -int x, y, radius
}

class Rectangle {
  -int x, y, width, height
}

class Triangle {
  -int x1, y1, x2, y2, x3, y3
}

class Ellipse {
  -int x, y, width, height
}

' 工厂模式
interface ShapeFactory {
  +createCircle(x, y, radius): Circle
  +createRectangle(x, y, width, height): Rectangle
  +createTriangle(x1, y1, x2, y2, x3, y3): Triangle
  +createEllipse(x, y, width, height): Ellipse
}

' 命令模式
interface Command {
  +execute()
  +undo()
  +redo()
}

class UndoManager {
  -Stack<Command> undoStack
  -Stack<Command> redoStack
  +executeCommand(cmd)
  +undo()
  +redo()
}

' 观察者模式
interface ShapeObserver {
  +onShapeChanged()
}

class LoggingShapeObserver {
  +onShapeChanged()
}

' 关系定义
Renderer <|-- SwingRenderer
Renderer <|-- SVGRenderer
Renderer <|-- LegacyRendererAdapter
Renderer <|-- RemoteRendererProxy

Shape <|-- Circle
Shape <|-- Rectangle
Shape <|-- Triangle
Shape <|-- Ellipse

LegacyRendererAdapter --> LegacyRenderer
RemoteRendererProxy --> Renderer

ShapeFactory ..> Circle
ShapeFactory ..> Rectangle
ShapeFactory ..> Triangle
ShapeFactory ..> Ellipse

UndoManager --> Command
LoggingShapeObserver ..|> ShapeObserver

@enduml
```

## 关键类说明

### 渲染器相关
- `Renderer`: 定义统一的渲染接口
- `SwingRenderer`: 基于Swing的实现
- `SVGRenderer`: 生成SVG格式输出
- `LegacyRendererAdapter`: 适配旧版渲染器
- `RemoteRendererProxy`: 远程渲染代理

### 图形相关
- `Shape`: 图形基类接口
- `Circle`/`Rectangle`/`Triangle`/`Ellipse`: 具体图形实现

### 设计模式
- `ShapeFactory`: 工厂模式创建图形
- `Command`/`UndoManager`: 命令模式实现撤销重做
- `ShapeObserver`: 观察者模式通知变化

## 如何查看图表

1. 安装PlantUML插件(VSCode/IntelliJ等)
2. 或使用在线PlantUML渲染器
3. 将代码复制到编辑器中即可生成图表
