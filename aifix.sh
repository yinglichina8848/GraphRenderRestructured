#!/bin/bash
set -e

LOGFILE=$1
if [ ! -f "$LOGFILE" ]; then
  echo "错误：缺少测试日志文件 $LOGFILE"
  exit 1
fi

REPORT_MD="doc/ai_fix_suggestions.md"

echo "# Aider 本地自动代码修复建议" > "$REPORT_MD"
echo "生成时间：$(date)" >> "$REPORT_MD"
echo "" >> "$REPORT_MD"

echo "提取测试失败信息..."
FAILURES=$(grep -A10 "FAILURE" "$LOGFILE" || true)

if [ -z "$FAILURES" ]; then
  echo "没有检测到测试失败，跳过修复。"
  exit 0
fi

echo "检测到测试失败，调用 Aider CLI 自动修复代码..."

# 假设 aider-cli 支持基于日志自动修复（示范命令，请根据你版本调整）
# 这里将日志内容传给 aider CLI 的 fix 功能，并输出结果到报告

echo "$FAILURES" > tmp_failures.txt

aider fix --input-file tmp_failures.txt --apply --output-report "$REPORT_MD" || {
  echo "⚠️ Aider 修复过程出现错误，继续执行"
}

rm -f tmp_failures.txt

echo "Aider 修复建议和操作日志已写入 $REPORT_MD"
echo "请查看报告并确认修改。"

exit 0

