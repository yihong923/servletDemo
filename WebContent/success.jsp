<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="gbk"%>
<%@ page language="java" import="com.bamboocloud.servlet.*"%>
<%@ page language="java" import="java.io.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<body>
    <%
    AccountBean account = (AccountBean)session.getAttribute("account");
    String username = "";
    String pwd = "";
    if(account != null){
    	username=account.getUsername();
    	pwd=account.getPassword();
    }
    Object bBCloudBAMSession = session.getAttribute("BBCloudBAMSession");
    %>
     token:<%=bBCloudBAMSession %>	
     <br>
     userName:<%=username %>
     <br>
     password:<%=pwd  %>
     <br>
     <input type="button" id="userBtn" value="显示用户信息"/>
     <br>
     <div id="user" ></div>
</body>
<script type="text/javascript" src="jquery-1.8.3.js"></script>
<script type="text/javascript">
$(function(){
	$.ajax({
		type: "POST",
		url:'/PayForDemo/bim-integration?cmd=sync',
		dataType:'text',
		success:function(data){
			
		}
	});
	
	$("#userBtn").click(function(){
		$.ajax({
			type: "POST",
			url:'/PayForDemo/user',
			dataType:'text',
			success:function(data){
					$("#user").html(data);
				}
			});
	});
	
})
</script>
</html>