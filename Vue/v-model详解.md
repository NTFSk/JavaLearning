# :cyclone:v-model详解:cyclone:

## 基础用法：

你可以用 `v-model` 指令在表单 `<input>`、`<textarea>` 及 `<select>` 元素上创建双向数据绑定。

```js
<input v-model="sth" />
<input :value="sth" @input="sth = $event.target.value" />
```

第一行的代码其实只是第二行的语法糖，**两行代码是等价的**。

要理解这行代码，首先你要知道 `input` 元素本身有个 `oninput`事件，这是 HTML5 新增加的，类似 `onchange` ，每当输入框内容发生变化，就会触发 `oninput` ，把最新的`value`赋值给 `sth`变量。

说到这里我们要介绍一下**input标签**的value属性和oninput事件，

| 属性                                                         | 值      | 描述                             |
| ------------------------------------------------------------ | ------- | -------------------------------- |
| [value](https://www.w3school.com.cn/tags/att_input_value.asp) | *value* | 规定 input 元素的值。            |
| oninput                                                      | script  | 当元素获得用户输入时运行的脚本。 |

**给组件添加 `v-model` 属性时，默认会把`value` 作为组件的属性，把 `input`作为给组件绑定事件时的事件名**