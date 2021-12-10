# 前端三件套

## HTML

## CSS

## JavaScript

### 简介

JS 与 Java 没有什么关系，它是弱类型语言。

有如下特点：
- 交互性（实现信息的动态交互）
- 安全性（不允许直接访问本地磁盘）
- 跨平台性（与浏览器有关，和OS无关）

### 变量

JS 的变量类型：
- 数值
- 字符串
- 对象
- bool
- 函数

JS 里的特殊值：
- undefined
- null
- NAN

JS 的 == 只比较字面值，比如
```js
12 == "12" // 结果为 true
```

JS 里所有的变量都可以当作 bool 值，0, null, undefined, "" 被认为是 false。

### 数组
JS 的数组会自动扩容，未赋值的下标值为 undefined
```js
var arr = []
alert(arr.length)
arr[0] = 12
alert(arr.length)
arr[2] = "abc"
for (var i = 0; i < arr.length; i++) {
    alert(arr[i])
}
```

### 函数

```js
// 方式一
function fun(a, b) {
    var c = a+b
    return c
}

// 方式二
var f = function(a, b) {
    var c = a + b
    return c
}
```

需要注意，JS 中函数不能重载，会直接覆盖定义

隐形参数
```js
function fun() {
    // arguments 为传入函数的参数列表，使用 fun(a, b) 调用时就为 [a, b]
    var len = arguments.length
    return len
}
```

### 对象

```js
var Person = new Object();
Person.name = "佚痕"
Person.age = 21
Person.introduction = function() {
    var msg = Person.name + Person.age
    return msg
}
```

```js
var Person = {
    name: "佚痕",
    age: 21,
    introduction: function() {
        var msg = Person.name + Person.age
        return msg
}
}
```

### 事件
常用事件：
- onload 加载完成
- onclick 单击
- onblur 失去焦点
- onchange 内容发生改变
- onsubmit 表单提交

静态注册：通过 HTML 标签的事件属性直接赋予事件响应后的代码

动态注册：先通过 JS 代码得到标签的 DOM 对象，再通过 DOM 对象.事件名 = function() {} 赋予事件响应后的代码

```js
// 动态注册 onClick 事件
window.onload = function() {
    // 1. 获取标签对象
    // console 是控制台对象，向浏览器控制台打印输出，由于测试
    console.log("")
    // document 时整个页面
    var btnObj = document.getElementById("btn")
    btnObj.onclick = function() {}
}
```

### DOM

Document Object Model，把文档中的标签、属性、文本转化成对象来管理。

Document Object 的理解：
- Document 管理了所有的 HTML 文档内容
- Document 是一种树形结构的文档
- 所有标签都被对象化
- 可以通过 Document 访问所有的标签对象

## XML

### 作用
- 保存数据
- 做配置文件
- 网络传输数据的格式之一（现在JSON为主）

### 语法

```xml
<books>
    <book sn="SN1">
        <name>肖八</name>
        <author>肖秀荣</author>
    </book>
    <book sn="SN2">
        <name>肖四</name>
        <author>肖秀荣</author>
    </book>
</books>
```

注意：
- 标签名不能以 "xml" 开始
- 标签名不能包含空格
- 对大小写敏感
- 有且仅有一个根标签（没有父标签的标签）
- <![CDATA[___]]> 可以不用转义


### 解析

