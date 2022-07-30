<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" 
href="https://cdn.jsdelivr.net/npm/bootstrap@4.4.1/dist/css/bootstrap.min.css" 
integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" 
crossorigin="anonymous">
</head>
<body>
	<header>
		<nav class="navbar navbar-expand-md navbar-dark" 
		style="background-color: #777"
		>
			<div>
				<a href="http://localhost:8080/UserMangegment/"  
				class="navbar-brand">
				User Management Application</a>
			</div>	
			<ul>
				<li><a href="<%= request.getContextPath()%>/list"
				class="nav-link" style="color: white">Users List</a></li>
			</ul>
		</nav>
	</header>
	<br>
	<div class="container col-md-5">
		<div class="card">
			<div class="card-body">
				<c:if test="${user != null}">
					<form action="update" method="post">
				</c:if>
				<c:if test="${user == null}">
					<form action="insert" method="post">
				</c:if>
				
				<caption>
					<h2>
						<c:if test="${user != null}">
							Edit User
						</c:if>
						<c:if test="${user == null}">
							Add New User
						</c:if>
					</h2>
				</caption>
				<c:if test="${user != null}">
					<input type="hidden" name="id" value="<c:out value='${user.id}'/>">
				</c:if>
				<fieldset class="form-group">
					<label>User Name</label>
					<input type="text" value="<c:out value='${user.name}'/>" 
					class="form-control" name="name" required="required">
				</fieldset>
				<fieldset class="form-group">
					<label>User Email</label>
					<input type="text" value="<c:out value='${user.email}'/>" 
					class="form-control" name="email" >
				</fieldset>
				<fieldset class="form-group">
					<label>User Country</label>
					<input type="text" value="<c:out value='${user.country}'/>" 
					class="form-control" name="country" >
				</fieldset>
				<button type="submit" class="btn btn-success">Save</button>
				</form>
			</div>
		</div>
	</div>

</body>
</html>