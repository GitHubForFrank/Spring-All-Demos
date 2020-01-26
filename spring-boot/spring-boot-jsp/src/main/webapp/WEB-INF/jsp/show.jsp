<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/show.css">
</head>
<body>
<ul>
    <li>姓名: ${userDto.name}</li>
    <li>年龄: ${userDto.age}</li>
</ul>
</body>
</html>