#!/bin/bash
set -e

echo "== Step 1: AI自动生成/补全单元测试和注释 =="

# 创建输出目录和文件
REPORT_FILE="doc/ai_fix_suggestions.md"
mkdir -p doc
echo "# 🤖 Aider 自动改进报告（$(date '+%Y-%m-%d %H:%M:%S')）" > "$REPORT_FILE"
echo "" >> "$REPORT_FILE"

# 限定处理范围：只包含源码目录
SRC_DIRS=(src test doc)

# 遍历 .java 和 .js 文件
for dir in "${SRC_DIRS[@]}"; do
  find "$dir" -type f \( -name "*.java" -o -name "*.js" \) | while read -r file; do
    echo "🛠️ Processing: $file"
    
    # 提示内容，可以自定义优化方向
    PROMPT="请改进以下文件的代码质量，包括注释补全、命名规范、结构简化，并补充可能缺失的单元测试。保持原有功能不变。"
    
    # 使用 aider 执行 AI 改进（需要配置好 aider 和模型）
    {
      echo "---"
      echo "## 📄 $file"
      echo ""
      echo "\`\`\`java"
      cat "$file"
      echo "\`\`\`"
      echo ""
      echo "**💡 Aider 改进建议：**"
      echo ""
      aider --no-chat --input "$file" --message "$PROMPT" >> "$REPORT_FILE"
    } || {
      echo "⚠️ Warn: AI提升失败 $file"
      echo "- $file: ❌ 分析失败，跳过。" >> "$REPORT_FILE"
    }
    
    echo "" >> "$REPORT_FILE"
  done
done

echo "✅ 所有文件处理完毕，报告已生成：$REPORT_FILE"

