# 📘 Changelog

本项目的所有可公开更改将记录于此文件中。

遵循 [Keep a Changelog](https://keepachangelog.com/zh-CN/1.0.0/) 和 [语义化版本控制](https://semver.org/lang/zh-CN/)。

---

## [Unreleased]

### ✨ 新增功能
- 集成 GitHub Actions 工作流，实现云端自动编译、测试、生成报告。
- 自动发布 `target/site` 中的静态文档至 `gh-pages` 分支。
- 添加 `publish.sh` 脚本，支持本地/CI 自动构建发布文档。
- 实现 Markdown → HTML 转换，文档索引页自动生成。

### 🧠 AI 提升
- 集成 Aider 脚本，辅助分析代码质量、自动补全注释与测试。
- 自动生成 `doc/ai_fix_suggestions.md` AI 建议报告。

### 🛠 持续集成改进
- 添加 `.github/workflows/auto_ai_workflow.yml` 支持自动发布。
- 自动生成 `CHANGELOG.md` 并集成进发布流程。
- 支持多格式代码质量分析：PMD、Checkstyle、JaCoCo、SpotBugs、OWASP Dependency Check。

---

## [v0.1.0] - 2025-06-27

### 🎉 初始版本
- Java + JavaScript 渲染核心代码结构搭建。
- 支持 JavaDoc 与 Doxygen 双文档系统。
- 上传类图、顺序图，文档结构标准化。

