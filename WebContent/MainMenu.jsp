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
	session.removeAttribute("zones");
	session.removeAttribute("riddles");
	session.removeAttribute("objects");
	String username = null;
	
	if(request.getParameter("play") != null){
		String idHunt = request.getParameter("play").toString();
		session.setAttribute("hunt", idHunt);
		response.sendRedirect("PlayHunt.jsp");
	}
	if(request.getParameter("editMap") != null){
		String idMap = request.getParameter("editMap").toString();
		session.setAttribute("map", idMap);
		response.sendRedirect("ManageMap.jsp");
	}
	if(request.getParameter("editHunt") != null){
		String idHunt = request.getParameter("editHunt").toString();
		session.setAttribute("hunt", idHunt);
		response.sendRedirect("ManageHunt.jsp");
	}
	
	if(request.getParameter("username") != null){
		username = request.getParameter("username");
	}
	if(request.getParameter("addHunt") != null){
		response.sendRedirect("ManageHunt.jsp");
	}
	if(request.getParameter("cancelMyHunt") != null){
		int idHunt = Integer.valueOf(request.getParameter("cancelMyHunt"));
		ManageHuntControl controller = new ManageHuntControl();
		HuntBean huntBean = new HuntBean();
		huntBean.setIdHunt(idHunt);
		huntBean.setUsername(username);
		controller.deleteHunt(huntBean);
		response.sendRedirect("MainMenu.jsp");
	}
	if(request.getParameter("deleteMap") != null){
		int idMap = Integer.valueOf(request.getParameter("deleteMap"));
		ManageMapControl controller = new ManageMapControl();
		controller.deleteMap(idMap, username);
		response.sendRedirect("MainMenu.jsp");
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
									<table style="width:100%">
										<tr>
											<td style="width:40%" align = left>
												<%= huntBean.getHuntName()%>
											</td>
											<td style="width:30%" align = left>
												<img src="https://img.icons8.com/material-rounded/24/000000/user.png"/>
												<%= huntBean.getUsername()%>
											</td>
											<td style="width:30%">
									     		<div class="btn-group" role="group" aria-label="Basic example">
												  <button type="submit" name = "play" value = "<%out.print(huntBean.getIdHunt()); %>" class="btn btn-secondary btn-success btn-xs" style = "vertical-align: middle;">
												  	<img src="https://img.icons8.com/ios/50/000000/play--v1.png" width="25" height="25"/>
												  </button>
												</div>
											</td>
										</tr>
									</table>
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
						<table style="width:100%">
							<tr>
								<td style="width:70%">
									<h2>My Hunts</h2>
	     						</td>
								<td style="width:30%">
									<form action = "MainMenu.jsp" name = "newHunt" method = "POST">
							     		<button type="submit" name = "addHunt" class="btn btn-secondary btn-circle"><img src="https://img.icons8.com/ios-glyphs/30/000000/plus-math.png" width="20" height="20"/></button>
							     	</form>
								</td>
							</tr>
						</table>
			     	
			    	<ul class="list-group">
				     	<%
				     		
							for(HuntBean huntBean : listHunt) {
						%>
						<li class="list-group-item">
							<form action = "MainMenu.jsp" name = "hunt<%out.print(huntBean.getIdHunt()); %>" method = "POST">
								<table style="width:100%">
									<tr>
										<td style="width:70%" align = left>
											<%= huntBean.getHuntName()%>
			     						</td>
										<td style="width:30%">
								     		<div class="btn-group" role="group" aria-label="Basic example">
				   							  <button type="submit" name = "play" value = "<%out.print(huntBean.getIdHunt()); %>" class="btn btn-secondary btn-success btn-xs"><img src="https://img.icons8.com/ios/50/000000/play--v1.png" width="25" height="25"/></button>
											  <button type="submit" class="btn btn-secondary btn-primary" name = "editHunt" value = "<%= huntBean.getIdHunt() %>"><img src="https://img.icons8.com/fluent-systems-regular/100/000000/edit--v1.png" width="25" height="25"/></button>
											  <button type="submit" class="btn btn-secondary btn-danger " name = "cancelMyHunt" value = "<%= huntBean.getIdHunt()%>"><img src="https://img.icons8.com/ios/50/000000/delete--v1.png" width="25" height="25"/></button>
											</div>
										</td>
									</tr>
								</table>
							</form>
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
							<form action = "MainMenu.jsp" name = "map<% out.print(mapBean.getId()); %>" method = "POST">
								<table style="width:100%">
									<tr>
										<td style="width:70%" align = left>
											<%= mapBean.getName()%>
			     						</td>
										<td style="width:30%">
								     		<div class="btn-group" role="group" aria-label="Basic example">
								  <button type="submit" class="btn btn-secondary btn-primary" name = "editMap" value = "<%= mapBean.getId()%>"><img src="https://img.icons8.com/fluent-systems-regular/100/000000/edit--v1.png" width="25" height="25"/></button>
								  <button type="submit" class="btn btn-secondary btn-danger" name = "deleteMap" value = "<%= mapBean.getId()%>"><img src="https://img.icons8.com/ios/50/000000/delete--v1.png" width="25" height="25"/></button>
								</div>
										</td>
									</tr>
								</table>
							</form>
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