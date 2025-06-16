# GraphRenderRestructured 🌐

为 GraphRenderRestructured 项目提供整体说明，包括构建、使用、文档查看和访问方式。

# GraphRenderRestructured
软件工程课程期末作业：利用模式设计的图形渲染系统

软件工程期末大作业：图形渲染系统的设计与实现
一、任务简介
本项目旨在通过设计并实现一个初步的图形渲染系统，综合运用以下9种典型设计模式：
• 创建型模式：
◦ Factory（工厂）
◦ Abstract Factory（抽象工厂）
◦ Singleton（单例）
• 结构型模式：
◦ Adapter（适配器）
◦ Bridge（桥接）
◦ Proxy（代理）
• 行为型模式：
◦ Command（命令）
◦ Visitor（访问者）
◦ Observer（观察者）
二、项目目标
设计并实现一个简化的图形渲染系统，具备以下功能：
1. 渲染基本图形元素（如圆形、矩形、线段）。
2. 支持不同的渲染引擎（如 SVG、Canvas、终端字符图）。
3. 支持用户操作命令（如添加图形、移动、撤销、重做）。
4. 支持图形访问器（例如导出图形结构为 JSON、XML）。
5. 具备插件代理机制（如远程渲染服务）。
三、任务要求
1. 功能要求
   • 图形对象定义（使用抽象工厂+工厂模式创建）
   • 渲染器接口与实现（使用桥接模式支持多种渲染方式）
   • 图形命令操作（使用命令模式实现操作和撤销）
   • 渲染服务代理（使用代理模式本地与远程渲染）
   • 监听模型变化（使用观察者模式）
   • 数据导出（访问者模式：导出为JSON、XML等格式）
   • 渲染器适配器（适配不同图形库）
   • 系统全局配置（使用单例模式提供共享配置）
2. 技术要求
   • 使用 Java（建议 Java 11 或以上）
   • 使用 PowerDesigner 建模（类图、时序图）
   • 使用 JUnit 实现测试（单元测试、集成测试）
   • 使用 JavaDoc 或 Doxygen 自动生成文档
   • 提供完整可执行包和源代码

四、开发指导流程
第一步：需求分析与建模
• 使用 PowerDesigner 绘制用例图、类图、活动图
• 识别与映射各设计模式的使用点
• 输出《需求规格说明书》
第二步：系统设计
• 总体架构设计（模块划分、包结构）
• 详细设计：类图 + 设计模式注释
• 输出《总体设计说明书》和《详细设计说明书》
第三步：编码实现
• 使用 IDE（如 IntelliJ IDEA 或 Eclipse）
• 模块按设计模式划分子包，例如：
• com.example.graphics.command
com.example.graphics.factory
com.example.graphics.render
...
• 实现必要接口与类，确保解耦与扩展性
第四步：测试与调试
• 使用 JUnit 编写测试用例
◦ 单元测试：每个模式和模块独立测试
◦ 集成测试：渲染整体流程
第五步：文档生成与归档
• 使用 JavaDoc 或 Doxygen 生成完整 API 文档，集成到 maven 配置中（mvn site)
• 编写《安装说明书》和《用户手册》
第六步：项目发布
• 使用 git 发布到 github 或者 Gitee
• 使用 Aider 分析项目，补全缺少的单元测试。


五、预期成果
学生应提交如下材料：
1. 软件设计文档
◦ 《需求规格说明书》
◦ 《总体设计说明书》
◦ 《详细设计说明书》
◦ 《单元测试报告》
◦ 《集成测试报告》
2. 建模文件
◦ PowerDesigner 模型文件（.pdm/.cdm/.oom 等）
3. 完整源码包
◦ 含所有 .java 源文件、测试用例、资源文件等
4. 可执行文件
◦ JAR 或其他部署包
5. 技术文档
◦ 使用 JavaDoc 或 Doxygen 自动生成的 API 文档
◦ 安装说明书（含依赖、打包命令等）
◦ 用户手册（操作流程、截图、示例）
6. 项目结构要求
6. /graphic-rendering/
├── /doc/
│     ├── 需求规格说明书.docx
│     ├── 总体设计说明书.docx
│     ├── ...
├── /model/
│     ├── classDiagram.oom
├── /src/
│     ├── main/java/...
│     ├── test/java/...
├── /lib/
├── /javadoc/
├── build.gradle / pom.xml
└── README.md

六、评分标准（满分 100 分）
项目                权重                说明
设计模式应用         25                  是否覆盖9个指定模式，且实现合理
模型设计            15                  UML模型完整性与清晰度
编码实现            15                  模块清晰、命名规范、功能完整
测试覆盖            10                  是否实现单元测试、集成测试
技术文档            10                  JavaDoc 或 Doxygen 文档完整性
安装说明/使用手册    10                  可执行性、说明清晰
归档完整性          10                  所有文档、代码、模型齐全
项目演示            5                   运行无误、演示逻辑清晰


## 

📄 项目文档

Doxygen 生成的完整 API 文档见：[点击查看](https://yinglichina8848.github.io/GraphRenderRestructured/)

---

## 🚀 项目简介

这是一个采用 Maven 构建的图形渲染系统，结合 Java 和 C++，生成了 JavaDoc 和 Doxygen 两套文档，并整合到 Maven Site 中。

---

## 🧱 构建 & 安装

```bash
# 克隆仓库
git clone https://github.com/yinglichina8848/GraphRenderRestructured.git
cd GraphRenderRestructured

# 构建项目并生成文档
mvn clean site
```

成功后，你将在 `target/site/` 目录下看到：

- `apidocs/` —— JavaDoc 文档
- `doxygen/` —— Doxygen 生成的文档
- `index.html` —— Maven Site 总入口

---

## 📄 使用示例

```bash
java -jar target/graph-render-1.0-SNAPSHOT.jar
# 或
mvn exec:java -Dexec.mainClass="com.example.renderer.ui.MainUI"
```

---

## 🧭 文档访问

所有文档都已发布至 GitHub Pages / GitLab Pages，可以通过以下链接访问：

👉 [查看项目文档（Maven Site）](https://yinglichina8848.github.io/GraphRenderRestructured/)

---

## ⭐ 特性亮点

- ✅ JavaDoc（Java API 文档）
- ✅ Doxygen（C++ 代码结构 & 注释文档）
- ✅ Maven Site 集成，统一输出在 `target/site/`
- ✅ 可通过 GitHub Pages 自动部署

---

## 🤝 贡献指南

欢迎贡献代码、提交 Issue 或 PR：

1. Fork 本仓库
2. 创建 `feature-xxx` 分支
3. 完成功能或修复问题
4. 提交 PR 及补充测试 / 文档

---

## 📝 许可证

本项目采用 [MIT License](LICENSE) 开源，欢迎自由使用和修改。

---

## 📞 联系方式

对接问题或合作，欢迎联系我 ：`yinglichina@gmail.com`

