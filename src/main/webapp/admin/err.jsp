<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%
	String base = request.getContextPath()+"/";
	String url = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+base;
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=url%>">
		<meta charset="UTF-8">
		<title></title>
		<style type="text/css">
			
			*{
				margin: 0px;
				padding: 0px;
				background-color: #ECECEC;
			}
			
			#all{
				width:1170px ;
				height: 55px;
				line-height: 55px;
				margin: 0px auto;
				margin-top: 58px;
				margin-left: 35px;
				
				border: 1px solid #ECECEC;
				
			}
			
			#all p{
				font-size: 25px;
				font-family: 黑体;
				background-color: #F2DEDE;
			}
			
		</style>
	</head>
	<body>
		<div id="all">
			<p>该功能尚未开放，请等待...</p>
		</div>
	</body>
</html>
