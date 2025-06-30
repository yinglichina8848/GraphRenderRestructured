# Update the work flow on Github Actions.
touch publish.sh
touch push.sh
git cz
git pull origin main
git add doc/CHANGELOG.md
git add publish.sh
git add push.sh
git commit -m '抽取更新的版本号和CHANGELOG， 推送到 Github main 分支，触发云端环境自动编译测试, 自动生成发布静态文档到 gh-pages，并自动同步项目到 Gitee '
#更新 main 分支，触发云端的 Action 工作流
git push origin main
