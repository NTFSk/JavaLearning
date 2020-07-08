# :cyclone:VUE学习笔记:cyclone:

## 1. 引入Vue - 以下两种二选一

```html
<!-- 开发环境版本，包含了有帮助的命令行警告 -->
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

<!-- 生产环境版本，优化了尺寸和速度 -->
<script src="https://cdn.jsdelivr.net/npm/vue"></script>
```



## 2.声明式渲染

也就是把数据、属性和dom联系在一起

```html
<body>
    <!--view层-模板-->
    <div id="app">
        <!--3)使用数据-->
        <!-- v-bind用来绑定属性，将span标签的title属性和messsage的值绑定在一起 -->
        <span v-bind:title="message">
            鼠标悬停几秒查看此处动态绑定的提示信息！    
        </span>
    </div>

    <!-- 1. 导入Vue.js-->
    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
    <script>
        var vm = new Vue({
            // 1)绑定标签-js对象
            el : "#app",
            // 2)放数据-键值对形式
            data:{
                message: "hello,vue!"
            }
        });

    </script>
</body>
```



## 3.条件判断

`v-if`

`v-else-if`

`v-else`

```html
<body>
    <!-- v-if v-else 条件判断 -->
    <div id="app">
        <h1 v-if="type==='A'">A</h1>
        <h1 v-else-if="type==='B'">B</h1>
        <h1 v-else>C</h1>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
    <script>
        var vm = new Vue({
            el: "#app",
            data: {
                type: 'D'
            }
        });

    </script>
</body>
```



## 4. 循环

`v-for`

```html
<body>
    <!-- v-for 循环 -->
    <div id="app">
        <li v-for="item in items">
            {{item.message}}
        </li>
    </div>

    <div id="app2">
        <!-- 还可以使用index获取数组中元素的下标 -->
        <li v-for="(item, index) in items2">
            {{item.message}}--- {{index}}
        </li>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
    <script>
        var vm = new Vue({
            el: "#app",
            data: {
                items: [
                    {message: 'text111'},
                    {message: 'text222'},
                    {message: 'text333'},
                    {message: 'text444'},
                    {message: 'text555'}

                ]
            }
        });

        var vm2 = new Vue({
            el: "#app2",
            data: {
                items2: [
                    {message: 'text111111'},
                    {message: 'text222222'},
                    {message: 'text333333'},
                    {message: 'text444444'},
                    {message: 'text555555'}

                ]
            }
        });

    </script>
</body>
```



## 5. 方法绑定

可以用 `v-on` 指令监听 DOM 事件，并在触发时运行一些 JavaScript 代码。

```html
<body>
    <!-- 方法绑定 v-on -->
    <div id="app">
        <button v-on:click="sayHi">点击我</button>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
    <script>
        var vm = new Vue({
            el: "#app",
            data: {},
            methods:{ // 方法定义在 Vue 的 methods 对象中
                // 方法名: function(){}
                sayHi: function(){
                    // 'this'在方法里指向当前的vue实例
                    alert(this.message);
                }
            }
        });

    </script>
</body>
```



## 6. 数据双向绑定

`v-model`

在控制台更改vm.selected的值时,<a></a>中的数值会实时变化

```html
<body>
    <!-- 数据双向绑定演示 v-model -->
    <div id="app">
        输入的文本: <input type="text" v-model="message"/> {{message}}
        <br/>
        <select v-model="selected">
            <option value="" disabled>--请选择--</option>
            <option>A</option>
            <option>B</option>
            <option>C</option>
        </select>
        选中的值<a>{{selected}}</a>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
    <script>
        var vm = new Vue({
            el: "#app",
            data: {
                message: "",
                selected: ""
            }
           
        });

    </script>
</body>
```

## 7. Vue组件-component

组件是可复用的 Vue 实例，且带有一个名字：在这个例子中是 `<component1>`。我们可以在一个通过 `new Vue` 创建的 Vue 根实例中，把这个组件作为自定义元素来使用。

**一个组件的 `data` 选项必须是一个函数**,  因此每个实例可以维护一份被返回对象的独立的拷贝。

```html
<body>
    <!-- Vue组件 -->
    <div id="app">
        <!-- v-bind:text="item"，将遍历的item项绑定到组件中props定义的名为item的属性上；
            = 左边的text是props定义的属性名，= 右边的是item in intems中遍历的item的值-->
        <component1 v-for="item in items" v-bind:text="item"></component1> 

    </div>


    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
    <script>
		
        // 定义一个Vue组件 component,命名为component1
        Vue.component("component1", {
            // 参数传递到组件，使用props属性接收，text相当于形参，而item是实参
            props: ['text'],
            template: '<li>{{text}}</li>'
        });

        var vm = new Vue({
            el: "#app",
            data: {
                items: ["java","linux","web","sql"]
            }
           
        });

    </script>
</body>
```

### 7.1 全局注册

