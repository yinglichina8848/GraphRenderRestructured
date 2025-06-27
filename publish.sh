#!/bin/bash
set -e

# === 配置 ===
SITE_DIR=target/site
DOXYGEN_HTML=docs/html
GH_PAGES_BRANCH=gh-pages
REPO_URL=git@github.com:yinglichina8848/GraphRenderRestructured.git
PUBLISH_DIR=gh-pages-publish
DOCS_SRC=doc
DOCS_HTML="$SITE_DIR/doc"
INDEX_HTML="$SITE_DIR/index.html"

# 当前分支
CURRENT_BRANCH=$(git symbolic-ref --short HEAD)

echo "🛠️ Step 1: Building Maven site and reports..."
mvn clean verify site

echo "📄 Step 2: Copying Doxygen HTML output if available..."
if [ -d "$DOXYGEN_HTML" ]; then
  mkdir -p "$SITE_DIR/doxygen"
  cp -r "$DOXYGEN_HTML"/* "$SITE_DIR/doxygen/"
else
  echo "⚠️ Warning: Doxygen output not found in $DOXYGEN_HTML"
fi

echo "📝 Step 3: Converting doc/*.md → HTML..."
mkdir -p "$DOCS_HTML"
for mdfile in "$DOCS_SRC"/*.md; do
  name=$(basename "$mdfile" .md)
  pandoc "$mdfile" -s -o "$DOCS_HTML/$name.html" --metadata title="$name"
  echo "✅ Converted: $mdfile → $DOCS_HTML/$name.html"
done

echo "📄 Step 3.5: Copying PDF files from doc/ to site/doc/..."
for pdffile in "$DOCS_SRC"/*.pdf; do
  cp "$pdffile" "$DOCS_HTML/"
  echo "✅ Copied: $pdffile → $DOCS_HTML/"
done

echo "📋 Step 4: Generating index.html..."
cat > "$INDEX_HTML" <<EOF
<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="UTF-8">
  <title>GraphRender 技术文档索引</title>
</head>
<body>
  <h1>📚 GraphRender 技术文档索引</h1>

  <h2>🔗 核心入口</h2>
  <ul>
    <li><a href="index.html">📂 文档主页（当前）</a></li>
    <li><a href="apidocs/index.html">📘 JavaDoc API</a></li>
    <li><a href="doxygen/index.html">📗 Doxygen 接口文档</a></li>
  </ul>
EOF

echo "  <h2>📄 PDF 技术文档</h2>" >> "$INDEX_HTML"
echo "  <ul>" >> "$INDEX_HTML"
for pdffile in "$DOCS_SRC"/*.pdf; do
  name=$(basename "$pdffile")
  echo "    <li><a href=\"doc/$name\">$name</a></li>" >> "$INDEX_HTML"
done
echo "  </ul>" >> "$INDEX_HTML"

echo "  <h2>📄 Markdown 文档（已转 HTML）</h2>" >> "$INDEX_HTML"
echo "  <ul>" >> "$INDEX_HTML"
for mdfile in "$DOCS_SRC"/*.md; do
  name=$(basename "$mdfile" .md)
  echo "    <li><a href=\"doc/$name.html\">$name</a></li>" >> "$INDEX_HTML"
done
echo "  </ul>" >> "$INDEX_HTML"

echo "  <h2>🧪 测试与分析报告</h2>" >> "$INDEX_HTML"
echo "  <ul>" >> "$INDEX_HTML"

# 先写核心常见报告
[ -f "$SITE_DIR/surefire-report.html" ] && echo "    <li><a href=\"surefire-report.html\">✅ 单元测试报告</a></li>" >> "$INDEX_HTML"
[ -f "$SITE_DIR/jacoco/index.html" ] && echo "    <li><a href=\"jacoco/index.html\">📊 覆盖率报告 (JaCoCo)</a></li>" >> "$INDEX_HTML"
[ -f "$SITE_DIR/dependencies.html" ] && echo "    <li><a href=\"dependencies.html\">📦 依赖报告</a></li>" >> "$INDEX_HTML"
[ -f "$SITE_DIR/scm.html" ] && echo "    <li><a href=\"scm.html\">🔖 版本管理报告</a></li>" >> "$INDEX_HTML"

# 其他各种报告
for report in modules.html licenses.html team.html ci-management.html issue-management.html summary.html checkstyle.html pmd.html cpd.html spotbugs.html dependency-check-report.html; do
  if [ -f "$SITE_DIR/$report" ]; then
    # 生成漂亮标题，首字母大写，空格替换短横线
    title=$(basename "$report" .html | sed 's/-/ /g' | awk '{for(i=1;i<=NF;i++)$i=toupper(substr($i,1,1)) substr($i,2)}1')
    echo "    <li><a href=\"$report\">📄 $title</a></li>" >> "$INDEX_HTML"
  fi
done

echo "  </ul>" >> "$INDEX_HTML"

echo "</body></html>" >> "$INDEX_HTML"
echo "✅ index.html generated."

echo "📁 Step 5: Cloning $GH_PAGES_BRANCH branch..."
rm -rf "$PUBLISH_DIR"
git clone --branch "$GH_PAGES_BRANCH" --depth 1 "$REPO_URL" "$PUBLISH_DIR"

echo "📦 Step 6: Replacing site content..."
rm -rf "$PUBLISH_DIR"/*
cp -r "$SITE_DIR"/* "$PUBLISH_DIR"

echo "✅ Step 7: Committing and pushing to GitHub Pages..."
cd "$PUBLISH_DIR"
git config user.name "GitHub Actions"
git config user.email "yinglichina@gmail.com"
git add .
git commit -m "📄 Auto-publish site on $(date +'%Y-%m-%d %H:%M:%S')" || echo "⚠️ Nothing to commit"
git push -f origin "$GH_PAGES_BRANCH"
cd ..

echo "🔄 Step 8: Switching back to original branch [$CURRENT_BRANCH]..."
git checkout "$CURRENT_BRANCH"

echo "🎉 Deployment complete!"
echo "🔗 View site at: https://yinglichina8848.github.io/GraphRenderRestructured/"

