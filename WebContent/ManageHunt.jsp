<%@page import="logic.bean.HuntBean"%>
<%@page import="logic.bean.RiddleBean"%>
<%@page import="logic.bean.ObjectBean"%>
<%@page import="logic.enumeration.Pages"%>
<%@page import="logic.control.ManageHuntControl"%>
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

	if(session.getAttribute("username") == null && Pages.LOGIN.needLogin()){
		response.sendRedirect(Pages.LOGIN.getWebPath());
	} else {
		String username = session.getAttribute("username").toString();
	}
	
	List<RiddleBean> riddles = new ArrayList<>();
	List<ObjectBean> objects = new ArrayList<>();
	HuntBean hunt = null;
	ManageHuntControl controller = new ManageHuntControl();
	
	if(session.getAttribute("hunt") != null){
		int idHunt = Integer.valueOf(session.getAttribute("hunt").toString());
		hunt = controller.getHunt(idHunt, "pippo");
		riddles.addAll(hunt.getRiddle());
		objects.addAll(hunt.getObject());
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
	
	if(request.getParameter("addRiddle") != null){
		riddles.add(riddle);
		session.setAttribute("riddles", riddles);
	}
	
	if(request.getParameter("addObject") != null){
		objects.add(object);
		session.setAttribute("objects", objects);
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
	
	if(request.getParameter("save") != null){
		hunt.setPrivate(true);
		String idHunt = save(hunt, "pippo", riddles, objects, "try");
		session.setAttribute("hunt", idHunt);
		response.sendRedirect("ManageHunt.jsp");
	}
	
	if(request.getParameter("exit") != null){
		hunt.setPrivate(true);
		String idHunt = save(hunt, "pippo", riddles, objects, "try");
		session.setAttribute("hunt", idHunt);
		response.sendRedirect("MainMenu.jsp");
	}
	
	if(request.getParameter("publish") != null){
		hunt.setPrivate(false);
		String idHunt = save(hunt, "pippo", riddles, objects, "try");
		session.setAttribute("hunt", idHunt);
		response.sendRedirect("MainMenu.jsp");
	}
	
	

%>

<%!
	private String save(HuntBean huntBean, String username, List<RiddleBean> riddles, List<ObjectBean> objects, String name){
		ManageHuntControl controller = new ManageHuntControl();
		if(huntBean == null){
			huntBean.setIdHunt(-1);
		}
		huntBean.setHuntName("try");
		huntBean.setRiddle(riddles);
		huntBean.setObject(objects);
		huntBean.setUsername(username);
		return String.valueOf(controller.saveHunt(huntBean));
	}
%>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Create new Hunt</title>
		<jsp:include page="NavBar.jsp"></jsp:include>

		
		<script type='text/javascript'>
			
		</script>
	</head>
	<body>
		 <div class="form-group">
		    <label for="huntName">Hunt Name:</label>
		    <input type="text" class="form-control" id="huntName">
		 </div> 
		 
		 <h2>Insert object</h2>      
		 <form>
  			<div class="form-group">
    			<label for="objName">Name:</label>
    			<input type="text" class="form-control" name="name">
  			</div>
  			<div class="form-group">
			    <label for="description">Description:</label>
			    <input type="text" class="form-control" name="description">
			</div>
			<input type="submit" name="addObject" value="Add object">
		 </form>
		 
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
		
		<h2>Insert new Riddle</h2>
		
		<form action = "ManageHunt.jsp" method = "POST" name = "riddlePost">
  			<div class="form-group">
    			<label for="riddle">Text:</label>
    			<input type="text" class="form-control" name="riddle">
  			</div>
  			<div class="form-group">
			    <label for="solution">Solution:</label>
			    <input type="text" class="form-control" name="solution">
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
			<input type="submit" name="addRiddle" value="Add riddle">
		</form> 
		
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
		<form action = "ManageHunt.jsp" method = "POST" name = "save">
			<button type = "submit" name = "save" value= "Save">Save</button>
			<button type = "submit" name = "exit" value= "Save&Exit">Save & Exit</button>
			<button type = "submit" name = "publish" value= "Publish">Publish</button>
		</form>
	</body>
</html>