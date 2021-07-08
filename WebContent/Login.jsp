<%@page import="logic.control.UserControl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<jsp:useBean id="loginBean" scope="request" class="logic.bean.LoginBean"/>
<jsp:setProperty name="loginBean" property="*"/>

<%
    if (request.getParameter("login") != null) {
    	UserControl controller = new UserControl();
        if (controller.verifyAccount(loginBean)) {
        	session.setAttribute("username", loginBean.getUsername());
        	response.sendRedirect("MainMenu.jsp");
        } else {
			System.out.println("Errore login");
        }
    }
%>

<html>
<head>
    <title>Login</title>
    <jsp:include page="NavBar.jsp"></jsp:include>
</head>
<body>
<div class="container">
    <form action="Login.jsp" name="myform" method="POST">
        <div class="row">
            <div class="col-lg-4 form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" autocomplete="off">
            </div>
        </div>
        <div class="row">
            <div class="col-lg-4 form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password">
            </div>
        </div>
        <div class="row">
            <div class="col-lg-4 form-group">
                <input type="submit" name="login" value="login">
            </div>
        </div>
    </form>
</div>
