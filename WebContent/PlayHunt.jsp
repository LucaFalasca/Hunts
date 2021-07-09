<%@page import="logic.bean.HuntBean"%>
<%@page import="logic.bean.RiddleBean"%>
<%@page import="logic.enumeration.Pages"%>
<%@page import="logic.control.ManageHuntControl"%>
<%@page import="logic.control.PlayHuntControl"%>
<%@ page language="java" import="java.util.*,java.lang.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<jsp:useBean id="answerBean" scope="request" class="logic.bean.AnswerBean"/>
<jsp:setProperty name="answerBean" property="*"/>
<!DOCTYPE html>
<%

	if(session.getAttribute("username") == null && Pages.LOGIN.needLogin()){
		response.sendRedirect(Pages.LOGIN.getWebPath());
	}
	HuntBean hunt = null;
	if(session.getAttribute("hunt") != null){
		int idHunt = Integer.valueOf(session.getAttribute("hunt").toString());
		ManageHuntControl controller = new ManageHuntControl();
		hunt = controller.getHunt(idHunt, "");
	}
	int riddleChoosen = -1;
	if(request.getParameter("riddle") != null){
		System.out.println(riddleChoosen);
		riddleChoosen = Integer.valueOf(request.getParameter("riddle").toString());
	}
	List<RiddleBean> riddles = hunt.getRiddle();
	if(session.getAttribute("riddles") != null){
		List<?> list = (List<?>)session.getAttribute("riddles");
		List<RiddleBean> riddlesTemp = new ArrayList<>();
		for(int i = 0; i < list.size(); i++){
			RiddleBean riddle = (RiddleBean)list.get(i);
			riddlesTemp.add(i, riddle);
		}
		
		hunt.setRiddle(riddlesTemp);
		riddles = riddlesTemp;
	}
	else{
		
		session.setAttribute("riddles", riddles);
	}
	
	if(request.getParameter("answer") != null){
		riddleChoosen = Integer.valueOf(request.getParameter("answer").toString());
		PlayHuntControl controller = new PlayHuntControl();
		answerBean.setRiddleAnswer(riddles.get(riddleChoosen).getSolution());
		boolean isRight = controller.answer(answerBean);
		if(isRight){
			System.out.println("yeee");
		}
		else{
			System.out.println("noooo");
		}
	}
	
	
	
%>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Play Hunt</title>
		<jsp:include page="NavBar.jsp"></jsp:include>
	</head>
	<body>
	<div class="row">
		<div class="col-sm-6 text-center"> 
	     	<h2>Riddles</h2>
	    	<ul class="list-group">
		     	<%
				for(RiddleBean riddle : riddles) {
				%>
					<li class="list-group-item">
						<form action = "PlayHunt.jsp" method = "POST">
					<%
							out.print(riddle.getRiddle());
			     	%>
				     		<div class="btn-group" role="group" aria-label="Basic example">
							  <button type="submit" name = "riddle" value = "<%= riddles.indexOf(riddle)%>" class="btn btn-secondary">Answer</button>
							</div>
						</form>
					</li>
		     	<%
					}
		     	%>
	    	</ul>
		</div>
	</div>
	<%
	if(riddleChoosen != -1){ 
		RiddleBean currentRiddle = riddles.get(riddleChoosen);
		answerBean.setRiddleAnswer(currentRiddle.getSolution());
	%>
	<div class="row">
		<div class="col-sm-6 text-center"> 
			<form action = "PlayHunt.jsp" name = "form_answer" method = "POST"> 
				<h2><% out.print(currentRiddle.getRiddle());%></h2>
				<input type = "text" name = "userAnswer">
				<button type = "submit" name = "answer" value = "<%= riddleChoosen%>">Answer</button>
			</form>
		</div>
	</div>
	<%} %>
	</body>
</html>