/** @mainpage 图形渲染系统 - 核心文档
 * @tableofcontents
 * 
 * @section doc_links 项目文档
 * 以下是项目相关文档列表：
 * @subpage md_doc_index
 * 
 * @section intro_sec 项目简介
 * 一个基于现代Java技术的图形渲染框架，主要特点：
 * 
 * - 🎨 多渲染后端支持(Swing/SVG/Legacy/Test)
 * - 📐 基本图形绘制(圆形/矩形/三角形/椭圆)
 * - 🔄 命令模式实现完整操作历史
 * - 📊 多种导出格式(JSON/XML)
 * - 🔍 可扩展的观察者机制
 * - 🧩 基于工厂模式的灵活创建
 *
 * @section build_sec 构建与依赖
 * 
 * @subsection deps_sec 核心依赖
 * | 依赖项         | 版本      | 用途                  |
 * |----------------|----------|----------------------|
 * | Gson           | 2.10.1   | JSON序列化/反序列化    |
 * | Guava          | 32.1.2   | 核心工具库            |
 * | SLF4J+Logback  | 2.0.9    | 日志系统              |
 * | JUnit Jupiter  | 5.10.2   | 单元测试框架          |
 * | Mockito        | 5.12.0   | 测试Mock支持          |
 *
 * @subsection plugins 构建插件
 * - **maven-compiler-plugin**: 3.11.0 - 设置Java 17编译环境
 * - **maven-jar-plugin**: 3.3.0 - 生成可执行JAR
 * - **jacoco-maven-plugin**: 0.8.11 - 生成测试覆盖率报告
 * - **exec-maven-plugin**: 3.1.0 - 集成Doxygen文档生成
 *
 * @subsection docs 文档生成
 * - **maven-javadoc-plugin**: 3.3.2 - 生成Java API文档
 * - **maven-site-plugin**: 3.12.1 - 生成项目站点
 * - **doxygen**: 通过exec插件调用生成技术文档
 *
 * @section commands 构建命令
 * @code{.bash}
 * # 编译项目
 * mvn compile
 *
 * # 运行测试
 * mvn test
 *
 * # 生成可执行JAR
 * mvn package
 *
 * # 生成文档站点(包含JavaDoc和Doxygen)
 * mvn site
 *
 * # 运行应用程序
 * java -jar target/graph-render-1.0-SNAPSHOT.jar
 * @endcode
 *
 * @section patterns_sec 设计模式应用
 * 
 * @subsection creation_sec 创建型模式
 * - 🏭 **工厂模式**: `RendererFactory`、`ShapeFactory`
 * - 🔒 **单例模式**: `GlobalConfig`、`PersistenceManager`
 * 
 * @subsection structural_sec 结构型模式  
 * - 🔌 **适配器模式**: `LegacyRendererAdapter`
 * - 🌉 **桥接模式**: `Shape` ←→ `Renderer` 抽象
 * - 🎭 **代理模式**: `RemoteRendererProxy`
 * 
 * @subsection behavioral_sec 行为型模式
 * - 📜 **命令模式**: `Command` 接口及实现类
 * - 👀 **观察者模式**: `ShapeObserver` 体系
 * - 🚶 **访问者模式**: `ExportVisitor` 实现
 *
 * @section classes 核心类列表
 * - \ref com.example.renderer.Shape
 * - \ref com.example.renderer.Circle
 * - \ref com.example.renderer.Rectangle
 * - \ref com.example.renderer.Renderer
 * - \ref com.example.renderer.ShapeModel
 *
 * @dotfile class_graph.dot
 *
 * @startuml
 * !theme plain
 * skinparam linetype ortho
 *
 * package "工厂模式" {
 *   [ShapeFactory] <|.. [DefaultShapeFactory]
 * }
 *
 * package "桥接模式" {
 *   [Shape] o-- [Renderer]
 * }
 *
 * package "命令模式" {
 *   [Commander] <|-- [AddShapeCommand]
 * }
 *
 * [Shape] <|-- [Circle]
 * [Shape] <|-- [Rectangle]
 * @enduml
 */
