# Update the work flow on Github Actions.
touch publish.sh
touch push.sh
git add CHANGELOG.md
git add publish.sh
git add push.sh
git add .github/workflows/auto_ai_workflow.yml
git commit -m ' 适应云端环境自动编译测试, 恢复工作流 '
#更新 main 分支，触发云端的 Action 工作流
git push origin main
