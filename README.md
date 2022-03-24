## Aphrodite


### 介绍
[Aphrodite（阿佛洛狄忒）](https://en.wikipedia.org/wiki/Aphrodite) 是希腊神话中爱与美的女神。
本应用的基础功能是人物的生日记录，可以设置人物的头像、姓名、出生日期信息，以列表形式展现。
<p align="center">
<img src=".\images\screen_shot_huawei_nova4.PNG" width=250/>
<p />


### 已知问题 | 待改进

1. ~无法删除人物~ 已经可以左滑删除

2. 数据库加载结果有时不符合预期, 属于严重 bug

3. 照片选择器不符合我的需求，后续将自行实现照片选择与拍照功能

4. 图片在应用目录下直接存储. 有 root 权限的手机如进入目录删除图片会造成应用异常

5. 应用无图标，界面比较简陋（绘画有在学了，请等后续更新图标）

6. 使用了 Fragment，但还没时间做平板适配. 

7. ~~夜间模式支持.~~ 已经添加夜间（黑暗）模式支持。

待改进的问题还有许多。


### 项目结构 & 贡献

项目采用 MVVM 架构，[主目录](https://github.com/sodalaboratory/Aphrodite/tree/main/app/src/main/java/com/sodalaboratory/aphrodite) 下的 [ui](https://github.com/sodalaboratory/Aphrodite/tree/main/app/src/main/java/com/sodalaboratory/aphrodite/ui) 包内包括了视图控制和 viewmodel 代码；[data](https://github.com/sodalaboratory/Aphrodite/tree/main/app/src/main/java/com/sodalaboratory/aphrodite/data) 包括数据库操作和数据模型代码；[utils](https://github.com/sodalaboratory/Aphrodite/tree/main/app/src/main/java/com/sodalaboratory/aphrodite/utils) 包内是一些工具类。

自己精力有限，且能力也有限，随时欢迎提 pr | issue 帮助改进软件。这是我的第一个真正意义上可用的安卓应用（而不是之前做的许多学习 Demo），我将持续更新它，让它变得更好，并在将来的某个时候上线各大应用市场。

### Special Thanks
[Cap.雪ノ下八幡](https://github.com/CaptainYukinoshitaHachiman)，其所属的 [Future Application Laboratory](https://github.com/future-application-laboratory) 开发的 iOS / WatchOS 项目 [birthreminder](https://github.com/future-application-laboratory/BirthReminder) 给了我最初的启蒙，并让我从大一开始地学习 Android 开发，期待完成一款 Android 类似应用。虽然因为各种问题时断时续，但如今终有一点点成绩。非常感谢。


