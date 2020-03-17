<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>客户管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!-- 导入easyui的资源文件 -->
<script type="text/javascript" src="easyui/jquery.min.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="easyui/themes/icon.css">
<link id="themeLink" rel="stylesheet" type="text/css"
	href="easyui/themes/default/easyui.css">
</head>

<body>
	<table id="list"></table>

	<!-- 工具条 -->
	<div id="tb">
		<a id="addBtn" href="#" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true">添加</a> <a id="editBtn"
			href="#" class="easyui-linkbutton"
			data-options="iconCls:'icon-edit',plain:true">修改</a> <a
			id="deleteBtn" href="#" class="easyui-linkbutton"
			data-options="iconCls:'icon-remove',plain:true">删除</a>
	</div>

	<!-- 编辑窗口 -->
	<div id="win" class="easyui-window" title="客户信息编辑"
		style="width: 400px; height: 300px"
		data-options="iconCls:'icon-save',modal:true, closed:true">
		<form id="editForm" method="post">
			<%-- 提供id隐藏域，用来区分新增和修改--%>
			<input type="hidden" name="id" /> 客户姓名：<input type="text" name="name"
				class="easyui-validatebox" data-options="required:true" /><br />
			客户性别： <input type="radio" name="gender" value="男" />男 <input
				type="radio" name="gender" value="女" />女 <br /> 客户手机：<input
				type="text" name="telephone" /><br /> 客户地址：<input type="text"
				name="address" /><br /> <a id="saveBtn" href="#"
				class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
		</form>
	</div>

	<script type="text/javascript">
  		$(function(){
  			$("#list").datagrid({
  				//url:后台数据查询的地址
  				url:"customer/listByPage.action",
  				//columns：填充的列数据
  				//field:后台对象的属性
  				//tille:列标题
  				columns:[[
  					{
  						field:"id",
  						title:"客户编号",
  						width:100,
  						checkbox:true
  					},
  					{
  						field:"name",
  						title:"客户姓名",
  						width:200
  					},
  					{
  						field:"gender",
  						title:"客户性别",
  						width:200
  					},
  					{
  						field:"telephone",
  						title:"客户手机",
  						width:200
  					},
  					{
  						field:"address",
  						title:"客户住址",
  						width:200
  					}
  				]],
  				// 添加pagination属性，进行分页
  				pagination:true,
  				// 工具条
  				toolbar:"#tb"
  				
  			});
  			
  			// 点击添加按钮
  			$("#addBtn").click(function(){
  				// 清空各个输入栏
  				$("#editForm").form("clear");
  				// 打开编辑窗口
  				$("#win").window("open");
  			});
  			
  			// 保存数据
  			$("#saveBtn").click(function(){
  				$("#editForm").form("submit",{
  					// url，数据提交的地址
  					url:"customer/save.action",
  					// 提交前的回调函数，返回true:提交表单，返回false：不提交表单
  					onSubmit:function(){
  					// 判断表单的验证是否通过
  						return $("#editForm").form("validate");
  					},
  					// success: 服务器执行完毕之后的回调函数
  					success:function(data){ // data: 服务器返回的数据，类型是String
  						data = eval("("+ data + ")");
  						if(data.success){
  							// 关闭窗口
  							$("#win").window("close");
  							//刷新datagrid
  							$("#list").datagrid("reload");
  							$.messager.alert("提示","保存成功","info")
  						}else{
  							$.messager.alert("提示","保存失败:"+ data.msg,"error")
  						}
  					}
  				});
  			});
  			
  			
  			// 修改数据
  			$("#editBtn").click(function(){
  				// 判断只能选择一行
  				var rows = $("#list").datagrid("getSelections");
  				if(rows.length!=1){
  					$.messager.alert("提示","修改操作只能选择一行","warning");
  					return;
  				}
  				
  				// 表单的数据回显
  				$("#editForm").form("load","customer/findById.action?id="+rows[0].id);
  				
  				$("#win").window("open");
  			});
  			
  			// 批量删除
  			$("#deleteBtn").click(function(){
  				var rows = $("#list").datagrid("getSelections");
  				if(rows.length==0){
  					$.messager.alert("提示","请至少选择一行","warning");
  					return;
  				}
  				// 如果点击了确定，则value的值为true
  				$.messager.confirm("提示","确定要删除该数据吗",function(value){
  					if(value){
  						//执行删除操作,把选定客户的id传到后台，然后通过id删除数据
  						// 遍历rows，拿到选中的id,然后拼凑成格式: id=1&id=2&id=3
  						var idStr = "";
  						$(rows).each(function(i){
  							idStr += "id=" + rows[i].id + "&";
  						});
  						// 去掉最后一个'&'符号
  						idStr = idStr.substring(0,idStr.length-1);
  						//alert(idStr);
  						// 传递到后台
  						$.post("customer/delete.action",idStr,function(data){
  							if(data.success){
  	  							// 关闭窗口
  	  							$("#win").window("close");
  	  							//刷新datagrid
  	  							$("#list").datagrid("reload");
  	  							$.messager.alert("提示","删除成功","info")
  	  						}else{
  	  							$.messager.alert("提示","删除失败:"+ data.msg,"error")
  	  						}
  						},"json");
  					}
  				});
  				
  			});
  			
  		});
  	</script>
</body>
</html>
