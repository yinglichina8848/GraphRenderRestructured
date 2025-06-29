#!/bin/bash
set -e

SITE_DIR=target/site
DOXYGEN_HTML=docs/html
GH_PAGES_BRANCH=gt-pages
REPO_URL=git@github.com:yinglichina8848/GraphRenderRestructured.git
PUBLISH_DIR=gh-pages-publish

echo "🛠️ Step 1: Building Maven site and reports..."
mvn clean verify site
mkdir -p $PUBLISH_DIR

echo "📄 Step 2: Copying Doxygen HTML output if available..."
if [ -d "$DOXYGEN_HTML" ]; then
  mkdir -p "$SITE_DIR/doxygen"
  cp -r "$DOXYGEN_HTML"/* "$SITE_DIR/doxygen/"
else
  echo "⚠️ Warning: Doxygen output not found in $DOXYGEN_HTML"
fi

echo "📦 Step 5: Copying new site contents to $GH_PAGES_BRANCH..."
cp -r "$SITE_DIR"/* "$PUBLISH_DIR"

echo "✅ Step 6: Committing and pushing to GitHub Pages..."
cd "$PUBLISH_DIR"
git config user.name "GitHub Actions"
git config user.email "yinglichina@163.com"
git add .
git commit -m "Auto-publish site $(date +'%Y-%m-%d %H:%M:%S')" || echo "Nothing to commit"
#git push gitee "$GH_PAGES_BRANCH"
git push gitee "$GH_PAGES_BRANCH"

echo "🎉 Deployment complete: https://yinglichina8848.github.io/GraphRenderRestructured/"
