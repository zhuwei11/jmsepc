<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>epc测试页</title>
</head>
<body>
	<h2>测试</h2><b>当前用户id:${account.id},余额:${account.balance}</b>	
<a href="rechage/${account.id}">并发充值</a><br><br>
<a href="withdraw/${account.id}">并发提现</a><br><br>
</body>
</html>