#!/bin/bash
set -e

SITE_DIR=target/site
GH_PAGES_BRANCH=gh-pages
REPO_URL=git@github.com:yinglichina8848/GraphRenderRestructured.git

# 检查 site 是否已生成
if [ ! -d "$SITE_DIR" ]; then
  echo "Site not found. Run 'mvn site' first."
  exit 1
fi

# 创建临时目录
TEMP_DIR=$(mktemp -d)
cd "$TEMP_DIR"
git clone -b $GH_PAGES_BRANCH --single-branch "$REPO_URL" site-publish
cd site-publish

# 清空旧内容
rm -rf *

# 拷贝新内容
cp -r "$OLDPWD/$SITE_DIR"/* .

# 提交并推送
git config user.name "GitHub Actions"
git config user.email "actions@github.com"
git add .
git commit -m "Auto-publish site $(date +'%Y-%m-%d %H:%M:%S')" || echo "Nothing to commit"
git push origin $GH_PAGES_BRANCH

# 清理
cd ~
rm -rf "$TEMP_DIR"
echo "✅ Site deployed to GitHub Pages branch '$GH_PAGES_BRANCH'."

