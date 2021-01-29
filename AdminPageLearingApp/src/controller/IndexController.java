package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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

import Model.AnswerModel;
import Model.CategoryModel;
import Model.QuestionModel;
import Model.UserModel;

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
		getCategories();
		request.setAttribute("categories", CategoryModel.getCategoryList());
		//Load all Questions from Backend
		getQuestions();
		request.setAttribute("questions", QuestionModel.getQuestionList());
		//Load the AdminPage.jsp		
		try {
			RequestDispatcher reqDis = request.getRequestDispatcher("AdminPage.jsp");
			reqDis.forward(request, response);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
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
	    	category.setId((int) item.opt("id"));
	    	category.setDescription(item.optString("description"));
	    	category.setTitle(item.optString("title"));
	    	category.setHash(item.optString("hash"));
	    	if(CategoryModel.addCategoryToList(category) != true && category != null) {
	    		System.out.println("Category wurde nicht hinzugefügt.");
	    	}	    	
	    }
	    //System.out.println(CategoryModel.ToStringCategoryList());
	}
	
	private void getUsers() throws IOException {
		UserModel.userList.clear();
		
		URL jsonpage = new URL("");
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

	    //TODO add in for the usermodel to list
	    for(int i=0;i<jsonArray.length();i++) {
	    	JSONObject item = jsonArray.getJSONObject(i);
	    	UserModel user = new UserModel();
	    	user.setUsername(item.optString("username"));
	    	if(UserModel.addUserToList(user) != true) {
	    		System.out.println("Category wurde nicht hinzugefügt.");
	    	}	
	    }
	    
	  //System.out.println(UserModel.ToStringUserList());
	}
	
	private void getQuestions() throws IOException{
		QuestionModel.questionList.clear();
		
		URL jsonpage = new URL("http://51.137.215.185:9000/api/questions");
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
	    	QuestionModel question = new QuestionModel();
	    	question.setId((int) item.opt("id"));
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
	    	question.setCategory(category);
	    	//Get AnswerId´s from API and change to AnswerModel	 
	    	JSONArray array = item.getJSONArray("answers");
	    	for(int j =0; j<array.length();j++) {
	    		AnswerModel answer = new AnswerModel();
	    		JSONObject obj = array.getJSONObject(j);
	    		answer.setText(obj.optString("text"));
	    		answer.setIsCorrect(obj.optBoolean("isCorrect"));
	    		question.setAnswer(answer);
	    	}
	    	if(QuestionModel.addQuestionToList(question) != true && question != null) {
	    		System.out.println("Category wurde nicht hinzugefügt.");
	    	}	   
	    }
	    
	    //TODO Hash mit speichern	    
	    //System.out.println(QuestionModel.ToStringQuestionList());
	}
}
