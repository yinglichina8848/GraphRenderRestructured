#!/bin/bash
set -e

# 目录
PROJECT_DIR=$(pwd)
DOC_DIR="$PROJECT_DIR/doc"
REPORT_FILE="$DOC_DIR/aider_report.md"

echo "== Step 1: AI自动生成/补全单元测试和注释 =="
# 调用 aider CLI 对所有 java/js 文件生成测试和注释
find "$PROJECT_DIR" -type f \( -name "*.java" -o -name "*.js" \) | while read -r file; do
  echo "Processing $file ..."
  # 这里假设 aider 有个命令 auto-improve 支持生成测试和注释
  # 请根据实际 aider CLI 修改命令
  aider auto-improve --file "$file" >> "$REPORT_FILE" 2>&1 || echo "Warn: AI提升失败 $file"
done

echo "== Step 2: 编译并执行测试 =="
mvn clean verify

echo "== Step 3: 测试失败，调用 AI 自动修复 =="
if [ $? -ne 0 ]; then
  echo "Tests failed, collecting logs..."
  mvn test > test.log 2>&1 || true

  # 假设有个脚本用 AI 解析 test.log 并自动修复代码
  ./ai_fix_code.sh test.log

  echo "== Step 4: 代码修复后，重新编译测试 =="
  mvn clean verify
fi

echo "== Step 5: 生成最终报告 =="
# 生成 markdown 报告供审阅
echo "# Aider 扫描和改进报告" > "$REPORT_FILE"
date >> "$REPORT_FILE"
# 假设有日志或扫描结果文件合并
cat test.log >> "$REPORT_FILE"

echo "== Step 6: Git提交代码 =="
git config user.name "github-actions"
git config user.email "actions@github.com"
git add .
git commit -m "🤖 AI 自动改进单元测试和注释" || echo "Nothing to commit"
git push origin main

echo "== Step 7: 发布文档 =="
./publish.sh

echo "== 流水线结束，已自动提交并发布 =="

