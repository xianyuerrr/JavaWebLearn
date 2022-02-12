# Tomcat

## 启动

启动：
1. `./bin/startup.bat`，进入 `localhost:8080` 查看是否启动成功。
2. `catalina run`

工程默认放在 webapps 文件夹下，也可在 conf 文件夹下自行配置：
```xml
<!--
path 表示访问路径
docBase 表示工程目录
-->
<Content path="" docBase="" />
```

Root 工程为默认工程（未写工程名的时候默认打开 Root 工程）
index 为默认资源名（未写资源名的时候默认打开 index 资源）


## web 工程目录介绍

![image-20220116165203669](https://cdn.jsdelivr.net/gh/xianyuerrr/PicGo/img/Roaming/Typora/typora-user-images/image-20220116165203669.png)

- src 存放源代码
- web 存放 web 工程的资源文件
- WEB-INF 是受服务器保护的目录，浏览器无法直接访问
- web.xml 是整个 web 工程的配置文件，比如 Servlet 程序、Filter 过滤器、Listener 监听器、Session 超时
- lib 存放第三方 jar 包（IDEA 还需要自己配置导入）
