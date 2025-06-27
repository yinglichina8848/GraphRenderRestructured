#!/bin/bash
set -e

# === 配置 ===
SITE_DIR=target/site
DOXYGEN_HTML=docs/html
GH_PAGES_BRANCH=gh-pages
WORKTREE_DIR=.gh-pages-worktree
DOCS_SRC=doc
DOCS_HTML="$SITE_DIR/doc"
INDEX_HTML="$SITE_DIR/index.html"

# 自动选择推送地址
if [ -n "$GITHUB_TOKEN" ]; then
  REPO_URL="https://x-access-token:${GITHUB_TOKEN}@github.com/yinglichina8848/GraphRenderRestructured.git"
else
  REPO_URL="git@github.com:yinglichina8848/GraphRenderRestructured.git"
fi

CURRENT_BRANCH=$(git symbolic-ref --short HEAD 2>/dev/null || echo "unknown")

echo "🛠️ Step 1: 构建 Maven Site 和测试报告..."
mvn clean verify site

echo "📄 Step 2: 拷贝 Doxygen HTML（如果存在）..."
if [ -d "$DOXYGEN_HTML" ]; then
  mkdir -p "$SITE_DIR/doxygen"
  cp -r "$DOXYGEN_HTML"/* "$SITE_DIR/doxygen/"
else
  echo "ℹ️ No Doxygen output found in $DOXYGEN_HTML"
fi

echo "📝 Step 3: 转换 doc/*.md → HTML..."
mkdir -p "$DOCS_HTML"
for mdfile in "$DOCS_SRC"/*.md; do
  name=$(basename "$mdfile" .md)
  pandoc "$mdfile" -s -o "$DOCS_HTML/$name.html" --metadata title="$name"
  echo "✅ Converted: $mdfile → $DOCS_HTML/$name.html"
done

echo "📄 Step 3.5: 复制 PDF 文件（如果有）..."
if compgen -G "$DOCS_SRC/*.pdf" > /dev/null; then
  cp "$DOCS_SRC"/*.pdf "$DOCS_HTML/"
  echo "✅ PDF files copied."
else
  echo "ℹ️ No PDF files found in $DOCS_SRC, skipping."
fi

echo "📋 Step 4: 生成 index.html..."
cat > "$INDEX_HTML" <<EOF
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>📚 GraphRender 文档索引</title>
</head>
<body>
  <h1>📚 GraphRender 技术文档索引</h1>

  <h2>🔗 核心入口</h2>
  <ul>
    <li><a href="index.html">📂 文档主页（当前）</a></li>
    <li><a href="apidocs/index.html">📘 JavaDoc API</a></li>
    <li><a href="doxygen/index.html">📗 Doxygen 接口文档</a></li>
  </ul>

  <h2>📘 更新与 AI 建议</h2>
  <ul>
    <li><a href="CHANGELOG.md">📝 更新记录 (CHANGELOG)</a></li>
    <li><a href="doc/ai_fix_suggestions.html">🤖 AI 改进建议</a></li>
  </ul>

  <h2>📄 PDF 文档</h2><ul>
EOF

for pdffile in "$DOCS_SRC"/*.pdf; do
  name=$(basename "$pdffile")
  echo "    <li><a href=\"doc/$name\">$name</a></li>" >> "$INDEX_HTML"
done 2>/dev/null

echo "  </ul><h2>📄 Markdown 文档（已转 HTML）</h2><ul>" >> "$INDEX_HTML"

for mdfile in "$DOCS_SRC"/*.md; do
  name=$(basename "$mdfile" .md)
  echo "    <li><a href=\"doc/$name.html\">$name</a></li>" >> "$INDEX_HTML"
done

echo "  </ul>" >> "$INDEX_HTML"
echo "  <h2>🧪 测试与分析报告</h2><ul>" >> "$INDEX_HTML"

# 映射：报告文件路径 → 中文标题 + 图标
declare -A report_map=(
  ["surefire-report.html"]="✅ 单元测试报告"
  ["jacoco/index.html"]="📊 JaCoCo 覆盖率"
  ["jacoco-aggregate/index.html"]="📊 JaCoCo 聚合覆盖率"
  ["checkstyle.html"]="🧹 Checkstyle 报告"
  ["pmd.html"]="🧽 PMD 检查"
  ["cpd.html"]="🔍 重复代码检查 (CPD)"
  ["spotbugs.html"]="🐞 SpotBugs 缺陷报告"
  ["dependency-check-report.html"]="🛡️ 依赖安全检查 (OWASP)"
  ["dependencies.html"]="📦 项目依赖分析"
  ["scm.html"]="🔗 版本控制信息 (SCM)"
  ["summary.html"]="📖 项目概览摘要"
)

for path in "${!report_map[@]}"; do
  if [ -f "$SITE_DIR/$path" ]; then
    echo "    <li><a href=\"$path\">${report_map[$path]}</a></li>" >> "$INDEX_HTML"
  fi
done

echo "  </ul>" >> "$INDEX_HTML"
echo "  </ul>" >> "$INDEX_HTML"
echo "</body></html>" >> "$INDEX_HTML"
echo "✅ index.html generated."

echo "📁 Step 5: 添加 git worktree 到 $WORKTREE_DIR..."
mkdir -p "$(dirname "$WORKTREE_DIR")"

if [ -d "$WORKTREE_DIR" ]; then
  echo "⚠️ $WORKTREE_DIR 已存在，移除旧 worktree..."
  git worktree remove "$WORKTREE_DIR" --force || true
fi

git fetch origin "$GH_PAGES_BRANCH"
git worktree add "$WORKTREE_DIR" origin/"$GH_PAGES_BRANCH"

echo "📦 Step 6: 替换 gh-pages 内容..."
rm -rf "$WORKTREE_DIR"/*
cp -r "$SITE_DIR"/* "$WORKTREE_DIR"

echo "✅ Step 7: 提交并推送更新..."
cd "$WORKTREE_DIR" || { echo "❌ Failed to enter worktree"; exit 1; }

git config user.name "GitHub Actions"
git config user.email "actions@github.com"
git add .

git commit -m "📄 Auto-publish site on $(date -u +'%Y-%m-%dT%H:%M:%SZ')" || echo "ℹ️ Only update static HTML document. Nothing to commit."

git push -f origin HEAD:gh-pages

cd -

echo "🧹 清理 worktree..."
git worktree remove "$WORKTREE_DIR" --force || true
#rm -rf "$WORKTREE_DIR"

echo "🎉 发布完成！访问地址：https://yinglichina8848.github.io/GraphRenderRestructured/"

