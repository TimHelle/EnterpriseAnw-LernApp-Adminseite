package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Model.AnswerModel;
import Model.CategoryModel;
import Model.QuestionModel;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		getCategories();
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
		if(request.getParameter("submitButton") != null)
		{
			QuestionModel question = new QuestionModel();
			CategoryModel category = new CategoryModel();
			List<CategoryModel> list = new ArrayList<CategoryModel>();
			AnswerModel rightAnswer = new AnswerModel();
			AnswerModel wrongAnswerOne = new AnswerModel();
			AnswerModel wrongAnswerTwo = new AnswerModel();
			AnswerModel wrongAnswerThree = new AnswerModel();
			boolean filled = false;			
			StringBuilder sb = new StringBuilder();
			list = CategoryModel.getCategoryList();
			
			System.out.println("Testvalue: " + request.getParameter("questionText") + "\n\n");
			
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
				for(CategoryModel item : list) {
					if(category.getTitle().equals(item.getTitle())) {
						category.setDescription(item.getDescription());
					}
				}
				question.setCategory(category);
				
				rightAnswer.setText(request.getParameter("rightAnswerText"));
				rightAnswer.setCorrect(true);
				rightAnswer.setDescription(request.getParameter("rightAnswerDescription"));
				wrongAnswerOne.setText(request.getParameter("wrongAnswerOneText"));
				wrongAnswerOne.setCorrect(false);
				wrongAnswerOne.setDescription(request.getParameter("wrongAnswerOneDescription"));
				wrongAnswerTwo.setText(request.getParameter("wrongAnswerTwoText"));
				wrongAnswerTwo.setCorrect(false);
				wrongAnswerTwo.setDescription(request.getParameter("wrongAnswerTwoDescription"));
				wrongAnswerThree.setText(request.getParameter("wrongAnswerThreeText"));
				wrongAnswerThree.setCorrect(false);
				wrongAnswerThree.setDescription(request.getParameter("wrongAnswerThreeDescription"));	
				
				question.setAnswer(rightAnswer);
				question.setAnswer(wrongAnswerOne);
				question.setAnswer(wrongAnswerTwo);
				question.setAnswer(wrongAnswerThree);
				try {
					question.setHash(hashQuestion(question));
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				}
				filled = true;
			}
			
			if(filled == true)
			{
				response.getOutputStream().println(objectToJson(question));
			}
			else {
				sb.append("Question: " + request.getParameter("questionText") + "\n");
				sb.append("Question explanation: " + request.getParameter("explanationTextfield")+ "\n");
				sb.append("Selection: " + request.getParameter("selection")+ "\n");
				sb.append("Right answer: " + request.getParameter("rightAnswerText")+ "\n");
				sb.append("Wrong answer: " + request.getParameter("wrongAnswerOneText")+ "\n");
				sb.append("Wrong answer: " + request.getParameter("wrongAnswerTwoText")+ "\n");
				sb.append("Wrong answer: " + request.getParameter("wrongAnswerThreeText")+ "\n");
				System.out.println(sb.toString() + "\n" + "There is something missing!");				
			}			
		}
		else
		{
			System.out.println("SubmitButton not found.");
		}
		
	}	
	
	private String objectToJson(QuestionModel question) {
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(question);
			return json;
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "Error";
	}
	
	private String hashQuestion(QuestionModel question) throws NoSuchAlgorithmException {
		String QuestionString = question.getText() + question.getExplanation() + question.getCategory().getTitle() + question.getCategory().getDescription();
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hashbyte = digest.digest(QuestionString.getBytes(StandardCharsets.UTF_8));
		String shaHex = bytesToHex(hashbyte);
		return shaHex;
	}
	
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}	
	
	private void getCategories() throws IOException {
		CategoryModel.categoryList.clear();
		
		URL jsonpage = new URL("http://51.137.215.185:9000/api/categories");
	    URLConnection urlcon = jsonpage.openConnection();	    
	    
	    StringBuffer jb = new StringBuffer();
	    String line = null;
	    try {
	    	BufferedReader buffread = new BufferedReader(new InputStreamReader(urlcon.getInputStream()));
	      while ((line = buffread.readLine()) != null)
	        jb.append(line);
	    } catch (Exception e) { /*report an error*/ }
	    
	    JSONArray jsonArray;
	    
	    try {
	      jsonArray = new JSONArray(jb.toString());
	    } catch (JSONException e) {
	      throw new IOException("Error parsing JSON request string");
	    }

	    for(int i=0;i<jsonArray.length();i++) {
	    	JSONObject item = jsonArray.getJSONObject(i);
	    	CategoryModel category = new CategoryModel();
	    	category.setDescription(item.optString("description"));
	    	category.setTitle(item.getString("title"));
	    	if(CategoryModel.addCategoryToList(category) != true) {
	    		System.out.println("Category wurde nicht hinzugefügt.");
	    	}	    	
	    }
	    //System.out.println(CategoryModel.ToStringCategoryList());
	}
}
