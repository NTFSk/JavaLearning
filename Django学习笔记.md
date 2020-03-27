DJango
===

* 开发环境 Ubuntu 18.04
* django版本 2.2.6

* 虚拟环境名 django-learn

进入虚拟环境： workon + 虚拟环境名

退出虚拟环境： deactivate



__创建Django项目:__ 

django-admin startproject 项目名

![](./startproject.png)

### django推荐的项目规范

按照功能或者模块进行分层，分成一个个app。所有和某个模块相关的视图都写在对应的app的views.py中。

__创建app的命令：__ python manage.py startapp 应用名



### DEBUG模式

开启DEBUG模式：在`settings.py`中设置 __DEBUG = True__

如果开启了DEBUG模式

1. 每次修改并保存Django项目的代码后，Django都会自动帮我们刷新项目，不需要重启项目。
2. Django项目中的代码出现了bug，在浏览器和控制台会打印出错信息
3. 在生产环境中，禁止开启DEBUG模式，不然有很大的安全隐患
4. 如果将DEBUG设置为False，则必须设置`ALLOWED_HOSTS`



### 视图函数

1. 视图函数的第一个参数必须是 `request`
2. 视图函数的返回值必须是`django.http.response.HttpResponseBase`的子类对象



### url映射

1. 为什么会去urls.py中寻找映射呢？

   因为在`settings.py`文件中配置了`ROOT_URLCONF` 为 `urls.py`

2. 在 `urls.py`中我们所有的映射，都应该放在`urlpatterns`这个变量中
3. 所有的映射都要使用`path`函数或者`re_path`函数进行包装



### url传参

1. 在url中使用变量的方式，在path的第一个参数中，使用 `<参数名>` 的方式可以传递参数。然后在对应的视图函数中，也要传递对应的参数，而且参数的命名要保持一致

   ```python
   path('xxx/<参数一>/<参数二>/',views.xxx)
   对应的视图中的函数是
   
   def xxx(request,参数一，参数二):
       pass
   ```

   

2. 采用查取字符串的方式：在url中不需要单独地匹配查询字符串的部分，只需要在视图函数中使用 `request.GET.get('参数')`的方式获取。

   ```python
   path('xxx/',views.xxx)
   对应的视图中的函数是
   
   def xxx(request):
       变量 = request.GET.get('参数名')
       pass
   ```

   