<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		<!-- <form action="/AdminPageLearingApp/AdminPage" method="post"> -->
			<table style="background-color: #24387f;height:75px;width:100%">
				<tr style="height:100%;width:100%">
					<td style="text-align:center;width:30%;">
						<button class="AddNewQuestion" onclick="window.location.href='/AdminPageLearingApp/QuestionController'">Add Question</button>
					</td>
					<td style="text-align:center;width:30%">
						<button class="AddNewCategory" onclick="window.location.href='/AdminPageLearingApp/CategoryController'">Add Category</button>
					</td>
				</tr>
			</table>
			<table style="width:100%;height:45%">
				<tr>
					<td style="width:30%">
						<div class="CategoryListDiv" style="background-color: white; height:100%;">
							<table style="background-color: #24387f;color:white; max-height:25px;width:100%">
								<tr>
									<td>
										<h3>Category</h3>
									</td>
									<td style="text-align:right">
										<button class="AddNewCategory" onclick="window.location.href='/AdminPageLearingApp/CategoryController'" style="text-align:center;border-radius:12px">Add</button>
									</td>
								</tr>
							</table>						
							<div class="CategoryListDiv" style="max-height:70%;overflow:auto">
							    <c:forEach var="category" items="${categories}" varStatus="i">
							    <div style="margin:5px;border-style: outset;border-width:2px">
							    	<table style="width:100%">
									    <tr>
									    	<td style="font-weight:bold; width:90%">${category.title}</td>
									    	<td><button onclick="window.location.href='/AdminPageLearingApp/DeleteCategoryController?id=${category.id}'">Delete</button></td>
									    </tr>			
									    <tr>
									    	<td>${category.description}</td>
									    </tr>					        
								  </table>
							    </div>
								</c:forEach>
							</div>
						</div>				
					</td>
					<td style="width:70%">
						<div class="JSONDiv" style="background-color: white; height:100%;width:100%">
								<table style="background-color: #24387f;color:white; max-height:25px;width:100%">
									<tr>
										<td>
											<h3>Input JSON</h3>
										</td>
										<td style="text-align:right">
											<button class="AddJSONContent" name="sendJson" style="text-align:center;border-radius:12px">Send</button>
										</td>
									</tr>
								</table>	
							<div class="JSONTextfieldDiv" style="text-align:center;padding-bottom:1px;height:87%">
								<textarea type="text" name="JSONTextfield" style="width:98%;height:100%"></textarea>
							</div>
						</div>				
					</td>
				</tr>
			</table>
			<table style="width:100%;height:65%">
				<tr>
					<td>
						<div class="QuestionDiv" style="background-color: white;">
							<table style="background-color: #24387f;color:white; max-height:25px;width:100%">
								<tr>
									<td>
										<h3>Question</h3>
									</td>
									<td style="text-align:right">
										<button class="AddNewQuestion" onclick="window.location.href='/AdminPageLearingApp/QuestionController'" style="text-align:center;border-radius:12px">Add</button>
									</td>
								</tr>
							</table>
						</div>
						<div style="background-color: white;min-height:400px;overflow:auto">
							<c:forEach var="question" items="${questions}" varStatus="i">
								<div style="margin:5px;border-style: outset;border-width:2px">
							    	<table style="width:100%">
									    <tr>
									    	<td style="width:70%">${question.text}</td>
									    	<td style="text-align:right;width:30%">${question.category.title}</td>
									    	<td><button onclick="window.location.href='/AdminPageLearingApp/DeleteQuestionController?id=${question.id}'">Delete</button></td>
									    </tr>							        
								  </table>
								  <table style="width:100%;margin-top:10px">
								    	<tr>
								    		<td>
										    	<c:forEach var="answer" items="${question.answers}">
										    		<td style="text-align:center;width:25%">${answer.text}</td>
										    	</c:forEach>
										    </td>
									    </tr>	
								    </table>
							    </div>
							</c:forEach>
						</div>	
					</td>
				</tr>
			</table>
		<!-- </form> -->
	</div>	
	<script type="text/javascript">
		function deleteQuestion(id){
			console.log(id);
			url = "http://51.137.215.185:9000/api/questions/{" + id + "}"
			fetch(url,{
				method:'delete',
				})
				.then(respone =>response.json());
		}
	</script>
</body>
</html>