<%@page import="logic.bean.HuntBean"%>
<%@page import="logic.bean.MapBean"%>
<%@page import="logic.bean.RiddleBean"%>
<%@page import="logic.bean.ZoneBean"%>
<%@page import="logic.enumeration.Pages"%>
<%@page import="logic.parser.Parser"%>
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
	MapBean map = null;
	if(session.getAttribute("hunt") != null){
		int idHunt = Integer.valueOf(session.getAttribute("hunt").toString());
		ManageHuntControl controller = new ManageHuntControl();
		hunt = controller.getHunt(idHunt, "");
		map = hunt.getMap();
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
			riddles.get(riddleChoosen).setCompleted();
			session.setAttribute("riddles", riddles);
			if(controller.isRiddlesCompleted(riddles)){
				session.removeAttribute("riddles");
				session.removeAttribute("hunt");
				%>
					<script type="text/javascript">
						alert("Hai vinto!");
						window.location = "<%= Pages.MAIN_MENU.getWebPath()%>";
					</script>
				<%
			}
		}
		else{
			
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
							if(riddle.isCompleted()){
								%>
									<input type="checkbox" checked disabled>
								<%
							}
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
		<div class="col-sm-6 text-center"> 
					<% if(map != null){%>
		  			<canvas id="canvas" width="350" height="350" style="border:1px solid #000000; <% if(map.getImage() != null){%>background: url('<%= "uploads/" + map.getImage().substring(8)%>'); <% }%>background-size: 350px 350px;">
					</canvas>
					<% }%>
					<%
					if(map != null){
						if(map.getZones() != null && !map.getZones().isEmpty()){
							for(ZoneBean zone : map.getZones()){
								%>
								<script type="text/javascript">
									var canvas = document.getElementById("canvas");
									var ctx = canvas.getContext("2d");
									ctx.fillStyle = "rgba(234, 237, 145, 0.5)";
									var x1 = <%= Parser.parseFromPercent(zone.getX1(), 350)%>;
									var y1 = <%= Parser.parseFromPercent(zone.getY1(), 350)%>;
									var x2 = <%= Parser.parseFromPercent(zone.getX2(), 350)%>;
									var y2 = <%= Parser.parseFromPercent(zone.getY2(), 350)%>;
									ctx.fillRect(x1, y1, x2 - x1, y2 - y1);
								</script>
								<%
							}
						}
					}%>
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