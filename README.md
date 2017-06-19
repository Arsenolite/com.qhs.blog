#一个没取过名的博客系统

我承认这个项目烂尾了……虽然我已经完成了大半部分。

github给了现成的gitpage，写自己的博客项目，部署到自己的服务器上就顿时有点失去意义。。

我做了什么：

+ ssm整合，基本的增删改查。
+ 引入shiro，并将相关的bean纳入Spring管理，实现整合
+ 提供了rest API，并实现了无状态API下的用户认证授权。
+ 将一个基于spring-boot的图片验证码[项目](https://github.com/lianggzone/captcha)移植到自己的项目中，并将其中redis的操作应用到项目其他地方。
（本想用jcaptcha，但是那个主要是基于表单，不符合我的需求）
+ 使用Spring整合Javamail和Freemarker，组装邮件模板，实现邮件验证
+ 在linux下部署了Tomcat，nginx，jdk，mysql，成功跑起来了项目
----
它目前的功能有：
+ 全部使用无状态API
+ 图片验证码/邮件验证码
+ 指定的web路径只有在请求中带上有效的Token才能访问
+ 加盐存储密码
+ 用户登陆/注册/修改个人信息
+ （其他文章等模块，虽然设计好了数据库和java bean，但是我觉得写完它不会让我学到更多东西）
----
我的收获：
+ 我现在能迅速配置出一个ssm的demo，并且能理解大部分配置文件的内容，理解Spring的设计思想。
+ 我对spring-boot有了初步理解，能使用它进行开发。
+ 我熟悉了CentOS 7下的Java Web环境配置。
