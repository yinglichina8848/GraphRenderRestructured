#!/bin/bash
set -e

# === 配置 ===
SITE_DIR=target/site
DOXYGEN_HTML=docs/html
GH_PAGES_BRANCH=gh-pages
REPO_URL=git@github.com:yinglichina8848/GraphRenderRestructured.git
PUBLISH_DIR=gh-pages-publish
DOCS_SRC=doc          # ← 注意：这是你的原始目录
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
  <ul>
    <li><a href="doxygen/index.html">Doxygen 文档首页</a></li>
    <li><a href="apidocs/index.html">JavaDoc API 文档</a></li>
EOF

echo "  <h2>🧪 自动测试与分析报告</h2>" >> "$INDEX_HTML"
echo "  <ul>" >> "$INDEX_HTML"

# 单元测试
if [ -f "$SITE_DIR/surefire-report.html" ]; then
  echo "    <li><a href=\"surefire-report.html\">✅ 单元测试结果 (Surefire Report)</a></li>" >> "$INDEX_HTML"
fi

# 覆盖率报告
if [ -f "$SITE_DIR/jacoco/index.html" ]; then
  echo "    <li><a href=\"jacoco/index.html\">📊 覆盖率报告 (JaCoCo)</a></li>" >> "$INDEX_HTML"
fi

# 依赖分析
if [ -f "$SITE_DIR/dependencies.html" ]; then
  echo "    <li><a href=\"dependencies.html\">📦 依赖关系分析 (Maven)</a></li>" >> "$INDEX_HTML"
fi

echo "  </ul>" >> "$INDEX_HTML"

# 添加 PDF 链接
PDF_FILES=$(find "$DOCS_SRC" -name "*.pdf")
if [ -n "$PDF_FILES" ]; then
  echo "  </ul>" >> "$INDEX_HTML"
  echo "  <h2>📄 PDF 技术手册</h2>" >> "$INDEX_HTML"
  echo "  <ul>" >> "$INDEX_HTML"
  for pdffile in $PDF_FILES; do
    name=$(basename "$pdffile")
    echo "    <li><a href=\"doc/$name\">$name</a></li>" >> "$INDEX_HTML"
  done
  echo "  </ul>" >> "$INDEX_HTML"
fi

# 添加 HTML 链接
echo "  <h2>📄 Markdown 技术文档 (已转 HTML)</h2>" >> "$INDEX_HTML"
echo "  <ul>" >> "$INDEX_HTML"
for mdfile in "$DOCS_SRC"/*.md; do
  name=$(basename "$mdfile" .md)
  echo "    <li><a href=\"doc/$name.html\">$name</a></li>" >> "$INDEX_HTML"
done

echo "  </ul>" >> "$INDEX_HTML"
echo "</body></html>" >> "$INDEX_HTML"
echo "✅ index.html generated with PDF + MD links."

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

