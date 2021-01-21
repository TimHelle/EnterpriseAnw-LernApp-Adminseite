<%@page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.io.BufferedReader" %>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.net.URL" %>
<%@page import="java.net.URLConnection" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="MainDiv" style="background-color: yellow; margin-left:10%;margin-right:10%;box-shadow: 5px 5px 5px grey">
		<div class="NavigatorDiv" style="background-color: darkblue">
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
				<td>
					<div class="UserListDiv" style="background-color: blue">
						<h3>User</h3>
						<div class=UserList>
						
						</div>
					</div>
				</td>
				<td>
					<div class="CategoryListDiv" style="background-color: red">
						<h3>Category</h3>
						<div class="CategoryList">
							<%
						       String recieve;
						       String buffer = "";
						       URL jsonpage = new URL("http://51.137.215.185:9000/api/categories");
						       URLConnection urlcon = jsonpage.openConnection();
						       BufferedReader buffread = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
						
						       while ((recieve = buffread.readLine()) != null)
						        buffer += recieve;
						       buffread.close();
						
						       System.out.println(buffer);
						    %>
						    
						    <c:forEach var="categories" items="${categories}" varStatus="i">
							   <c:set var="categoryID" value="${categories.Id}"/>
							  <table>
							    <tr class="tr1">
							        <td>${jobs.Topic}</td>
							        <td>${stats.get(i.index).No}</td>
							    </tr>
							  </table>
							</c:forEach>
							
							
						</div>
					</div>				
				</td>
				<td>
					<div class="JSONDiv" style="background-color: green;">
						<h3>Input JSON here</h3>
						<div class="JSONTextfieldDiv" style="text-align:center;padding-bottom:1px">
							<textarea type="text" id="JSONTextfield" style="width:99%;height:250px;margin:0 auto"></textarea>
						</div>
					</div>				
				</td>
			</tr>
		</table>
		<div class="QuestionDiv" style="background-color: pink">
			<h3>QuestionDiv</h3>
			<div class="QuestionList">
			
			</div>
		</div>	
	</div>
</body>
</html>