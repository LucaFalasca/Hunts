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
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Home</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
		<link href="Style.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<div class="container-fluid text-center">    
		  	<div class="row content">
		    	<div class="col-sm-3 sidenav">
	      			<p><a href="#">Create Hunt</a></p>
		      		<p><a href="#">Create Map</a></p>
	    		</div>
    			<%
		     		ManageHuntControl controllerHunts = new ManageHuntControl();
					List<HuntBean> listHunts = controllerHunts.getAllHunts(null);
					List<HuntBean> listHunt = controllerHunts.getAllHunts("a");
				%>
		    	<div class="col-sm-3 text-center"> 
			     	<h2>Hunts</h2>
			    	<ul class="list-group">
				     	<%
				     		
							for(HuntBean huntBean : listHunts) {
						%>
						<li class="list-group-item">
						<%
								out.print(huntBean.getHuntName());
							
				     	%>
				     		
				     		<div class="btn-group" role="group" aria-label="Basic example">
							  <button type="button" class="btn btn-secondary">Play</button>
							</div>
						</li>
					     	<%
								}
					     	%>
			    	</ul>
				</div>
				<div class="col-sm-3 text-center"> 
			     	<h2>Hunt</h2>
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
			     	<h2>Maps</h2>
			     	<ul class="list-group">
				     	<%
				     		ManageMapControl controllerMaps = new ManageMapControl();
							List<MapBean> mapBeans = controllerMaps.getAllMaps("a");
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
			</div>
		</div>
	</body>
</html>