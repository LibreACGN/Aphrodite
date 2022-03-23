## Aphrodite


### 介绍
[Aphrodite（阿佛洛狄忒）](https://en.wikipedia.org/wiki/Aphrodite) 是希腊神话中爱与美的女神。
本应用的基础功能是人物的生日记录，可以设置人物的头像、姓名、出生日期信息，以列表形式展现。


### 已知问题 | 待改进

1. ~无法删除人物~ 已经可以左滑删除

2. ~数据库加载结果有时不符合预期~ Glide 缓存问题

3. 照片选择器不符合我的需求，后续将自行实现照片选择与拍照功能

4. 图片在应用目录下直接存储. 有 root 权限的手机如进入目录删除图片会造成应用异常


### 安装包

~**目前处于开发完善阶段，可通过源码自行构建安装包。**~ 已发布预览 release


### 项目结构 & 贡献

项目采用 MVVM 架构，[主目录](https://github.com/sodalaboratory/Aphrodite/tree/main/app/src/main/java/com/sodalaboratory/aphrodite) 下的 [ui](https://github.com/sodalaboratory/Aphrodite/tree/main/app/src/main/java/com/sodalaboratory/aphrodite/ui) 包内包括了视图控制和 viewmodel 代码；[data](https://github.com/sodalaboratory/Aphrodite/tree/main/app/src/main/java/com/sodalaboratory/aphrodite/data) 包括数据库操作和数据模型代码；[utils](https://github.com/sodalaboratory/Aphrodite/tree/main/app/src/main/java/com/sodalaboratory/aphrodite/utils) 包内是一些工具类。

自己精力有限，随时欢迎提 pr | issue 帮助改进软件。



