# 图形渲染系统文档

## 项目构建配置
项目使用Maven构建，主要配置如下：

### 核心依赖
- **Gson**: 2.10.1 - 用于JSON序列化/反序列化
- **Guava**: 32.1.2-jre - Google核心工具库
- **SLF4J**: 2.0.9 - 日志门面接口
- **Logback**: 1.4.11 - 日志实现
- **JUnit Jupiter**: 5.10.2 - 单元测试框架
- **Mockito**: 5.12.0 - 测试mock框架

### 构建插件
- **maven-compiler-plugin**: 3.11.0 - 设置Java 17编译环境
- **maven-jar-plugin**: 3.3.0 - 生成可执行JAR
- **jacoco-maven-plugin**: 0.8.11 - 生成测试覆盖率报告
- **exec-maven-plugin**: 3.1.0 - 集成Doxygen文档生成

### 文档生成
- **maven-javadoc-plugin**: 3.3.2 - 生成Java API文档
- **maven-site-plugin**: 3.12.1 - 生成项目站点
- **doxygen**: 通过exec插件调用生成技术文档

## 构建命令
```bash
# 编译项目
mvn compile

# 运行测试
mvn test

# 生成可执行JAR
mvn package

# 生成文档站点(包含JavaDoc和Doxygen)
mvn site

# 运行应用程序
java -jar target/graph-render-1.0-SNAPSHOT.jar
```

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
