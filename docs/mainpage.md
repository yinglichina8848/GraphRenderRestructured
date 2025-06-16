# 图形渲染系统文档

## 设计模式实现
- **工厂模式**: ShapeFactory, DefaultShapeFactory
- **单例模式**: GlobalConfig
- **适配器模式**: SVGRendererAdapter
- **桥接模式**: Shape + Renderer
- **代理模式**: RendererProxy
- **命令模式**: Commander, AddShapeCommand
- **访问者模式**: ShapeVisitor, JsonExportVisitor
- **观察者模式**: ShapeListObserver

## 类继承图
\dotfile class_graph.dot

## 核心类列表
- \ref com.example.renderer.Shape
- \ref com.example.renderer.Circle
- \ref com.example.renderer.Rectangle
- \ref com.example.renderer.Renderer
- \ref com.example.renderer.ShapeModel

## 设计模式关系
@startuml
!theme plain
skinparam linetype ortho

package "工厂模式" {
  [ShapeFactory] <|.. [DefaultShapeFactory]
}

package "桥接模式" {
  [Shape] o-- [Renderer]
}

package "命令模式" {
  [Commander] <|-- [AddShapeCommand]
}

[Shape] <|-- [Circle]
[Shape] <|-- [Rectangle]
@enduml
