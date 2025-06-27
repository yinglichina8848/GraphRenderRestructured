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
#mvn clean verify site

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
PDF_FILES=("$DOCS_SRC"/*.pdf)
if [ -e "${PDF_FILES[0]}" ]; then
  for pdffile in "${PDF_FILES[@]}"; do
    cp "$pdffile" "$DOCS_HTML/"
    echo "✅ Copied: $pdffile → $DOCS_HTML/"
  done
else
  echo "ℹ️ No PDF files found in $DOCS_SRC, skipping."
fi

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

