# GraphRenderRestructured
![Build](https://github.com/yinglichina8848/GraphRenderRestructured/actions/workflows/quality.yml/badge.svg)
[![codecov](https://codecov.io/gh/yinglichina8848/GraphRenderRestructured/branch/main/graph/badge.svg)](https://codecov.io/gh/yinglichina8848/GraphRenderRestructured)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=yinglichina8848_GraphRenderRestructured&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=yinglichina8848_GraphRenderRestructured)


# 软件工程期末大作业：图形渲染系统的设计与实现

# 🚀 一、项目简介

本项目旨在通过设计并实现一个初步的图形渲染系统，综合运用以下9种典型设计模式：

- • 创建型模式：
- ◦ Factory（工厂）
- ◦ Abstract Factory（抽象工厂）
- ◦ Singleton（单例）
- • 结构型模式：
- ◦ Adapter（适配器）
- ◦ Bridge（桥接）
- ◦ Proxy（代理）
- • 行为型模式：
- ◦ Command（命令）
- ◦ Visitor（访问者）
- ◦ Observer（观察者）
- 

# 🚀 二、项目目标
设计并实现一个简化的图形渲染系统，具备以下功能：
-1. 渲染基本图形元素（如圆形、矩形、线段）。
-2. 支持不同的渲染引擎（如 SVG、Canvas、终端字符图）。
-3. 支持用户操作命令（如添加图形、移动、撤销、重做）。
-4. 支持图形访问器（例如导出图形结构为 JSON、XML）。
-5. 具备插件代理机制（如远程渲染服务）。


# 🚀 三、任务要求

- 1. 功能要求
-    • 图形对象定义（使用抽象工厂+工厂模式创建）
-    • 渲染器接口与实现（使用桥接模式支持多种渲染方式）
-    • 图形命令操作（使用命令模式实现操作和撤销）
-    • 渲染服务代理（使用代理模式本地与远程渲染）
-    • 监听模型变化（使用观察者模式）
-    • 数据导出（访问者模式：导出为JSON、XML等格式）
-    • 渲染器适配器（适配不同图形库）
-    • 系统全局配置（使用单例模式提供共享配置）
- 2. 技术要求
-    • 使用 Java（建议 Java 11 或以上）
-    • 使用 PowerDesigner 建模（类图、时序图）
-    • 使用 JUnit 实现测试（单元测试、集成测试）
-    • 使用 JavaDoc 或 Doxygen 自动生成文档
-    • 提供完整可执行包和源代码
- 

# 🚀 四、开发指导流程
## 第一步：需求分析与建模
###• 使用 PowerDesigner 绘制用例图、类图、活动图
###• 识别与映射各设计模式的使用点
###• 输出《需求规格说明书》
## 第二步：系统设计
###• 总体架构设计（模块划分、包结构）
###• 详细设计：类图 + 设计模式注释
###• 输出《总体设计说明书》和《详细设计说明书》
## 第三步：编码实现
###• 使用 IDE（如 IntelliJ IDEA 或 Eclipse）
###• 模块按设计模式划分子包，例如：
###• com.example.graphics.command
     com.example.graphics.factory
     com.example.graphics.render
...

###• 实现必要接口与类，确保解耦与扩展性
## 第四步：测试与调试
###• 使用 JUnit 编写测试用例
###◦ 单元测试：每个模式和模块独立测试
###◦ 集成测试：渲染整体流程
## 第五步：文档生成与归档
###• 使用 JavaDoc 或 Doxygen 生成完整 API 文档，集成到 maven 配置中（mvn site)
###• 编写《安装说明书》和《用户手册》
## 第六步：项目发布
###• 使用 git 发布到 github 或者 Gitee
###• 使用 Aider 分析项目，补全缺少的单元测试。


# 🚀 五、预期成果
学生应提交如下材料：
##1. 软件设计文档

- ◦ 《需求规格说明书》
- ◦ 《总体设计说明书》
- ◦ 《详细设计说明书》
- ◦ 《单元测试报告》
- ◦ 《集成测试报告》

##2. 建模文件
- ◦ PowerDesigner 模型文件（.pdm/.cdm/.oom 等）
##3. 完整源码包
- ◦ 含所有 .java 源文件、测试用例、资源文件等
##4. 可执行文件
- ◦ JAR 或其他部署包
##5. 技术文档

- ◦ 使用 JavaDoc 或 Doxygen 自动生成的 API 文档
- ◦ 安装说明书（含依赖、打包命令等）
- ◦ 用户手册（操作流程、截图、示例）

##6. 项目结构要求

-     6. /graphic-rendering/
-     ├── /doc/
-     │     ├── 需求规格说明书.docx
-     │     ├── 总体设计说明书.docx
-     │     ├── ...
-     ├── /model/
-     │     ├── classDiagram.oom
-     ├── /src/
-     │     ├── main/java/...
-     │     ├── test/java/...
-     ├── /lib/
-     ├── /javadoc/
-     ├── build.gradle / pom.xml
-     └── README.md
- 
    
# 🚀 六、评分标准（满分 100 分）
### 项目                权重                说明
### 设计模式应用         25                  是否覆盖9个指定模式，且实现合理
### 模型设计            15                  UML模型完整性与清晰度
### 编码实现            15                  模块清晰、命名规范、功能完整
### 测试覆盖            10                  是否实现单元测试、集成测试
### 技术文档            10                  JavaDoc 或 Doxygen 文档完整性
### 安装说明/使用手册    10                  可执行性、说明清晰
### 归档完整性          10                  所有文档、代码、模型齐全
### 项目演示            5                   运行无误、演示逻辑清晰

---


## 

#📄 项目文档

-  完整的项目文档：https://yinglichina8848.github.io/GraphRenderRestructured

-  Doxygen 生成的完整 API 文档见：https://yinglichina8848.github.io/GraphRenderRestructured/html/index.html

-  软件质量报告：https://yinglichina8848.github.io/GraphRenderRestructured/doc/quality-summary.html

-  软件变更报告：https://yinglichina8848.github.io/GraphRenderRestructured/doc/CHANGELOG.html

-  软件版本说明：https://yinglichina8848.github.io/GraphRenderRestructured/doc/MYCHANGELOG.html

---

- JavaDoc 生成的完整 API 文档见：https://apidoc.gitee.com/yinglichina/graph-render-restructured

- Gitee 由于 Gitee Pages 服务关闭，所以无法看 Doxygen 在线文档，可以根据下面的教程在本地生成（mvn clean site).


# 📄 PDF 文档：记录整个软件的开发过程，主要是和各种 AI 工具的对话过程

- [图形渲染环境设计报告.pdf](https://yinglichina8848.github.io/GraphRenderRestructured/doc/%E5%9B%BE%E5%BD%A2%E6%B8%B2%E6%9F%93%E7%8E%AF%E5%A2%83%E8%AE%BE%E8%AE%A1%E6%8A%A5%E5%91%8A.pdf)
- [Deepseek对话过程.pdf](https://yinglichina8848.github.io/GraphRenderRestructured/doc/Deepseek%E5%AF%B9%E8%AF%9D%E8%BF%87%E7%A8%8B.pdf
- [ChatGPT-GraphRender项目问答全过程.pdf](https://yinglichina8848.github.io/GraphRenderRestructured/doc/ChatGPT-GraphRender%E9%A1%B9%E7%9B%AE%E9%97%AE%E7%AD%94%E5%85%A8%E8%BF%87%E7%A8%8B.pdf)
- [ChatGPT对话过程.pdf](https://yinglichina8848.github.io/GraphRenderRestructured/doc/ChatGPT%E5%AF%B9%E8%AF%9D%E8%BF%87%E7%A8%8B.pdf)


# 🚀 项目简介

这是一个采用 AI + IDEA + Java 构建的图形渲染系统，结合多种设计模式（9种），整个系统完全在 Ubuntu 22 图形环境下开发，历时3天，展示了开源环境和软件的性能。
整个开发环境配置了魔法梯子（使用 ChatGPT，安装 IDEA 工具及 AI 插件，以及 Aider 和 Github 环境），后续迁移到 国内的 Gitee 环境。

doc 目录下的3个PDF 文件记录了图形渲染环境的设计过程。
基本框架是通过AI 对话得到的各个源码文件，然后用 IDEA 建立的 基于 Maven 的 Java 项目（JDK 21）。
然后使用了 Aider + Deepseek 对整个项目进行了优化，单元测试补全，注释补全，也使用了 IDEA 环境的 通义灵码纠正编译错误和 JavaDoc 的生成错误。

大家在开发过程中遇到问题，建议使用 Deepseek 或者 ChatGPT 进行排错。
整个项目的开发，耗时3天（6月14 22：00 -16日 20：00），这是最终的结果。期间配置 Aider 使用 Deepseek 进行开发，API 方式充值10元，额度已经基本用完。
还有个别的单元测试需要改进，部分测试代码的注释尚未完全补全（可以使用 IDEA + 通义灵码逐步补全每个文件，但是过于烦琐，本项目是示例程序，已经足够说明AI编程的能力）。

---

## 🧱 构建 & 安装
```bash
# 克隆仓库（Github 版本）
git clone https://github.com/yinglichina8848/GraphRenderRestructured.git
cd GraphRenderRestructured

# 构建项目并生成文档
mvn clean package site
#成功后，你将在 `target/site/` 目录下看到：
- `apidocs/` —— JavaDoc 文档
- `doxygen/` —— Doxygen 生成的文档
- `index.html` —— Maven Site 总入口


---
# 克隆 Gitee 仓库（将原 GitHub 地址替换为 Gitee 地址）
```bash
git clone https://gitee.com/yinglichina/graph-render-restructured.git

cd graph-render-restructured

# 构建项目并生成文档（如 Maven Site 或 Doxygen）
mvn clean package site

#成功后，你将在 `target/site/` 目录下看到：

`apidocs/` —— JavaDoc 文档
`doxygen/` —— Doxygen 生成的文档
`index.html` —— Maven Site 总入口
```

---
## 📄 使用示例

```bash
mvn package
# 
java -jar target/graph-render-1.0-SNAPSHOT.jar
# 或
mvn exec:java -Dexec.mainClass="com.example.renderer.ui.MainUI"
```
#运行时需要 Java 图形环境。

---
## 🧭 文档访问

所有文档都已发布至 GitHub Pages / GitLab Pages，可以通过以下链接访问：
##
👉 [查看项目(GitHub)]    https://yinglichina8848.github.io/GraphRenderRestructured

##
👉 [查看项目(Gitee)]    https://apidoc.gitee.com/yinglichina/graph-render-restructured


## ⭐ 特性亮点

- ✅ JavaDoc（Java API 文档）
- ✅ Doxygen（C++ 代码结构 & 注释文档）
- ✅ Maven Site 集成，统一输出在 `target/site/`
- ✅ 可通过 GitHub Pages 自动部署
- ✅ 集成各种测试和文档报告工具，更改日志和软件质量分析报告自动生成
- ✅ 集成单元测试工具，单元测试代码自动生成
- ✅ 集成同步发布功能，一次提交，自动同步到 Github 和 Gitee 
- ✅ 云端文档自动生成和发布，main 分支的任何代码提交，会自动触发云端编译、测试和报告生成，并自动发布到 gh-pages 分支，以 Github Pages 网页的形式展示。
- ✅ 语义代码注释和版本集成，自动抽取函数注释，错误修正，生成版本变更记录，并生成版本号。
- ✅ AI 注释补全和测试代码生成。


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

贵州民族大学软件工程专业的同学，如果在复现项目中遇到问题，欢迎通过邮件或者 QQ 联系我 ：`1820393151@qq.com` 

![Build](https://github.com/yinglichina8848/GraphRenderRestructured/actions/workflows/quality.yml/badge.svg)
[![codecov](https://codecov.io/gh/yinglichina8848/GraphRenderRestructured/branch/main/graph/badge.svg)](https://codecov.io/gh/yinglichina8848/GraphRenderRestructured)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=yinglichina8848_GraphRenderRestructured&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=yinglichina8848_GraphRenderRestructured)

---

# 🎨 ChatGPT 对 GraphRenderRestructured 的介绍

- GraphRenderRestructured 是一个基于设计模式构建的图形渲染系统，使用 Java 实现，重点展示了建模能力、架构设计与自动化工程能力。

- 该项目不仅实现了功能完整的图形渲染逻辑，还配置了完整的 CI/CD 自动化构建、测试、版本控制和文档发布流程。

> 🔗 在线页面：  
- 📘 [项目首页 (GitHub Pages)](https://yinglichina8848.github.io/GraphRenderRestructured/)  
- 📊 [软件工程质量评估报告 HTML](https://yinglichina8848.github.io/GraphRenderRestructured/doc/quality-summary.html)
- 📊 [ChatGPT 总结的软件工程质量评估报告 HTML](https://yinglichina8848.github.io/GraphRenderRestructured/doc/quality_summary.html)

---

## 🗂 项目结构概览

```bash
GraphRenderRestructured/
├── src/                   # Java 模块（建造器、图形结构、渲染引擎等）
├── uml/                   # 类图、时序图、系统设计等 PNG 结构图（暂缺，需要整理补充）
├── doc/                   # PDF 报告、发布摘要、最终说明文档
├── scripts/               # 发布脚本（含 Jinja2 渲染、美化 index）
├── target/site/           # Maven Site 构建输出（含质量报告）
├── .github/workflows/     # 自动构建发布的 CI/CD 脚本
└── pom.xml                # Maven 配置文件
```

---

## 🚀 快速开始

### 1. 克隆项目

```bash
git clone https://github.com/yinglichina8848/GraphRenderRestructured.git
cd GraphRenderRestructured
```

国内学生请使用 Gitee 
```bash
#克隆 Gitee 仓库（将原 GitHub 地址替换为 Gitee 地址）
git clone https://gitee.com/yinglichina/graph-render-restructured.git
cd graph-render-restructured
```

### 2. 编译构建 + 文档生成

```bash
mvn clean package site
```

### 3. 查看本地文档
```bash
`target/site/index.html`：主页汇总
`target/site/apidocs/`：Java API 文档
`target/site/surefire-report.html`：单元测试报告
`target/site/checkstyle.html`：代码风格分析
`target/site/project-info.html`：依赖关系分析
```
### 4. 运行(需要 Java 图形环境)。
```bash
java -jar target/graph-render-1.0-SNAPSHOT.jar
# 或
mvn exec:java -Dexec.mainClass="com.example.renderer.ui.MainUI"
```


## ✅ 软件工程实践亮点

### 📐 架构设计与设计模式

- 使用 **建造者模式、桥接模式、访问者模式** 等实现图形抽象与渲染解耦
- 支持图形的组合、图层控制、颜色填充与导出等功能
- Java 管理数据模型

### 🤖 自动构建与部署（CI/CD）

- 使用 GitHub Actions 自动：
   - 构建项目
   - 运行测试
   - 生成并部署 JavaDoc、Doxygen、Maven Site 到 GitHub Pages
- 集成 `semantic-release` 实现语义化版本控制与自动化发布流程

### 🔍 测试与质量控制

- 单元测试：覆盖核心模块 `ShapeBuilder`、`PersistenceManager` 等
- 静态分析：Checkstyle 风格检查、依赖结构报告、构建日志分析
- 发布报告集成版本、改动、变更日志与质量报告链接

---

## 📈 版本发布历史

| 版本号 | 发布说明 |
|--------|----------|
| [v1.0.1](https://github.com/yinglichina8848/GraphRenderRestructured/releases/tag/v1.0.1) | 增加测试用例、补全抽象方法，完善 UML 结构图 |
| [v1.0.0](https://github.com/yinglichina8848/GraphRenderRestructured/releases/tag/v1.0.0) | 完整系统设计与功能实现，发布初版 API 与文档 |

- 简要的版本说明 请见 [Release 页面](https://yinglichina8848.github.io/GraphRenderRestructured/doc/MYCHANGELOG.html)。
- 完整变更记录和详细版本历史 请见 [CHANGELOG 变更记录](https://yinglichina8848.github.io/GraphRenderRestructured/doc/CHANGELOG.html)。

---

## 🛠 改进建议（未来工作）

- [ ] 增加功能测试用例，集成 Codecov 报告覆盖率
- [ ] 自动提取 UML 类图到文档导航页（可使用 PlantUML）
- [ ] 支持导出 SVG / PNG / JSON 的图形持久化格式
- [ ] 进一步模块化渲染器接口，便于扩展 WebGL 或 Canvas 后端

---

## 🤝 致谢与许可

- 本项目由 [李莹](https://github.com/yinglichina8848) 独立设计与实现，作为课程设计作品公开发布。
- 同步发布到 [Gitee] (https://gitee.com/yinglichina/graph-render-restructured) 请国内用户使用 Gitee 

---
 
- 📄 本项目遵循 MIT 许可证，欢迎学习、引用与修改。

> 💬 如你对本项目感兴趣，欢迎 Star ⭐、Fork 🔱 或提交 Pull Request！
> 
---
