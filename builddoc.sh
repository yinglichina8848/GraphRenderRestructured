#!/bin/bash

set -e  # 遇错退出
PROJECT_NAME="GraphRenderRestructured"
SRC_DIR="src"
JAVADOC_OUT="docs/javadoc"
BUILD_TMP_DIR="$(mktemp -d)"
GH_PAGES_DIR="$BUILD_TMP_DIR/gh-pages"

echo "📦 [1/6] 清理旧输出..."
rm -rf docs
mkdir -p docs "$JAVADOC_OUT"

echo "🧾 [2/6] 生成 Javadoc..."
javadoc -d "$JAVADOC_OUT" -sourcepath "$SRC_DIR" -subpackages com.example

echo "🛠️ [3/6] 生成 Doxygen 文档..."
doxygen Doxygen

echo "📚 [4/6] 编译 LaTeX 为 PDF..."
cd docs/latex || { echo "❌ 未找到 latex 目录，请检查 Doxyfile 输出路径"; exit 1; }

# 检查是否已安装 pdflatex
if ! command -v pdflatex &> /dev/null; then
    echo "❗ 未找到 pdflatex，请先安装：sudo apt install texlive-full"
    exit 1
fi

make > /dev/null
cd ../..

mv docs/latex/refman.pdf docs/${PROJECT_NAME}_doc.pdf
rm -rf docs/latex/*.aux docs/latex/*.log docs/latex/*.toc

echo "🚀 [5/6] 切换到 gh-pages 分支并更新内容..."

# 记录当前分支
CURRENT_BRANCH=$(git rev-parse --abbrev-ref HEAD)

# 构建 gh-pages 内容临时目录
mkdir -p "$GH_PAGES_DIR"
cp -r docs/* "$GH_PAGES_DIR"

# 切换到 gh-pages 分支（不存在则创建）
if git show-ref --quiet refs/heads/gh-pages; then
    git checkout gh-pages
else
    git checkout --orphan gh-pages
    git rm -rf . > /dev/null 2>&1 || true
fi

# 清空当前 gh-pages 并复制新文档
#rm -rf *
cp -r "$GH_PAGES_DIR"/* .

# 提交并推送
git add .
git commit -m "📖 更新文档：Javadoc + Doxygen + PDF"
git push -u github gh-pages

# 切换回原来的开发分支
git checkout "$CURRENT_BRANCH"

echo "✅ 文档已部署到 gh-pages 分支！"
echo "- Javadoc:    https://<your-username>.github.io/${PROJECT_NAME}/javadoc/index.html"
echo "- Doxygen:    https://<your-username>.github.io/${PROJECT_NAME}/html/index.html"
echo "- PDF:        https://<your-username>.github.io/${PROJECT_NAME}/${PROJECT_NAME}_doc.pdf"

