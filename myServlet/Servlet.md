# Servlet


## Servlet 是什么？

- Java EE 规范之一，规范就是接口。
- JavaWeb 三大组件之一。三大组件：Servlet 程序、Filter 过滤器、Listener 监听器。
- 运行在服务器上的一个 Java 小程序，可以 **接收客户端发送过来的请求，并响应数据给客户端。**


## 实现 Servlet 程序

1. 编写类实现 Servlet 接口
2. 实现 Service 方法，处理请求并响应数据
3. 在 web.xml 中配置 Servlet 程序的访问地址


## Servlet 处理流程

1. 用户发请求，action = add
2. 项目中，web.xml 中在 <servlet-mapping> 中找到 url-pattern = /add
3. 找到对应的 servlet-name = AddServlet
4. 找到和 <servlet-mapping> 中 servlet-name 一致的 <servlet>
5. 在此 <servlet> 中找到 servlet-name 对应的 servlet-class
6. 用户发送的是 post 请求（method = post），tomcat 执行 AddServlet 类的 doPost(request, response) 方法


## Servlet 继承关系

HttpServlet -> GenericServlet -> Servlet
核心方法：init(), service(), destroy()


## Servlet 生命周期

第一次接受请求时，Servlet 会进行实例化、初始化 init()、服务 service()，之后的请求都是服务，当容器关闭时 Servlet 实例才被销毁。

Servlet 在容器中是单例的、线程不安全的


## 会话

Http 无状态，服务器无法判断多个请求是否是同一个客户端发来的。
e.g. 如果服务器无法区分添加到购物车和结账两次请求是同一用户发起的，就会导致混乱。

可以通过会话跟踪技术解决无状态的问题:
1. 客户端第一次发请求，服务器获取不到 session，创建新的 session 响应给客户端
2. 客户端再次发请求时，把 sessionID 也发给服务器，服务器借助 sessionID 识别请求


## Thymeleaf


## 保存作用域

四个级别：
- page（不咋用了）
- request
- session
- application