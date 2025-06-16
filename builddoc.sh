#!/bin/bash

set -e  # 出错退出
PROJECT_NAME="graph-render-restructured"
SRC_DIR="src/main/java"
JAVADOC_OUT="docs/javadoc"

echo "📦 [1/6] 清理旧输出..."
rm -rf docs
mkdir -p docs "$JAVADOC_OUT"

echo "🧾 [2/6] 生成 Javadoc..."
javadoc -d "$JAVADOC_OUT" -sourcepath "$SRC_DIR" -subpackages com.example

echo "🛠️ [3/6] 生成 Doxygen 文档..."
doxygen Doxyfile

echo "📚 [4/6] 编译 LaTeX 为 PDF..."
cd docs/latex || { echo "❌ 未找到 latex 目录，请检查 Doxyfile 输出路径"; exit 1; }

# 检查是否已安装 pdflatex
if ! command -v pdflatex &> /dev/null; then
    echo "❗ 未找到 pdflatex，请先安装：sudo apt install texlive-full"
    exit 1
fi

make > /dev/null
cd ../..

# 重命名 PDF 并移到 docs 根目录
mv docs/latex/refman.pdf docs/${PROJECT_NAME}_doc.pdf

echo "🧼 [5/6] 清理中间文件..."
rm -rf docs/latex/*.aux docs/latex/*.log docs/latex/*.toc

echo "🚀 [6/6] 添加并提交到 Git（可选）"
git add docs/
git commit -m "生成文档：Javadoc + Doxygen + PDF"
git push origin main

echo "✅ 文档构建完成！结构如下："
echo "- Javadoc:    docs/javadoc/index.html"
echo "- Doxygen:    docs/html/index.html"
echo "- PDF:        docs/${PROJECT_NAME}_doc.pdf"

