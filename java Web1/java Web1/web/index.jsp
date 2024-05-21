<%--
  Created by IntelliJ IDEA.
  User: YY
  Date: 2024/5/14
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>软件主界面</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        nav {
            background-color: #333;
            color: #fff;
            padding: 10px;
        }
        nav ul {
            list-style-type: none;
            padding: 0;
        }
        nav ul li {
            display: inline;
            margin-right: 20px;
        }
        nav ul li a {
            color: #fff;
            text-decoration: none;
        }
    </style>
</head>
<body>
<h1>软件主界面</h1>
<nav>
    <ul>
        <li><a href="search.jsp">查询</a></li>
        <li><a href="update.jsp">更新</a></li>
        <li><a href="dele.jsp">删除</a></li>
        <li><a href="allShow.jsp">显示</a></li>
    </ul>
</nav>
</body>
</html>