```js
Vue.component('my-component-name', {
  // ... 选项 ...
})
```

这些组件是**全局注册的**。也就是说它们在注册之后可以用在任何新创建的 Vue 根实例 (`new Vue`) 的模板中。比如：

```js
Vue.component('component-a', { /* ... */ })
Vue.component('component-b', { /* ... */ })
Vue.component('component-c', { /* ... */ })

new Vue({ el: '#app' })
<div id="app">
  <component-a></component-a>
  <component-b></component-b>
  <component-c></component-c>
</div>
```

在所有子组件中也是如此，也就是说这三个组件*在各自内部*也都可以相互使用。

### 7.2 局部注册

全局注册所有的组件意味着即便你已经不再使用一个组件了，

它仍然会被包含在你最终的构建结果中。这造成了用户下载的 JavaScript 的无谓的增加。

在这些情况下，你可以通过一个普通的 JavaScript 对象来定义组件：

```js
var ComponentA = { /* ... */ }
var ComponentB = { /* ... */ }
var ComponentC = { /* ... */ }
```

然后在 `components` 选项中定义你想要使用的组件：

```js
new Vue({
  el: '#app',
  components: {
    'component-a': ComponentA,
    'component-b': ComponentB
  }
})
```

注意**局部注册的组件在其子组件中\*不可用\***。例如，如果你希望 `ComponentA` 在 `ComponentB` 中可用，则你需要这样写：

```js
var ComponentA = { /* ... */ }

var ComponentB = {
  components: {
    'component-a': ComponentA
  },
  // ...
}
```

## 8. 计算属性 computed

**计算属性**可以帮助实现复杂的逻辑，从而一定程度上简化模板

例如，对于下面这段翻转字符串的代码

```html
<div id="example">
  {{ message.split('').reverse().join('') }}
</div>
```

可以使用**计算属性**来实现

这里我们声明了一个计算属性 `reversedMessage`。

我们提供的函数将用作 property `vm.reversedMessage` 的 getter 函数：

```html
<body>
  	<div id="example">
      <p>Original message: "{{ message }}"</p>
      <p>Computed reversed message: "{{ reversedMessage }}"</p>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
    <script>
        var vm = new Vue({
          el: '#example',
          data: {
            message: 'Hello'
          },
          computed: {
            // 计算属性的 getter
            reversedMessage: function () {
              // `this` 指向 vm 实例
              return this.message.split('').reverse().join('')
            }
          }
        })  
    </script>
</body>

```

**计算属性**和**方法**的不同之处在于：**计算属性是基于它们的响应式依赖进行缓存的**

只在相关响应式依赖发生改变时它们才会重新求值。这就意味着只要 `message` 还没有发生改变，多次访问 `reversedMessage` 计算属性会立即返回之前的计算结果，而不必再次执行函数。

*若 methods , computed 中的方法重名后，只会调用 methods 中的方法*

### 8.1 计算属性的setter

计算属性默认只有 getter，不过在需要时也可以提供一个 setter：

```javascript
// ...

    computed: {
      fullName: {
        // getter
        get: function () {
          return this.firstName + ' ' + this.lastName
        },
        // setter
        set: function (newValue) {
          var names = newValue.split(' ')
          this.firstName = names[0]
          this.lastName = names[names.length - 1]
        }
      }
    }

// ...
```

## 9. 插槽

```html
<body>
    <!-- 插槽 slot -->
    <div id="app">
        <todo>
           	<!-- 
				这里在todo的插槽的位置放入todo-title和todo-items两个组件
				然后利用 slot="slot-name"属性把组件和模板中的slot对应起来
				“slot-name”的值和slot标签的name属性相对应
			-->
            <todo-title slot="todo-title" v-bind:title="title"></todo-title>
            <todo-items slot="todo-items" v-for="item in todoItems" v-bind:item="item"></todo-items>
        </todo>
    </div>


    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
    <script>

        /**
        * 组件 "todo"
        * 当具有多个插槽的时候，使用<slot>元素的name属性来定义额外的插槽
        * 一个不带 name 的 <slot> 出口会带有隐含的名字“default”。
        * 
        */
        Vue.component("todo",{
            template:   '<div>\
                            <slot name="todo-title"></slot>\
                            <ul>\
                                <slot name="todo-items"></slot>\
                            </ul>\
                        </div>'
        });

        /** 两个组件 "todo-title" 和 "todo-items"
       	* "todo-title"接收属性title
       	* "todo-items"接收属性item
      	*/
        Vue.component("todo-title",{
            props:['title'],
            template: '<div>{{title}}</div>'
        });
        Vue.component("todo-items",{
            props:['item'],
            template: '<li>{{item}}</li>'
        });

        var vm = new Vue({
            el: "#app",
            data: {
                title: "mylist",
                todoItems: ['javajavajava','python','webwebweb']
            }
        });

    </script>
</body>
```

