如何在html中插入JavaScript代码

方式一：在html中的任何位置插入script标签，主意，head中的js代码会比body中的先被加载

```javascript
<script type="text/JavaScript">
	// js代码
</script>
```

方式二：引用js文件

```javascript
<script src="script.js"></script>
```



#### JS中的注释

```javascript
// 单行注释

/*
	多行注释
	多行注释
	
*/
```



#### JavaScript-判断语句（if...else）

**语法**

```javascript
if(条件)
{ 条件成立时执行的代码 }
else
{ 条件不成立时执行的代码 }
```

**示例**

```javascript
var score =80; //score变量存储成绩，初值为80
if(score>=60)
{
    document.write("很棒，成绩及格了。");
}
else
{
    document.write("加油，成绩不及格。");
}
```



#### JS中的函数

```html
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>函数调用</title>
   <script type="text/javascript">
      function contxt() //定义函数
      {
         alert("哈哈，调用函数了!");
      }
   </script>
</head>
<body>
   <form>
      <input type="button"  value="点击我" onclick="contxt()" />  
   </form>
</body>
</html>
```



#### JavaScript-输出内容（document.write）

```javascript
var mychar="JavaScript";
document.write(mychar + "<br>");
```



#### JavaScript-警告（alert 消息对话框）

```javascript
<script type="text/javascript">
    function rec(){
    var mychar="I love JavaScript";
    alert(mychar);
}
</script>
<input name="button" type="button" onClick="rec()" value="点击我，弹出对话框" />
```



#### JavaScript-确认（confirm 消息对话框）

**语法:**

```
confirm(str);
```

**参数说明:**

```
str：在消息对话框中要显示的文本
返回值: Boolean值
```

**返回值:**

```
当用户点击"确定"按钮时，返回true
当用户点击"取消"按钮时，返回false
```

```javascript
<script type="text/javascript">
    var mymessage=confirm("你喜欢JavaScript吗?");
    if(mymessage==true)
    {   document.write("很好,加油!");   }
    else
    {  document.write("JS功能强大，要学习噢!");   }
</script>
```



#### JavaScript-提问（prompt 消息对话框）

**`prompt`**弹出消息对话框,通常用于询问一些需要与用户交互的信息。弹出消息对话框（包含一个确定按钮、取消按钮与一个文本输入框）。

**语法:**

```
prompt(str1, str2);
```

**参数说明：**

```
str1: 要显示在消息对话框中的文本，不可修改
str2：文本框中的内容，可以修改
```

**返回值:**

```
1. 点击确定按钮，文本框中的内容将作为函数返回值
2. 点击取消按钮，将返回null
```

看看下面代码:

```
var myname=prompt("请输入你的姓名:");
if(myname!=null)
  {   alert("你好"+myname); }
else
  {  alert("你好 my friend.");  }
```



#### JavaScript-打开新窗口（window.open）

**语法：**

```
window.open([URL], [窗口名称], [参数字符串])
```

**参数说明:**

```
URL：可选参数，在窗口中要显示网页的网址或路径。如果省略这个参数，或者它的值是空字符串，那么窗口就不显示任何文档。
窗口名称：可选参数，被打开窗口的名称。
    1.该名称由字母、数字和下划线字符组成。
    2."_top"、"_blank"、"_self"具有特殊意义的名称。
       _blank：在新窗口显示目标网页
       _self：在当前窗口显示目标网页
       _top：框架网页中在上部窗口中显示目标网页
    3.相同 name 的窗口只能创建一个，要想创建多个窗口则 name 不能相同。
    4.name 不能包含有空格。
参数字符串：可选参数，设置窗口参数，各参数用逗号隔开。
```



```javascript
window.open("http://www.imooc.com","_blank","width=600,height=400,top=100,left=0");
```

