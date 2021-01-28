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
		<form action="/AdminPageLearingApp/CategoryController" method="post">
			<table style="background-color: #24387f;height:75px;width:100%">
				<tr style="height:100%;width:100%">
					<td style="text-align:center;width:30%;color:white">
						<h1>Add a Category</h1>
					</td>		
				</tr>
			</table>
			<table style="width:100%;height:45%">
				<tr>
					<td style="width:50%">
						<div class="CategoryListDiv" style="background-color: white; min-height:400px;max-height:400px">
							<table style="background-color: #24387f;color:white; max-height:25px;width:100%">
								<tr>
									<td>
										<h3>Already available categories</h3>
									</td>
								</tr>
							</table>
							<div class="CategoryListDiv">
							    <c:forEach var="alreadyAvailableCategory" items="${alreadyAvailableCategories}" varStatus="i">
								    <div style="margin:5px;border-style: outset;border-width:2px">
								    	<table style="width:100%">
										    <tr>
										    	<td style="font-weight:bold">${alreadyAvailableCategory.title}</td>
										    </tr>			
										    <tr>
										    	<td>${alreadyAvailableCategory.description}</td>
										    </tr>					        
									  </table>
								    </div>
								</c:forEach>
							</div>				
						</div>				
					</td>
					<td style="width:50%">
						<div class="CategoryInformationDiv" style="background-color: white; min-height:400px;max-height:400px">
								<table style="background-color: #24387f;color:white; max-height:25px;width:100%">
									<tr>
										<td>
											<h3>Category information</h3>
										</td>
									</tr>
								</table>						
							<div class="CategoryDiv">
							    <div style="margin:5px;border-style: outset;border-width:2px">
							    	<table style="width:100%">
									    <tr style="width:100%">
									    	<td><a>Title of your category: </a></td>
									    </tr>			
									    <tr style="width:100%">
									    	<td><input name="categoryTitle" style="width:98%" placeholder="Type in your category here?"></td>
									    </tr>	
									    <tr style="width:100%">
									    	<td><a>Description of your category: </a></td>
									    </tr>	
									    <tr style="width:100%">
									    	<td>
												<textarea type="text" name="descriptionCategoryTextfield" style="width:90%;min-height:240px;max-height:240px;min-width:600px;max-width:600px" placeholder="This is the description of my category."></textarea>
											</td>
									    </tr>			        
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