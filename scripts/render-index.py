#!/usr/bin/env python3

import json
import os
import jinja2
from jinja2 import Environment, FileSystemLoader

# 加载模板目录
env = Environment(loader=FileSystemLoader('./templates'))
template = env.get_template('index.html.j2')

# 读取 package.json 中的版本号
try:
    with open('package.json', 'r') as f:
        pkg = json.load(f)
        version = pkg.get('version', 'unknown')
except Exception as e:
    print(f"⚠️ 无法读取版本号: {e}")
    version = 'unknown'

# --- 最近更新时间（使用最新 Git 提交时间） ---
try:
    commit_time_raw = subprocess.check_output(
        ["git", "log", "-1", "--format=%cd", "--date=iso"]
    ).decode().strip()
    # 转为本地格式
    commit_time = datetime.fromisoformat(commit_time_raw).strftime("%Y-%m-%d %H:%M")
except Exception as e:
    commit_time = "unknown"

# --- 最近一次提交摘要（hash + message） ---
try:
    commit_info = subprocess.check_output(
        ["git", "log", "-1", "--pretty=format:%h - %s"]
    ).decode().strip()
except Exception as e:
    commit_info = "unknown"


SITE_DIR = "target/site"
DOCS_DIR = "doc"
TEMPLATE_FILE = "templates/index.html.j2"
OUTPUT_FILE = os.path.join(SITE_DIR, "index.html")

# 报告映射：路径 → 中文描述（带图标）
REPORTS = {
    "surefire-report.html": "✅ 单元测试报告",
    "jacoco/index.html": "📊 JaCoCo 覆盖率",
    "jacoco-aggregate/index.html": "📊 JaCoCo 聚合覆盖率",
    "checkstyle.html": "🧹 Checkstyle 报告",
    "pmd.html": "🧽 PMD 检查",
    "cpd.html": "🔍 重复代码检查 (CPD)",
    "spotbugs.html": "🐞 SpotBugs 缺陷报告",
    "dependency-check-report.html": "🛡️ 依赖安全检查 (OWASP)",
    "dependencies.html": "📦 项目依赖分析",
    "scm.html": "🔗 版本控制信息 (SCM)",
    "summary.html": "📖 项目概览摘要"
}

# 收集 PDF 文件名
pdfs = [f for f in os.listdir(DOCS_DIR) if f.endswith(".pdf")]

# 收集 Markdown 文档名（不含扩展名）
docs = [os.path.splitext(f)[0] for f in os.listdir(DOCS_DIR) if f.endswith(".md")]


# 检查哪些报告实际存在
reports = []
for path, label in REPORTS.items():
    if os.path.exists(os.path.join(SITE_DIR, path)):
        reports.append({"path": path, "label": label})

# 渲染模板
env = jinja2.Environment(loader=jinja2.FileSystemLoader(searchpath="./"))
template = env.get_template(TEMPLATE_FILE)
output = template.render(
    version=version,
    last_updated=commit_time,
    last_commit=commit_info,
    pdfs=pdfs,
    docs=docs,
    reports=reports
    )

os.makedirs(SITE_DIR, exist_ok=True)
with open(OUTPUT_FILE, "w", encoding="utf-8") as f:
    f.write(output)


print(f"✅ index.html 渲染完成 → {OUTPUT_FILE}")
print(f"✅ 渲染完成：v{version} | {commit_info} @ {commit_time}")