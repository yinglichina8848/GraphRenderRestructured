# 系统序列图

## 1. 图形创建流程

```plantuml
@startuml
actor User
participant Client
participant ShapeFactory
participant Circle

User -> Client: 创建圆形(x=10,y=20,r=30)
Client -> ShapeFactory: createCircle(10,20,30)
ShapeFactory -> Circle: new()
ShapeFactory --> Client: 返回Circle实例
Client --> User: 返回创建结果
@enduml
```

## 2. 图形渲染流程

```plantuml
@startuml
actor User
participant Client
participant RendererFactory
participant SwingRenderer
participant Graphics2D

User -> Client: 渲染图形
Client -> RendererFactory: getRenderer("swing")
RendererFactory -> SwingRenderer: new()
RendererFactory --> Client: 返回renderer实例
Client -> SwingRenderer: setGraphics(graphics2D)
Client -> SwingRenderer: beginFrame()
Client -> SwingRenderer: drawCircle(circle)
SwingRenderer -> Graphics2D: 实际绘制
Client -> SwingRenderer: endFrame()
@enduml
```

## 3. 撤销操作流程

```plantuml
@startuml
actor User
participant Client
participant UndoManager
participant AddShapeCommand
participant ShapeList

User -> Client: 执行撤销
Client -> UndoManager: undo()
UndoManager -> AddShapeCommand: undo()
AddShapeCommand -> ShapeList: remove(shape)
AddShapeCommand --> UndoManager: 完成
UndoManager --> Client: 完成
Client --> User: 显示结果
@enduml
```

## 4. 观察者通知流程

```plantuml
@startuml
actor User
participant Client
participant Shape
participant LoggingShapeObserver

User -> Client: 修改图形属性
Client -> Shape: setPosition(x,y)
Shape -> LoggingShapeObserver: onShapeChanged()
LoggingShapeObserver -> System.out: 记录日志
@enduml
```

## 5. 远程渲染代理流程

```plantuml
@startuml
actor User
participant Client
participant RemoteRendererProxy
participant RealRenderer
participant Network

User -> Client: 绘制图形
Client -> RemoteRendererProxy: drawCircle(100,100,50)
RemoteRendererProxy -> Network: 发送请求
Network -> RealRenderer: 执行绘制
RealRenderer --> Network: 返回结果
Network --> RemoteRendererProxy: 返回结果
RemoteRendererProxy --> Client: 完成
Client --> User: 显示结果
@enduml
```

## 如何使用

1. 安装PlantUML插件(VSCode/IntelliJ等)
2. 将上述代码复制到`.puml`文件中
3. 使用插件渲染生成序列图

```bash
# 生成PNG图片
plantuml sequence-diagrams.puml -png
```

> 注意：需要先安装PlantUML，在Ubuntu上可以使用`sudo apt-get install plantuml`
