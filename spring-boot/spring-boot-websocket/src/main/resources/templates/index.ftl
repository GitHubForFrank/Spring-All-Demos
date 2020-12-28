<!doctype html>
<html lang="en">
<head>
    <title>WebSocket登录页面</title>
</head>
<body>

<form action="/app/login" method="post">
    <table border="1" bordercolor="#00ffff" cellpadding=10 cellspacing=0 width=400>
        <tr>
            <!-- 占两列 -->
            <th colspan="2">登录表单</th>
        </tr>
        <tr>
            <td>用户名称</td>
            <td><input type="text" name="username" value=""><br /></td>
        </tr>

        <tr>
            <td>选择聊天页面</td>
            <td>
                <select name="chatPageType">
                    <!-- 默认选择1 -->
                    <option value="1" selected="selected" >简易聊天页面</option>
                    <option value="2" >中等聊天页面</option>
                </select>
            </td>
        </tr>
        <tr>
            <th colspan="2">
                <input type="reset" value="清除数据" />
                <input type="submit" value="提交数据"/>
            </th>
        </tr>
    </table>
</form>

</body>
</html>
