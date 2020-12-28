<%--
  Created by IntelliJ IDEA.
  User: ASNPHDG
  Date: 2020/1/5
  Time: 21:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>User Update Page</title>
</head>
<body>
测试国际化：<s:text name="user.name"/>
<br/><br/>
如下User信息是后端Action类中为模拟从数据库返回而仿造的；
<br/>
表单提交后，后端接收到信息，意味着测试完成：
<s:form action="user_doUpdate" method="POST">
<%--	<s:textfield label="User ID" name="user.id" />--%>
	<s:textfield label="User Name" name="user.name" />
	<s:textfield label="User Dept" name="user.dept" />
	<s:textfield label="User Phone" name="user.phone" />
	<s:textfield label="User Website" name="user.website" />
	<s:submit lable="Submit"></s:submit>
</s:form>

</body>
</html>