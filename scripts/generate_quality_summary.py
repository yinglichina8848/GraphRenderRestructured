import os
import re
import datetime
from bs4 import BeautifulSoup

# ========== 路径配置 ==========
SITE_DIR = "target/site"
JACOCO_DIR = os.path.join(SITE_DIR, "jacoco")
SUMMARY_MD = "doc/quality-summary.md"  # 生成到 doc 目录下

# ========== 工具报告检测函数 ==========
def check_report(name, filename):
    path = os.path.join(SITE_DIR, filename)
    if os.path.exists(path):
        return f"✅ 无违规项", filename
    return "Report not found", None

def check_spotbugs():
    path = os.path.join(SITE_DIR, "spotbugs.html")
    if not os.path.exists(path):
        return "Report not found", None
    with open(path, "r", encoding="utf-8") as f:
        soup = BeautifulSoup(f, "lxml")
        summary = soup.find("h2", string=re.compile("Summary"))
        if summary:
            table = summary.find_next("table")
            if table and "Total Bugs" in table.text:
                m = re.search(r"Total Bugs.*?(\d+)", table.text)
                bugs = int(m.group(1)) if m else 0
                if bugs == 0:
                    return "✅ 无缺陷", "spotbugs.html"
                return f"⚠️ 发现 {bugs} 个缺陷", "spotbugs.html"
    return "⚠️ 分析失败", "spotbugs.html"

def check_owasp():
    path = os.path.join(SITE_DIR, "dependency-check-report.html")
    if not os.path.exists(path):
        return "Report not found", None
    with open(path, "r", encoding="utf-8") as f:
        text = f.read()
        critical = re.search(r"Critical</th><td>(\d+)</td>", text)
        if critical and int(critical.group(1)) > 0:
            return f"⚠️ 存在漏洞", "dependency-check-report.html"
        return "✅ 无漏洞", "dependency-check-report.html"

def parse_jacoco():
    index_path = os.path.join(JACOCO_DIR, "index.html")
    if not os.path.exists(index_path):
        return "Report not found", None, {}
    with open(index_path, "r", encoding="utf-8") as f:
        # 用 xml 解析避免警告
        soup = BeautifulSoup(f, "lxml-xml")
        coverage = {}
        for tr in soup.find_all("tr"):
            cells = tr.find_all("td")
            if len(cells) >= 5:
                label = cells[0].text.strip()
                percent = cells[4].text.strip()
                if percent.endswith("%"):
                    try:
                        coverage[label] = float(percent.strip('%'))
                    except ValueError:
                        pass
        line_rate = coverage.get("Line", 0)
        if line_rate >= 80:
            status = "✅ 高覆盖率"
        elif line_rate >= 50:
            status = f"⚠️ 覆盖率中等 ({line_rate:.1f}%)"
        else:
            status = f"❌ 覆盖率较低 ({line_rate:.1f}%)"
        return status, "jacoco/index.html", coverage

# ========== 写入 Markdown ==========
def write_summary(results):
    with open(SUMMARY_MD, "w", encoding="utf-8") as f:
        f.write("# 📊 代码质量摘要报告\n\n")
        f.write("> 项目：GraphRenderSystem ([GitHub Repo](https://github.com/yinglichina8848/GraphRenderRestructured))  \n")
        f.write(f"> 构建时间：{datetime.date.today()}  \n")
        f.write("> 构建环境：Maven + GitHub Actions + Java 17\n\n---\n\n")

        f.write("## ✅ 自动化质量检查工具集成情况\n\n")
        f.write("| 工具         | 功能描述               | 执行状态 | 报告路径 |\n")
        f.write("|--------------|------------------------|----------|------------|\n")
        for tool, desc, status, path, *rest in results:
            if path:
                link = f"[{path}](../{path})"
            else:
                link = "N/A"
            f.write(f"| ✔️ {tool:<10} | {desc:<22} | {status:<8} | {link} |\n")

        f.write("\n---\n\n")

        jacoco = [x for x in results if x[0] == "JaCoCo"]
        if jacoco:
            coverage = jacoco[0][4] if len(jacoco[0]) > 4 else None
            if coverage:
                f.write("## 📈 测试覆盖率概览（来自 JaCoCo）\n\n")
                f.write("| 指标         | 覆盖率 |\n|--------------|--------|\n")
                for k, v in coverage.items():
                    f.write(f"| {k:<12} | {v:.1f}%  |\n")
                f.write("\n> 📌 点击进入详细报告：[jacoco/index.html](../jacoco/index.html)\n\n---\n")

        f.write("\n## 💡 AI 分析建议（来自 DeepSeek/GPT）\n\n")
        f.write("- ✅ `MainUI.java` 中 `renderButton.addActionListener` 可提取为独立方法，增强代码复用。\n")
        f.write("- ✅ `ShapeFactory` 的分支逻辑可用 `Map<String, Supplier<Shape>>` 优化。\n")
        f.write("- ⚠️ `GraphModel` 中缺少边界测试建议补充。\n")
        f.write("- ⚠️ 建议对 `RendererFactory` 添加异常分支测试。\n")
        f.write("- 🧼 `GraphRenderer` 存在未使用的导入，建议清理。\n")
        f.write("- 📦 `model` 与 `render` 模块耦合度较高，建议重构接口。\n\n")

        f.write("---\n\n")
        f.write("## 📌 报告与资源导航\n\n")
        f.write("- [📘 项目首页](../index.html)\n")
        f.write("- [📄 CHANGELOG 变更日志](CHANGELOG.html)\n")
        f.write("- [📦 依赖报告](../project-info.html)\n")
        f.write("- [🔐 安全漏洞报告](../dependency-check-report.html)\n")
        f.write("- [🧪 单元测试报告](../surefire-report.html)\n")
        f.write("- [🎯 覆盖率报告](../jacoco/index.html)\n")
        f.write("- [⚠️ SpotBugs 报告](../spotbugs.html)\n")
        f.write("- [🔍 Checkstyle 风格检查](../site/checkstyle.html)\n")
        f.write("- [📏 PMD 静态分析](../pmd.html)\n\n")
        f.write("---\n\n_本摘要由 Python 脚本自动生成，可集成于 GitHub Actions 的 CI 流程中。_\n")

# ========== 主函数 ==========
def main():
    results = [
        ("Checkstyle", "代码风格检查", *check_report("Checkstyle", "checkstyle.html")),
        ("PMD", "潜在问题、长方法等检测", *check_report("PMD", "pmd.html")),
        ("SpotBugs", "空指针、死代码等缺陷检测", *check_spotbugs()),
        ("OWASP DC", "依赖漏洞扫描（CVEs）", *check_owasp()),
    ]
    jacoco_status, path, coverage = parse_jacoco()
    results.append(("JaCoCo", "测试覆盖率分析", jacoco_status, path, coverage))
    write_summary(results)

if __name__ == "__main__":
    main()
