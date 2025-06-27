# 在本地 main 分支上
8586728+yinglichina@user.noreply.gitee.com
git config user.name "YingLi"
git config user.email "8586728+yinglichina@user.noreply.gitee.com"

git checkout main

# 添加 Gitee 仓库作为另一个远程地址（如果还没有）
git remote add gitee git@gitee.com:yinglichina/graph-render-restructured.git
# 推送 main 分支
git push --force gitee main
