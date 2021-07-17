<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String uri = request.getRequestURI();
	String pageName = uri.substring(uri.lastIndexOf("/")+1);
	
	if(request.getParameter("logout") != null){
		session.setAttribute("username", null); 
	}
%>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="Style.css" rel="stylesheet" type="text/css">
<nav class="navbar navbar-expand-lg navbar-light bg-light">
 		<div class="container-fluid">
 			<div class="navbar-header">
		 		<a class="navbar-brand" href="MainMenu.jsp">
		   			<img src="img/logo_scritta.png"  height="80" alt="">
	  			</a>
	   		</div>
	   		
      		<ul class="form-inline nav navbar-nav navbar-right">
	      		<%
	      		Object username = session.getAttribute("username");
	      		if(username != null) {
			  		%>
			  		<li class="nav-item">
			        	<form name = "logout_form" action = "<%= pageName%>" method = "POST">
	 							<input type = "submit" class="nav-link active" aria-current="page" name = "logout" value = "Logout"/>
	 					</form>
		       		</li>
	        		<li class = "nav-item">
			          <a class="nav-link active" aria-current="page" href="#"><%= username.toString()%></a>
			        </li>
			        
			        <%
		  		}else{
		  			%>
			        <li class="nav-item">
			          <a class="nav-link" href="Login.jsp">Sign in</a>
			        </li>
			        <li class="nav-item">
			          <a class="nav-link" href="Register.jsp">Sign up</a>
			        </li>
			        <%
	 			}%>
      		</ul>
		</div>
</nav>
<style>
body,
		html {
			margin: 0;
			padding: 0;
			height: 100%;
			background: #f0f0f2 !important;
		}
</style>

