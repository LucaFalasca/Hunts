<%@page import="logic.bean.HuntBean"%>
<%@page import="logic.bean.RiddleBean"%>
<%@page import="logic.bean.ObjectBean"%>
<%@page import="logic.bean.MapBean"%>
<%@page import="logic.bean.ZoneBean"%>
<%@page import="logic.enumeration.Pages"%>
<%@page import="logic.control.ManageHuntControl"%>
<%@page import="logic.parser.Parser"%>
<%@page import="logic.control.ManageMapControl" %>
<%@page import="logic.exception.DatabaseException" %>
<%@page import="logic.enumeration.Pages" %>
<%@ page language="java" import="java.util.*,java.lang.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<jsp:useBean id="riddle" scope="request" class="logic.bean.RiddleBean"/>
<jsp:setProperty name="riddle" property="riddle"/>
<jsp:setProperty name="riddle" property="solution"/>
<jsp:setProperty name="riddle" property="clue1"/>
<jsp:setProperty name="riddle" property="clue2"/>
<jsp:setProperty name="riddle" property="clue3"/>
<jsp:setProperty name="riddle" property="objectName"/>
<jsp:setProperty name="riddle" property="zoneName"/>

<jsp:useBean id="object" scope="request" class="logic.bean.ObjectBean"/>
<jsp:setProperty name="object" property="name"/>
<jsp:setProperty name="object" property="description"/>

<%

	List<RiddleBean> riddles = new ArrayList<>();
	List<ObjectBean> objects = new ArrayList<>();
	List<MapBean> maps = new ArrayList<>();
	ManageHuntControl controllerHunt = new ManageHuntControl();
	ManageMapControl controllerMap = new ManageMapControl();
	HuntBean hunt = null;
	MapBean map = null;
	String username = null;
	
	if(session.getAttribute("username") == null && Pages.LOGIN.needLogin()){
		response.sendRedirect(Pages.LOGIN.getWebPath());
	} else {
		username = session.getAttribute("username").toString();
	}
	

	maps = controllerMap.getAllMaps(username);

	if(session.getAttribute("hunt") != null){
		hunt = (HuntBean) session.getAttribute("hunt");
		if(hunt.getIdHunt() != 0){
			hunt = controllerHunt.getHunt(hunt.getIdHunt(), username);
			map = hunt.getMap();
			riddles.addAll(hunt.getRiddle());
			objects.addAll(hunt.getObject());
		}
	}
	
	if(session.getAttribute("riddles") != null){
		List<?> list = (List<?>)session.getAttribute("riddles");
		List<RiddleBean> riddlesTemp = new ArrayList<>();
		for(int i = 0; i < list.size(); i++){
			RiddleBean rid = (RiddleBean)list.get(i);
			riddlesTemp.add(i, rid);
		}
		riddles = riddlesTemp;
	} else {
		session.setAttribute("riddles", riddles);
	}
	
	if(session.getAttribute("objects") != null){
		List<?> list = (List<?>)session.getAttribute("objects");
		List<ObjectBean> objectTemp = new ArrayList<>();
		for(int i = 0; i < list.size(); i++){
			ObjectBean obj = (ObjectBean)list.get(i);
			objectTemp.add(i, obj);
		}
		objects = objectTemp;
	} else {
		session.setAttribute("objects", objects);
	}
	
	if(request.getParameter("riddleHidden") != null){
		String nameHunt = request.getParameter("riddleHidden");
		riddles.add(riddle);
		session.setAttribute("riddles", riddles);
		
		if(!nameHunt.equals("submit")){
			if(hunt == null)
				hunt = new HuntBean();
			hunt.setHuntName(nameHunt);
			session.setAttribute("hunt", hunt);
		}
	}
	
	if(request.getParameter("obejctHidden") != null){
		String nameHunt = request.getParameter("obejctHidden");
		objects.add(object);
		session.setAttribute("objects", objects);
		
		if(!nameHunt.equals("submit")){
			if(hunt == null)
				hunt = new HuntBean();
			hunt.setHuntName(nameHunt);
			session.setAttribute("hunt", hunt);
		}
	}
	
	if(request.getParameter("deleteRiddle") != null){
		int posRiddle = Integer.valueOf(request.getParameter("deleteRiddle"));
		riddles.remove(posRiddle);
		session.setAttribute("riddles", riddles);
	}
	
	if(request.getParameter("deleteObject") != null){
		int posObject = Integer.valueOf(request.getParameter("deleteObject"));
		objects.remove(posObject);
		session.setAttribute("objects", objects);
	}
	
	if(request.getParameter("chooseName") != null) {
        String mapName = request.getParameter("chooseName");
        if(maps != null){
        	for(MapBean mapBean : maps){
        		if(mapBean.getName() == mapName){
        			map = controllerMap.getMapById(username, mapBean.getId());
        			System.out.println(map);
        		}
        	}
        }
 	}
	
		if(request.getParameter("saveName") != null){
		String name = request.getParameter("saveName");
		String type = name.substring(0, 4);
		name = name.substring(4);

		ManageHuntControl controller = new ManageHuntControl();
		
		if(hunt == null || hunt.getIdHunt() == 0){
			hunt = new HuntBean();
			hunt.setIdHunt(-1);
		}
		
		if(type.equals("publ")){
			hunt.setPrivate(false);
		} else {
			hunt.setPrivate(true);
		}
		
		hunt.setHuntName(name);
		hunt.setRiddle(riddles);
		hunt.setObject(objects);
		hunt.setUsername(username);
		hunt.setMap(map);
		int idHunt = controller.saveHunt(hunt);
		hunt.setIdHunt(idHunt);
		session.setAttribute("hunt", hunt);
		if(!type.equals("save")){		
			response.sendRedirect(Pages.MAIN_MENU.getWebPath());
		}
	}
	
	

