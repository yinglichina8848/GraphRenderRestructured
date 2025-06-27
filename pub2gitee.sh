git init
git checkout -b gh-pages
git remote add gitee git@gitee.com:yinglichina/graph-render-restructured.git
git config user.name "YingLi"
git config user.email "yinglichina@163.com"
git add .
git commit -m "Update site $(date +'%F %T')" || echo "Nothing to commit"
git push --force gitee gh-pages

