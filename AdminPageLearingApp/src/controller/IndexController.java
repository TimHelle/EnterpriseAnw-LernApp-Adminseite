package controller;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.AnswerModel;
import Model.CategoryModel;
import Model.QuestionModel;
import Model.SendCategoryModel;
import Model.SendQuestionModel;

import controller.ServiceHelper;

@WebServlet("/AdminPage")
public class IndexController extends HttpServlet {
	private static final long serialVersionUID = 1L;	
       
    public IndexController() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//Load all Categories from Backend
		ServiceHelper.getCategories();
		request.setAttribute("categories", CategoryModel.getCategoryList());
		//Load all Questions from Backend
		ServiceHelper.getQuestions();
		request.setAttribute("questions", QuestionModel.getQuestionList());
		//Load the AdminPage.jsp		
		try {
			RequestDispatcher reqDis = request.getRequestDispatcher("AdminPage.jsp");
			response.setHeader("Access-Control-Allow-Origin", "*"); 
			reqDis.forward(request, response);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		URL backendURL = new URL("http://51.137.215.185:9000/api/questions");
		HttpURLConnection connection = (HttpURLConnection) backendURL.openConnection();
		if(request.getParameter("sendJson") != null)
		{
			SendQuestionModel question = new SendQuestionModel();
			CategoryModel category = new CategoryModel();
			AnswerModel rightAnswer = new AnswerModel();
			AnswerModel wrongAnswerOne = new AnswerModel();
			AnswerModel wrongAnswerTwo = new AnswerModel();
			AnswerModel wrongAnswerThree = new AnswerModel();
			SendCategoryModel sendCategory = new SendCategoryModel();
			List<SendQuestionModel> questions = new ArrayList<SendQuestionModel>();
			String json = "";
			boolean filled = false;	
			if(request.getParameter("JSONTextfield") != null) {
				json = request.getParameter("JSONTextfield");
				
				questions = changeJsontextToQuestion(json);
				
				//POST
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type", "application/json; utf-8");
				connection.setDoOutput(true);
				for(SendQuestionModel item : questions) {
					try(OutputStream os = connection.getOutputStream()) 
					{
						byte[] input = ServiceHelper.sendQuestionModelToJson(item).getBytes("utf-8");
						os.write(input,0,input.length);
					}
					catch(Exception ex)
					{
						connection.disconnect();
						System.out.println("Send request error: \n" + ex.getMessage());
					}
					StringBuilder responseString = new StringBuilder();
					try(BufferedReader br = new BufferedReader(
					  new InputStreamReader(connection.getInputStream(), "utf-8"))) {				    
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
					response.getOutputStream().println(responseString.toString());	
				}
			}
			else {
				System.out.println("Textfield not found!");
			}						
		}
		else
		{
			System.out.println("sendJson not found.");
		}		
	}
	
	private List<SendQuestionModel> changeJsontextToQuestion(String json) throws IOException {
		AnswerModel rightAnswer = new AnswerModel();
		AnswerModel wrongAnswerOne = new AnswerModel();
		AnswerModel wrongAnswerTwo = new AnswerModel();
		AnswerModel wrongAnswerThree = new AnswerModel();
		SendCategoryModel sendCategory = new SendCategoryModel();
		List<SendQuestionModel> questions = new ArrayList<SendQuestionModel>();
	    StringBuffer jb = new StringBuffer();
	    String line = null;
	    try {
	    	InputStream in = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
	    	BufferedReader buffread = new BufferedReader(new InputStreamReader(in));
	      while ((line = buffread.readLine()) != null)
	        jb.append(line);
	    } catch (Exception e) { /*report an error*/ }
	    
	    JSONArray jsonArray;
	    
	    try {
	      jsonArray = new JSONArray(jb.toString());
	    } catch (JSONException e) {
	      throw new IOException("Error parsing JSON request string :" + e.getMessage());
	    }
	    
	    for(int i=0;i<jsonArray.length();i++) {
	    	JSONObject item = jsonArray.getJSONObject(i);
	    	SendQuestionModel question = new SendQuestionModel();
	    	question.setText(item.optString("text"));
	    	question.setExplanation(item.optString("explanation"));
	    	
	    	//Get CategoryId from API and change to CategoryModel 
	    	CategoryModel category = new CategoryModel();
	    	JSONObject categoryJsonObject = item.getJSONObject("category");	    	
	    	String categoryTitle = categoryJsonObject.optString("title");
	    	for(CategoryModel categoryItem : CategoryModel.categoryList) {
	    		if(categoryItem.getTitle().equals(categoryTitle)) {
	    			category.setTitle(categoryItem.getTitle());
	    			category.setDescription(categoryItem.getDescription());
	    			category.setId(categoryItem.getId());
	    			category.setHash(categoryItem.getHash());
	    			break;
	    		}
	    	}
	    	for(CategoryModel itemCat : CategoryModel.getCategoryList()) {
				if(category.getTitle().equals(itemCat.getTitle())) {
					category.setDescription(itemCat.getDescription());
					category.setId(itemCat.getId());
				}
			}
			sendCategory.setId(category.getId());
			question.setCategory(sendCategory);
	    	//Get AnswerId´s from API and change to AnswerModel	 
	    	JSONArray array = item.getJSONArray("answers");
	    	for(int j =0; j<array.length();j++) {
	    		AnswerModel answer = new AnswerModel();
	    		JSONObject obj = array.getJSONObject(j);
	    		answer.setText(obj.optString("text"));
	    		answer.setIsCorrect(obj.optBoolean("isCorrect"));
	    		question.setAnswer(answer);
	    	}
	    	questions.add(question);
	    }
	    return questions;
	}
}
