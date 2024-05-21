<%@ page import="excel.ExcelToDatabase" %><%--
  Created by IntelliJ IDEA.
  User: YY
  Date: 2024/5/19
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>上传用户信息</title>
</head>
<body>
<h2>上传用户信息</h2>
<form action="ExcelToDatabase" method="post" enctype="multipart/form-data">
    <input type="file" name="excelFile" accept=".xls,.xlsx">
    <button type="submit">上传</button>
</form>
</body>
</html>

