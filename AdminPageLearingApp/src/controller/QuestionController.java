package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Model.AnswerModel;
import Model.CategoryModel;
import Model.QuestionModel;
import Model.SendCategoryModel;
import Model.SendQuestionModel;

import controller.ServiceHelper;

/**
 * Servlet implementation class QuestionController
 */
@WebServlet("/QuestionController")
public class QuestionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServiceHelper.getCategories();
		request.setAttribute("categoriesForQuestion", CategoryModel.getCategoryList());
		try {
			RequestDispatcher reqDis = request.getRequestDispatcher("AddQuestionPage.jsp");
			reqDis.forward(request, response);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		URL backendURL = new URL("http://51.137.215.185:9000/api/questions");
		HttpURLConnection connection = (HttpURLConnection) backendURL.openConnection();
		if(request.getParameter("submitButton") != null)
		{
			SendQuestionModel question = new SendQuestionModel();
			CategoryModel category = new CategoryModel();
			AnswerModel rightAnswer = new AnswerModel();
			AnswerModel wrongAnswerOne = new AnswerModel();
			AnswerModel wrongAnswerTwo = new AnswerModel();
			AnswerModel wrongAnswerThree = new AnswerModel();
			SendCategoryModel sendCategory = new SendCategoryModel();
			boolean filled = false;			
			StringBuilder sb = new StringBuilder();					
			if (request.getParameter("questionText") != null && 
				request.getParameter("explanationTextfield") != null && 
				request.getParameter("selection") != null &&
				request.getParameter("rightAnswerText") != null &&
			    request.getParameter("wrongAnswerOneText") != null &&
			    request.getParameter("wrongAnswerTwoText") != null &&
			    request.getParameter("wrongAnswerThreeText") != null) 
			{				
				question.setText(request.getParameter("questionText"));
				question.setExplanation(request.getParameter("explanationTextfield"));
				category.setTitle(request.getParameter("selection"));
				
				rightAnswer.setText(request.getParameter("rightAnswerText"));
				rightAnswer.setIsCorrect(true);
				//rightAnswer.setDescription(request.getParameter("rightAnswerDescription"));
				wrongAnswerOne.setText(request.getParameter("wrongAnswerOneText"));
				wrongAnswerOne.setIsCorrect(false);
				//wrongAnswerOne.setDescription(request.getParameter("wrongAnswerOneDescription"));
				wrongAnswerTwo.setText(request.getParameter("wrongAnswerTwoText"));
				wrongAnswerTwo.setIsCorrect(false);
				//wrongAnswerTwo.setDescription(request.getParameter("wrongAnswerTwoDescription"));
				wrongAnswerThree.setText(request.getParameter("wrongAnswerThreeText"));
				wrongAnswerThree.setIsCorrect(false);
				//wrongAnswerThree.setDescription(request.getParameter("wrongAnswerThreeDescription"));	
				
				question.setAnswer(rightAnswer);
				question.setAnswer(wrongAnswerOne);
				question.setAnswer(wrongAnswerTwo);
				question.setAnswer(wrongAnswerThree);
				
				for(CategoryModel item : CategoryModel.getCategoryList()) {
					if(category.getTitle().equals(item.getTitle())) {
						category.setDescription(item.getDescription());
						category.setId(item.getId());
					}
				}
				sendCategory.setId(category.getId());
				question.setCategory(sendCategory);
				
				try {
					question.setHash(ServiceHelper.hashQuestion(question));
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				filled = true;
			}
			
			if(filled == true)
			{
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/json; utf-8");
				connection.setDoOutput(true);
				try(OutputStream os = connection.getOutputStream()) 
				{
					byte[] input = ServiceHelper.sendQuestionModelToJson(question).getBytes("utf-8");
					os.write(input,0,input.length);
				}
				catch(Exception ex)
				{
					connection.disconnect();
					System.out.println("Send request error: \n" + ex.getMessage());
				}
				StringBuilder responseString = new StringBuilder();
				try {
					BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));			    
				    String responseLine = null;
				    while ((responseLine = br.readLine()) != null) {
				        responseString.append(responseLine.trim());
				    }
				    System.out.println("Response Stream: " + response.toString());
				}
				catch(Exception ex)
				{
					connection.disconnect();
					System.out.println("Get response error: \n" + ex.getMessage());
				}
				response.sendRedirect("/AdminPageLearingApp/AdminPage");
				connection.disconnect();
				//response.getOutputStream().println(responseString.toString());
			}
			else {
				sb.append("Question: " + request.getParameter("questionText") + "\n");
				//sb.append("Question explanation: " + request.getParameter("explanationTextfield")+ "\n");
				sb.append("Selection: " + request.getParameter("selection")+ "\n");
				sb.append("Right answer: " + request.getParameter("rightAnswerText")+ "\n");
				sb.append("Wrong answer: " + request.getParameter("wrongAnswerOneText")+ "\n");
				sb.append("Wrong answer: " + request.getParameter("wrongAnswerTwoText")+ "\n");
				sb.append("Wrong answer: " + request.getParameter("wrongAnswerThreeText")+ "\n");
				System.out.println(sb.toString() + "\n" + "There is something missing in question!");
				connection.disconnect();
			}			
		}
		else
		{
			connection.disconnect();
			response.sendRedirect("/AdminPageLearingApp/AdminPage");
			System.out.println("SubmitButton not found.");
		}
		
	}		
}
