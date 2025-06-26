#!/bin/bash
# 文件名: generate-docs.sh
# 用法: ./generate-docs.sh

DOC_DIR="doc"
SRC_DIR="src"

mkdir -p "$DOC_DIR"

echo "📦 正在启动 Aider 文档生成任务..."

# 启动 Aider 并执行批量操作
aider --model=deepseek/deepseek-coder:instruct \
--system-message "你是一个经验丰富的软件工程师，请根据 Java 项目代码自动生成高质量的文档。" \
--input-files "$SRC_DIR/**/*.java" << 'EOF'

请完成以下文档生成任务，并将结果写入 doc 目录下：

1. **doc/architecture.md**  
   - 描述系统整体架构：模块划分、职责说明
   - 使用 Mermaid 画出主要模块调用流程图

2. **doc/api.md**  
   - 为所有 Java 类生成 API 文档，包括方法说明、参数和返回值
   - 文档格式为 Markdown，适合开发者查阅

3. **doc/usage.md**  
   - 写出项目的使用说明，包括：如何运行主类、输入输出格式、示例命令

4. **doc/install.md**  
   - 自动分析是 Maven 或 Gradle，并生成安装+构建文档
   - 包括依赖安装、编译命令、常见问题等

5. **doc/changelog.md**  
   - 假设有 v0.1、v0.2、v1.0 三个阶段
   - 使用 Keep a Changelog 风格输出初始版本更新日志

6. **doc/contributing.md**  
   - 编写贡献指南，包括 Fork、分支策略、PR 流程、代码风格要求

请一次性完成所有任务。每份文档请用 Markdown 编写，放入 doc 目录。避免覆盖已有 README.md。

EOF

echo "✅ 文档生成完成，文件位于 ./$DOC_DIR/"

