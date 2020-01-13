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
	<title>User Update.jsp</title>
</head>
<body>
<s:form id="user_edit_submitForm" action="user_doUpdate" method="POST">
	<s:hidden  label="User id" name="user.id" />
	<s:textfield label="User name" name="user.name" />
	<s:textfield label="User dept" name="user.dept" />
	<s:textfield label="User phone" name="user.phone" />
	<s:textfield label="User website" name="user.website" />
	<s:submit lable="Submit"></s:submit>
</s:form>


</body>

</html>