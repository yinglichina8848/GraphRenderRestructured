/** @cond */
# 文档列表

以下是通过脚本自动生成的文档列表：

```bash
find doc -name "*.md" -type f | sed 's|^doc/||' | while read file; do
  echo "- [${file}](${file})"
done
```

/** @endcond */
