<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Create new Hunt</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
		<link href="Style.css" rel="stylesheet" type="text/css">
	</head>
	<body>
		<nav class="navbar navbar-inverse">
		  <div class="container-fluid">
		    <div class="navbar-header">
		      <a class="navbar-brand" href="#">Hunt</a>
		    </div>
		    <ul class="nav navbar-nav">
		      <li><a href="#">Page 2</a></li>
		      <li><a href="#">Page 3</a></li>
		    </ul>
		  </div>
		</nav>
		  
		 <form action="/action_page.php">
		  <div class="form-group">
		    <label for="huntName">Hunt Name:</label>
		    <input type="email" class="form-control" id="huntName">
		  </div>
		  <div class="form-group">
		    <label for="objectName">Add an Object for your Hunt. That can be a reward for getting a Riddle or the solution of a Riddle <br>Insert object name</label>
		    <input type="text" class="form-control" id="objectName">
		  </div>
		  <button type="submit" class="btn btn-default">Add image for your object</button>
		  <div class="form-group">
		    <label for="objectName">Insert the description of an Object</label>
		    <input type="text" class="form-control" id="objectName">
		  </div>
		  <button type="submit" class="btn btn-default">Add object</button>
		  </form> 
		  <div class="container">
		  <h2>Inserted object</h2>         
		  <table class="table table-bordered">
		    <thead>
		      <tr>
		        <th>Object Name</th>
		        <th>Object Description</th>
		        <th>Path</th>
		      </tr>
		    </thead>
		
		  </table>
		</div>
		
		<h2>Insert new Riddle</h2>
		
		<form action="/action_page.php">
		  <div class="form-group">
		    <label for="riddleText">Insert riddle text</label>
		    <input type="email" class="form-control" id="riddleText">
		  </div>
		  <div class="form-group">
		    <label for="riddleSolution">Insert riddle solution</label>
		    <input type="text" class="form-control" id="riddleSolution">
		  </div>
		  <div class="form-group">
		    <label for="objectName">Insert the clues of a Riddle</label>
		    <input type="text" class="form-control" id="clue1" placeholder ="Clue number 1">
		    <input type="text" class="form-control" id="clue2" placeholder ="Clue number 2">
		    <input type="text" class="form-control" id="clue3" placeholder ="Clue number 3">
		    
		  </div>
		  <button type="submit" class="btn btn-default">Add riddle</button>
		  </form> 
		  <div class="container">
		  <h2>Inserted Riddle</h2>         
		  <table class="table table-bordered">
		    <thead>
		      <tr>
		        <th>Riddle Name</th>
		        <th>Riddle Solution</th>
		        <th>Riddle Clue 1</th>
		        <th>Riddle Clue 2</th>
		        <th>Riddle Clue 3</th>
		      </tr>
		    </thead>
		  </table>
		</div>
	</body>
</html>