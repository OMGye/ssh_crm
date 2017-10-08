<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户信息管理</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/themes/icon.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.1.2.6.min.js"></script>
<script type="text/javascript">
   $(function(){
      $("#linkManid").datagrid({
       url:"${pageContext.request.contextPath}/linkMan_linkManJson.action",
       columns:[[
           {field:'lkmName',title:'联系人名称',width:200},
           {field:'lkmGender',title:'客户电话',width:150},
           {field:'lkmPhone',title:'联系人电话',width:100},
          // {field:'customer.custName',title:'所属客户',width:100}
          {field:'customer',title:'所属客户',width:100,formatter:function(value,row,index){
               if(row.customer){
               return row.customer.custName;
               }
          }}
       ]],
       pagination:true,//是否显示分页
       singleSelect:true
      
      });
   
   })
</script>
</head>
<body>
  <table id="linkManid"></table>
</body>
</html>
