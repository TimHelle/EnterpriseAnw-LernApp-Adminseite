<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.BufferedReader" %>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.URL" %>
<%@page import="java.net.URLConnection" %>
<%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="MainDiv" style="background-color: lightgray; margin-left:10%;margin-right:10%;box-shadow: 5px 5px 5px grey">
		<div class="NavigatorDiv" style="background-color: #24387f;height:75px">
			<table style="width:100%;">
			<tr>
				<td>
					<button>Question</button>
				</td>
				<td>
					<button>Category</button>
				</td>
				<td>
					<button>JSON</button>		
				</td>
			</tr>
		</table>
		</div>
		<table style="width:100%;height:45%">
			<tr>
				<td style="width:30%">
					<div class="UserListDiv" style="background-color: white;min-height:400px;">
						<div style="background-color: #24387f;color:white; height:35px">
							<h3>User</h3>
						</div>
						<div class=UserList>
							<c:forEach var="user" items="${users}" varStatus="i">
							  <table>
							    <tr>
							        <td>${user.id}</td>
							        <td>${user.username}</td>
							    </tr>
							  </table>
							</c:forEach>
						</div>
					</div>
				</td>
				<td style="width:30%">
					<div class="CategoryListDiv" style="background-color: white; min-height:400px;">
						<div style="background-color: #24387f;color:white;height:35px">
							<h3>Category</h3>
						</div>						
						<div class="CategoryListDiv">
						    <c:forEach var="category" items="${categories}" varStatus="i">
							  <table>
							    <tr>
							    	<td>${category.id}</td>
							        <td>${category.title}</td>
							        <td>${category.description}</td>
							    </tr>
							  </table>
							</c:forEach>
						</div>
					</div>				
				</td>
				<td style="width:30%">
					<div class="JSONDiv" style="background-color: white;min-height:400px;">
						<div style="background-color: #24387f;color:white;height:35px">
							<h3>Input JSON here</h3>
						</div>
						<div class="JSONTextfieldDiv" style="text-align:center;padding-bottom:1px;">
							<textarea type="text" id="JSONTextfield" style="width:90%;min-height:350px;max-width:400px"></textarea>
						</div>
					</div>				
				</td>
			</tr>
		</table>
		<table style="width:100%;height:65%">
			<tr>
				<td>
					<div class="QuestionDiv" style="background-color: white;min-height:400px;">
						<div style="background-color: #24387f;color:white;height:35px">
							<h3>QuestionDiv</h3>
						</div>			
						<div class="QuestionList">
							<c:forEach var="question" items="${questions}" varStatus="i">
							  <table>
							    <tr>
							        <td>${question.id}</td>
							        <td>${question.text}</td>
							        <td>${question.explanation}</td>
							    </tr>
							  </table>
							</c:forEach>
						</div>
					</div>	
				</td>
			</tr>
		</table>
	</div>
</body>
</html>