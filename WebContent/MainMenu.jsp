<%@page import="logic.bean.MapBean"%>
<%@page import="logic.bean.HuntBean"%>
<%@page import="logic.control.ManageMapControl"%>
<%@page import="logic.control.ManageHuntControl"%>
<%@page import="logic.control.UploadFileControl"%>
<%@ page language="java" import="java.util.*,java.lang.*"%>
<%@ page language="java" import="java.io.FileOutputStream"%>
<%@ page language="java" import="java.io.File"%>
<%@ page language="java" import="java.util.*,java.lang.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%	
	
	if(request.getParameter("play") != null){
		String idHunt = request.getParameter("play").toString();
		session.setAttribute("hunt", idHunt);
		response.sendRedirect("PlayHunt.jsp");
	}
%>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Home</title>
		<jsp:include page="NavBar.jsp"></jsp:include>
	</head>
	<body>
	
		<div class="container-fluid text-center">    
		  	<div class="row content">
    			<%
		     		ManageHuntControl controllerHunts = new ManageHuntControl();
					List<HuntBean> listHunts = controllerHunts.getAllHunts();
				%>
		    	<div class="col-sm-6 text-center"> 
			     	<h2>Hunts</h2>
			     	
				    	<ul class="list-group">
				    		
					     	<%
					     		
								for(HuntBean huntBean : listHunts) {
							%>
							<li class="list-group-item">
								<form action = "MainMenu.jsp" name = "hunt<%out.print(huntBean.getIdHunt()); %>" method = "POST">
							<%
									out.print(huntBean.getHuntName());
								
					     	%>
					     		
						     		<div class="btn-group" role="group" aria-label="Basic example">
									  <button type="submit" name = "play" value = "<%out.print(huntBean.getIdHunt()); %>" class="btn btn-secondary">Play</button>
									</div>
								</form>
							</li>
						     	<%
									}
						     	%>
				    	</ul>
			    	
				</div>
				<%if(session.getAttribute("username") != null) {
					List<HuntBean> listHunt = controllerHunts.getAllHunts(session.getAttribute("username").toString());
				%>
					
				<div class="col-sm-3 text-center"> 
			     	<h2>My Hunts</h2>
			    	<ul class="list-group">
				     	<%
				     		
							for(HuntBean huntBean : listHunt) {
						%>
						<li class="list-group-item">
						<%
								out.print(huntBean.getHuntName());
							
				     	%>
				     		
				     		<div class="btn-group" role="group" aria-label="Basic example">
   							  <button type="button" class="btn btn-secondary">Play</button>
							  <button type="button" class="btn btn-secondary">Edit</button>
							  <button type="button" class="btn btn-secondary">Delete</button>
							</div>
						</li>
					     	<%
								}
					     	%>
			    	</ul>
				</div>
				<div class="col-sm-3 text-center"> 
			     	<h2>My Maps</h2>
			     	<ul class="list-group">
				     	<%
				     		ManageMapControl controllerMaps = new ManageMapControl();
							List<MapBean> mapBeans = controllerMaps.getAllMaps(session.getAttribute("username").toString());
							for(MapBean mapBean : mapBeans) {
						%>
						<li class="list-group-item">
						<%
								out.print(mapBean.getName());
							
				     	%>
				     		
				     		<div class="btn-group" role="group" aria-label="Basic example">
							  <button type="button" class="btn btn-secondary">Edit</button>
							  <button type="button" class="btn btn-secondary">Delete</button>
							</div>
						</li>
					     	<%
								}
					     	%>
			    	</ul>
				</div>
				<%} %>
			</div>
		</div>
	</body>
</html>