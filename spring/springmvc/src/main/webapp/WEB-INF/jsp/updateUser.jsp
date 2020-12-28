<%--
  Created by IntelliJ IDEA.
  User: ASNPHDG
  Date: 2020/1/4
  Time: 21:43
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page  isELIgnored = "false" %>
<%
    pageContext.setAttribute("path", request.getContextPath());
%>
<html>
<head>
    <title>修改User</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    Spring MVC Demo Page
                </h1>
            </div>
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>修改User</small>
                </h1>
            </div>
        </div>
    </div>

    <form action="" name="userForm">
        <input type="hidden" name="id" value="${user.id}"/><br><br><br>
        User名字：<input type="text" name="name" value="${user.name}"/><br><br><br>
        User部门：<input type="text" name="dept" value="${user.dept }"/><br><br><br>
        User电话：<input type="text" name="phone" value="${user.phone }"/><br><br><br>
        User网站：<input type="text" name="website" value="${user.website }"/><br><br><br>
        <input type="button" value="提交" onclick="updateUser()"/>
    </form>
    <script type="text/javascript">
        function updateUser() {
            let form = document.forms[0];
            form.action = "${path}/user/doUpdateUser";
            form.method = "post";
            form.submit();
        }
    </script>
</div>