package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
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
		
		QuestionModel question = new QuestionModel();
		CategoryModel cat = new CategoryModel();
		question.setId(1);
		question.setText("Test");
		question.setExplanation("Text");
		cat.setTitle("test");
		cat.setDescription("descrip");
		question.setCategory(cat);
		
		try {
			System.out.println(hashQuestion(question));
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
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
		// TODO Auto-generated method stub
		String s = request.getParameter("test");
		response.getOutputStream().println(s);
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
	    	category.setId(item.optInt("id"));
	    	category.setDescription(item.optString("description"));
	    	category.setTitle(item.getString("title"));
	    	if(CategoryModel.addCategoryToList(category) != true) {
	    		System.out.println("Category wurde nicht hinzugefügt.");
	    	}	    	
	    }
	    //System.out.println(CategoryModel.ToStringCategoryList());
	}
}