%>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Create new Hunt</title>
		<jsp:include page="NavBar.jsp"></jsp:include>

		
		<script type='text/javascript'>
			function getName(){
				var nome = document.getElementById('chooseMap').value;
				if(nome != ""){
					document.formMap.chooseName.value = nome;
					document.formMap.submit();
				}
			}
			
			function getHuntName(type){
				var nome = document.getElementById('huntName').value;
				if(nome != ""){
					document.saveForm.saveName.value = type+nome;
					document.saveForm.submit();
				} else {
					alert("Insert the hunt's name before save");
				}
			}
			
			function isEmptyRiddle(){
				
				var text = document.getElementById("riddle").value;
				var solution = document.getElementById("solution").value;
				var hidden = document.getElementById("riddleHidden");
				if((text == "" || text == undefined) && (solution == "" || solution == undefined)){
					alert("Insert at least the riddle's text and solution");
				} else {
					if(document.getElementById("huntName").value == "")
						hidden.value = "submit";
					else
						hidden.value = document.getElementById("huntName").value;
					
					document.riddlePost.submit();
				}
			}
			
			function isEmptyObject(){
				
				var name = document.getElementById("name").value;
				var hidden = document.getElementById("objectHidden");
				
				if(name == "" || name == undefined){
					alert("Insert at least the object's name");
				} else {
					if(document.getElementById("huntName").value == "")
						hidden.value = "submit";
					else
						hidden.value = document.getElementById("huntName").value;
					document.objectPost.submit();
				}
			}
			
		</script>
	</head>
	<body>
		<div class="container">
			<div class="row" id = "name_hunt">
				<div class="form-group">
				   <label for="huntName">Hunt Name:</label>
				   <%if(hunt != null){ %>
				   <input type="text" class="form-control" id="huntName" value = "<%= hunt.getHuntName() %>">
				   <%}else{ %>
				   <input type="text" class="form-control" id="huntName" value = "">
				   <%} %>
				</div> 
			</div>
			<div class="row">
				<div class="col-lg-4 form-group" id = "insert_object">
					<h2>Insert object</h2>      
					<form name = "objectPost">
						<div class="form-group">
				 			<label for="objName">Name:</label>
				 			<input type="text" class="form-control" name="name" id="name">
						</div>
						<div class="form-group">
						    <label for="description">Description:</label>
						    <input type="text" class="form-control" name="description">
						</div>
						<input type="hidden" name="obejctHidden" id="objectHidden">
						<input type="button" name="addObject" value="Add object" onClick = "isEmptyObject()">
					</form>
				</div>
				<div class="col-lg-4 form-group" id = "object_list">
					<div> 
						<table class="table table-bordered" id = "objectTable">
						    <thead id = "theadObject">
						      <tr>
						        <th>
						        	Object Name
						        </th>
						        <th>
						        	Object Description
						        </th>
						      </tr>
						    </thead>
							<tbody id = "tbodyObject">
								<%
									for(int i = 0; i < objects.size(); i++){
										ObjectBean obj = objects.get(i);
								%>
									<tr>
										<th>
											<%=
												obj.getName()
											%>
										</th>
										<th>
											<%=
												obj.getDescription()	
											%>
										</th>
										<th>
											<form action = "ManageHunt.jsp" method = "POST" name = "deleteObject">
												<button type = "submit" name = "deleteObject" value= "<%= i %>"> Delete</button>
											</form>
										</th>
									</tr>
								<%
									}
								%>
							</tbody>	
						</table>
					</div>
				</div>
				<div class="col-lg-4 form-group" id = "map">
					<div class="chooseMapConteiner">
						<div class="form-group">
						
						  <label for="chooseMap">Choose map:</label>
						  <select class="form-control" name="chooseMap" id = "chooseMap">
						  	<option>
						  	</option>
						  	<% 
						  		for(MapBean mapBean : maps){ 
						  	%>
						  	<option>
						  		<%= mapBean.getName() %>
						  	</option>
						  	<%
						  		}
						  	%>
						  </select>
						  <form action = "ManageHunt.jsp" method = "POST" name = "formMap" >
						  		<INPUT TYPE="hidden" name="chooseName">
								<button type = "button" name = "chooseMapButton" onClick = "getName()"> Choose</button>
						  </form>
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
				</div>
			</div>
			<div class="row">
				<div class="col-lg-4 form-group" id = "insert_riddle">
					<h2>Insert new Riddle</h2>
					
					<form action = "ManageHunt.jsp" method = "POST" name = "riddlePost" id = "riddlePost">
			  			<div class="form-group">
			    			<label for="riddle">Text:</label>
			    			<input type="text" class="form-control" name="riddle" id = "riddle">
			  			</div>
			  			<div class="form-group">
						    <label for="solution">Solution:</label>
						    <input type="text" class="form-control" name="solution" id = "solution">
						</div>
						<div class="form-group">
						    <label for="clue1">Clue 1:</label>
						    <input type="text" class="form-control" name="clue1">
						</div>
						<div class="form-group">
						    <label for="clue2">Clue 2:</label>
						    <input type="text" class="form-control" name="clue2">
						</div>
						<div class="form-group">
						    <label for="clue3">clue 3</label>
						    <input type="text" class="form-control" name="clue3">
						</div>
						<div class="form-group">
						  <label for="objectName">Object list:</label>
						  <select class="form-control" name="objectName">
						  	<option></option>
						  	<%
						  		for(ObjectBean obj : objects){
						  	%>
						  	<option>
						  		<%=
						  			obj.getName()
						  		%> 
						  	</option>
						  	<%
						  		}
						  	%>
						  </select>
						</div> 
						<div class="form-group">
						  <label for="zoneName">Zone list:</label>
						  <select class="form-control" name="zoneName">
						  	<option></option>
						  
						  </select>
						</div> 
						<input type="hidden" name="riddleHidden" id="riddleHidden">
						<input type="button" name="addRiddle" value="Add riddle" onClick = "isEmptyRiddle()">
					</form> 
				</div>
				<div class="col-lg-8 form-group" id = "riddle_list">
					<div class="container">
					<h2>Inserted Riddle</h2>         
					<table class="table table-bordered" id = "riddleTable">
						<thead id = theadRiddle>
							<tr>
								<th>
									Riddle Text
								</th>
								<th>
									Riddle Solution
								</th>
								<th>
									Clue 1
								</th>
								<th>
									Clue 2
								</th>
								<th>
									Clue 3
								</th>
								<th>
									Object
								</th>
								<th>
									Zone
								</th>
							</tr>
						</thead>
						<tbody id = "tbodyRiddle">
							<%
								for(int i = 0; i < riddles.size(); i++){
									RiddleBean rid = riddles.get(i);
							%>
								<tr>
									<th>
										<%=
											rid.getRiddle()	
										%>
									</th>
									<th>
										<%=
											rid.getSolution()
										%>
									</th>
									<th>
										<%=
											rid.getClue1()
										%>
									</th>
									<th>
										<%=
											rid.getClue2()
										%>
									</th>
									<th>
										<%=
											rid.getClue3()
										%>
									</th>
									<th>
										<%=
											rid.getObjectName()
										%>
									</th>
									<th>
										<%=
											rid.getZoneName()
										%>
									</th>
									<th>
										<form action = "ManageHunt.jsp" method = "POST" name = "deleteRiddle">
											<button type = "submit" name = "deleteRiddle" value= "<%= i %>"> Delete</button>
										</form>
									</th>
								</tr>
							<%
								}
							%>
						</tbody>
					</table><br/>	
					
				</div>
				</div>
			</div>
			<div class="row" id = "save_buttons">
				<form action = "ManageHunt.jsp" method = "POST" name = "saveForm">
					<INPUT TYPE="hidden" name="saveName">
					<button type = "button" name = "save" onClick = "getHuntName('save')" value = "Save"> Save</button>
					<button type = "submit" name = "exit" onClick = "getHuntName('exit')" value= "Save&Exit">Save and Exit</button>
					<button type = "submit" name = "publish" onClick = "getHuntName('publ')" value= "Publish">Publish</button>
				</form>
			</div>
		</div>
	</body>
</html>