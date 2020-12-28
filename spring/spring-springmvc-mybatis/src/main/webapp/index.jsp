<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page  isELIgnored = "false" %>
<%
  pageContext.setAttribute("path", request.getContextPath());
  //发现path存在乱码，导致页面跳转失败，先不使用此处的path
//  pageContext.setAttribute("path", "http://localhost:8080/poc_ssm_war/");
%>
<!DOCTYPE HTML>
<html>
<head>

  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

  <title>首页</title>
  <style type="text/css">
    a {
      text-decoration: none;
      color: black;
      font-size: 18px;
    }

    h3 {
      width: 180px;
      height: 38px;
      margin: 100px auto;
      text-align: center;
      line-height: 38px;
      background: deepskyblue;
      border-radius: 4px;
    }
  </style>
</head>
<body>
<div class="container">
  <div class="row clearfix">
    <div class="col-md-12 column">
      <div class="page-header">
        <h1>
          基于SSM框架的管理系统：简单实现增、删、改、查。
        </h1>
      </div>
    </div>
  </div>
</div>
<br><br>
<h3>
  <a href="${path}/user/allUser">点击进入管理页面</a>
</h3>
</body>
</html>