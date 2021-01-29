<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri= "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="MainDiv" style="background-color: lightgray; margin-left:10%;margin-right:10%;box-shadow: 5px 5px 5px grey">
		<form action="/AdminPageLearingApp/QuestionController" method="post">
			<table style="background-color: #24387f;height:75px;width:100%">
				<tr style="height:100%;width:100%">
					<td style="text-align:center;width:30%;color:white">
						<h1>Add a Question</h1>
					</td>		
				</tr>
			</table>
			<table style="width:100%;height:45%">
				<tr>
					<td style="width:50%">
						<div class="QuestionInformationDiv" style="background-color: white; min-height:400px;max-height:400px">
								<table style="background-color: #24387f;color:white; max-height:25px;width:100%">
									<tr>
										<td>
											<h3>Question information</h3>
										</td>
									</tr>
								</table>						
							<div class="QuestionCategoryDiv">
							    <div style="margin:5px;border-style: outset;border-width:2px">
							    	<table style="width:100%">
									    <tr style="width:100%">
									    	<td><a>Text of your question: </a></td>
									    </tr>			
									    <tr style="width:100%">
									    	<td><input name="questionText" style="width:98%" placeholder="Type in your question here?"></td>
									    </tr>	
									    <tr style="width:100%">
									    	<td><a>Text of your explanation: </a></td>
									    </tr>	
									    <tr style="width:100%">
									    	<td>
												<textarea type="text" name="explanationTextfield" style="width:90%;min-height:240px;max-height:240px;min-width:600px;max-width:600px" placeholder="This is the explanation of my question."></textarea>
											</td>
									    </tr>			        
								  </table>
							    </div>
							</div>
						</div>				
					</td>
					<td style="width:50%">
						<div class="JSONDiv" style="background-color: white;max-height:400px;min-height:400px">
							<table style="background-color: #24387f;color:white; max-height:25px;width:100%">
								<tr>
									<td>
										<h3>Category of Question</h3>
									</td>
								</tr>
							</table>
							<div class="QuestionCategoryDiv">
							    <div style="margin:5px;border-style: outset;border-width:2px">
							    	<table style="width:100%">
									    <tr>
									    	<td><a>Choose your category here:</a></td>
									    </tr>							        
								  	</table>
									<c:forEach var="categoryForQuestion" items="${categoriesForQuestion}" varStatus="i">
									    <div style="margin:5px;border-style: outset;border-width:2px">
									    	<table style="width:100%">
											    <tr>
											    	<td>${categoryForQuestion.title}</td>
											    	<td style="text-align:right"><input type="radio" name="selection" value="${categoryForQuestion.title}"></td>
											    </tr>							        
										  </table>
									    </div>
									</c:forEach>
							    </div>
							</div>	
						</div>				
					</td>
				</tr>
			</table>
			<table style="width:100%;height:65%">
				<tr>
					<td>
						<div class="answerOfQuestionDiv" style="background-color: white;min-height:300px;">
								<table style="background-color: #24387f;color:white; max-height:25px;width:100%">
									<tr>
										<td>
											<h3>Answer's of question</h3>
										</td>
									</tr>
								</table>		
							<div class="answerOfQuestion">
								<div style="margin:5px;border-style: outset;border-width:2px">
							    	<table style="width:100%">
									    <tr>
									    	<td><a>Right answer:</a></td>
									    </tr>							 
									    <tr>
									    	<td><input name="rightAnswerText" style="width:98%" placeholder="Type in your right answer here?"></td>
									    </tr>  
									    <!-- <tr>
									    	<td><input name="rightAnswerDescription" style="width:98%" placeholder="Type in your explanation here?"></td>
									    </tr> -->  
									    <tr>
									    	<td><a>Wrong answer one:</a></td>
									    </tr>							 
									    <tr>
									    	<td><input name="wrongAnswerOneText" style="width:98%" placeholder="Type in your wrong answer here?"></td>
									    </tr>
									    <!-- <tr>
									    	<td><input name="wrongAnswerOneDescription" style="width:98%" placeholder="Type in your explanation here?"></td>
									    </tr> -->
									    <tr>
									    	<td><a>Wrong answer two:</a></td>
									    </tr>							 
									    <tr>
									    	<td><input name="wrongAnswerTwoText" style="width:98%" placeholder="Type in your wrong answer here?"></td>
									    </tr> 
									    <!-- <tr>
									    	<td><input name="wrongAnswerTwoDescription" style="width:98%" placeholder="Type in your explanation here?"></td>
									    </tr> --> 
									    <tr>
									    	<td><a>Wrong answer three:</a></td>
									    </tr>							 
									    <tr>
									    	<td><input name="wrongAnswerThreeText" style="width:98%" placeholder="Type in your wrong answer here?"></td>
									    </tr> 
									    <!-- <tr>
									    	<td><input name="wrongAnswerThreeDescription" style="width:98%" placeholder="Type in your explanation here?"></td>
									    </tr> -->
								  </table>
							    </div>
							</div>
						</div>	
					</td>
				</tr>
			</table>
			<table style="width:100%">
				<tr>
					<td><input style="margin:2px;" name="cancelButton" type="submit" value="Cancel"></td>
					<td style="text-align:right;">
						<input style="margin:2px;background-color:green" name="submitButton" type="submit" value="Submit">				
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>