<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="logic.bean.MapBean"%>
<%@page import="logic.bean.ZoneBean"%>
<%@page import="logic.control.ManageMapControl"%>
<%@page import="logic.enumeration.Pages"%>
<%@page import="logic.parser.Parser"%>
<%@ page language="java" import="java.util.*,java.lang.*"%>
<%@ page language="java" import="java.io.FileOutputStream"%>
<%@ page language="java" import="java.io.File"%>
<%@ page language="java" import="java.util.*,java.lang.*"%>
<%@ page language="java" import="java.net.URL"%>

<%
	if(session.getAttribute("username") == null && Pages.MANAGE_MAP.needLogin()){
		response.sendRedirect(Pages.LOGIN.getWebPath());
	}
	MapBean map = null;
	ManageMapControl controller = new ManageMapControl();
	if(session.getAttribute("map") != null){
		int idMap = Integer.valueOf(session.getAttribute("map").toString());
		
		map = controller.getMapById(session.getAttribute("username").toString(), idMap);
	
		if(session.getAttribute("zones") != null){
			List<?> list = (List<?>)session.getAttribute("zones");
			List<ZoneBean> zonesTemp = new ArrayList<>();
			for(int i = 0; i < list.size(); i++){
				ZoneBean zone = (ZoneBean)list.get(i);
				zonesTemp.add(i, zone);
			}
			
			map.setZones(zonesTemp);
		}
		else{
			session.setAttribute("zones", map.getZones());
		}
		if(request.getParameter("delete") != null){
			String zoneName = request.getParameter("delete").toString();
			List<ZoneBean> zones = map.getZones();
			for(int i = 0; i < zones.size(); i++){
				if(zones.get(i).getNameZone().equals(zoneName)){
					zones.remove(i);
				}
			}
			session.setAttribute("zones", map.getZones());
		}
	}
	if(request.getParameter("hiddenMapName") != null){
		String name = request.getParameter("hiddenMapName");
		String type = name.substring(0, 4);
		name = name.substring(4);
		map.setName(name);
		controller.save(session.getAttribute("username").toString(), map);
		if(type.equals("exit"));
			response.sendRedirect("MainMenu.jsp");
	}
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>ManageMap</title>
	<jsp:include page="NavBar.jsp"></jsp:include>
	<script type='text/javascript'>
		function saveMap(type){
			var name = document.getElementById("mapName").value;
			if(name == ""){
				alert("Insert Map name");
			} else {
				document.save.hiddenMapName.value = type + name;
				document.save.submit();
			}
			
		}
	</script>
</head>
<body>
		<div class="container-fluid text-center">    
		  	<div class="row content">
		  		<div class="col-sm-12 text-center"> 
		  			<h1>Manage Map</h1>
		  		</div>
		  	</div>
		  	<div class="row content">
		  		<div class="col-sm-6 text-center"> 
		  			<input type = "text" name = "name_map" placeholder = "Nome della mappa" id = "mapName"<%if(map != null) %>value = "<%= map.getName()%>"/>
		  		</div>
		  		<div class="col-sm-1 text-center"> 
		  			Zones
		  		</div>
		  		<div class="col-sm-5 text-center"> 
		  			
		  		</div>
		  	</div>
		  	<div class="row content">
		  		<div class="col-sm-6 text-center"> 
		  			<canvas id="canvas" width="350" height="350" style="border:1px solid #000000; background: url('<%= "uploads/" + map.getImage().substring(8)%>'); background-size: 350px 350px;">
					</canvas>
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
		  		<div class="col-sm-3 text-center"> 
		  			<%
					if(map != null){
						if(map.getZones() != null && !map.getZones().isEmpty()){
							%>
							<ul class="list-group">
					     	<%
							for(ZoneBean zone : map.getZones()){
								%>
								<li class="list-group-item">
									<form action = "ManageMap.jsp" name = "hunt<%out.print(zone.getNameZone());%>" method = "POST">
										<%
										out.print(zone.getNameZone());
						     			%>
							     		<div class="btn-group" role="group" aria-label="Basic example">
										  <button type="submit" name = "delete" value = "<%out.print(zone.getNameZone()); %>" class="btn btn-secondary">Delete</button>
										</div>
									</form>
								</li>
							     	<%
							}
					     	%>
					    	</ul>
					    	<%
						}
					}%>
		  		</div>
		  		<div class="col-sm-3 text-center"> 
		  			
		  		</div>
		  	</div>
	  		<div class="row content">
	  		<form action = "ManageMap.jsp" name = "save" method = "POST">
	  			<input type = "hidden" id = "hiddenMapName" name = "hiddenMapName">
		  		<div class="col-sm-3 text-center"> 
		  			<button type="button" name = "save" id = "save" onClick = "saveMap('save')">Salva</button>
		  		</div>
		  		<div class="col-sm-3 text-center"> 
		  			<button type="button" name = "save_exit" id = "exit" onClick = "saveMap('exit')">Salva e esci</button>
		  		</div>
	  		</form>
		  	</div>
	  	</div>
	</body>
</html>