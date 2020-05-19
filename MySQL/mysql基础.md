



<h2 id="1">约束</h3>

#### 非空约束 ： not null

删除非空约束

```mysql
alter table 表名 modify 列名 字段类型(字段大小);
```

创建完表后，添加非空约束

```mysql
alter table 表名 modify 列名 字段类型(字段大小) not null;
```



#### 唯一约束 ： unique, 值不能重复, 但可以有多个null

删除唯一约束

```mysql
alter table 表名 drop index 列名;
```

**创建表后，添加唯一约束**

```mysql
alter table 表名 modify 列名 字段类型(字段大小) unique;
```



#### 主键约束 ： primary key, 非空且唯一

删除主键

```mysql
alter table 表名 drop primary key;
```

创建表后，添加主键

```mysql
alter table 表名 modify 列名 字段类型 primary key;
```

自动增长 :  auto_increment

```mysql
create table 表名(
	列名 字段类型 primary key auto_increment
);
```

删除自动增长

```mysql
alter table 表名 modify 列名 字段类型;
```

创建表后，设置自动增长

```mysql
alter table 表名 modify 列名 字段类型 auto_increment;
```



#### 外键约束 ： foreign key

建表时添加外键

```mysql
create table 表名(
	...,
    外键列,
    constraint 外键名称 foreign key (外键列) references 主表名称(主表列名称)
);
```

删除外键

```mysql
alter table 表名 drop foreign key 外键名称;
```

建表后添加外键

```mysql
alter table 表名 add constraint 外键名称 foreign key (外键列) references 主表名称(主表列名称);
```









