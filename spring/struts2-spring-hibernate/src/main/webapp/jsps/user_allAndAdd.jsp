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
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>User Add and All</title>
</head>
<body>
User Form:
<s:form id="user_edit_submitForm" action="user_create" method="POST">
<%--	<s:textfield label="UserEntity id" name="user.id" />--%>
	<s:textfield label="User name" name="user.name" />
	<s:textfield label="User dept" name="user.dept" />
	<s:textfield label="User phone" name="user.phone" />
	<s:textfield label="User website" name="user.website" />
	<s:submit lable="Create User"></s:submit>
</s:form>


<br /><br />
User List:
<table border="1">
	<tr>
<%--		<th>id</th>--%>
		<th>name</th>
		<th>dept</th>
		<th>phone</th>
		<th>website</th>
		<th>操作</th>
	</tr>
	<s:iterator var="user1" value="userList">
		<tr>
<%--			<td>${user.id}</td>--%>
			<td>${user1.name}</td>
			<td>${user1.dept}</td>
			<td>${user1.phone}</td>
			<td>${user1.website}</td>
			<td>
				<a href="user_toUpdate?id=${user1.id}">更改</a> |
				<a href="user_delete?id=${user1.id}">删除</a>
			</td>
		</tr>
	</s:iterator>
</table>
</body>

</html>