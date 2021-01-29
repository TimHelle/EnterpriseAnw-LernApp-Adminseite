package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.CategoryModel;
import Model.QuestionModel;

/**
 * Servlet implementation class CateogoryController
 */
@WebServlet("/CategoryController")
public class CategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		getCategories();
		request.setAttribute("alreadyAvailableCategories", CategoryModel.getCategoryList());
		
		try {
			RequestDispatcher reqDis = request.getRequestDispatcher("AddCategoryPage.jsp");
			reqDis.forward(request, response);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		URL backendURL = new URL("http://51.137.215.185:9000/api/categories");
		HttpURLConnection connection = (HttpURLConnection) backendURL.openConnection();
		if(request.getParameter("submitButton") != null)
		{
			CategoryModel category = new CategoryModel();
			List<CategoryModel> list = new ArrayList<CategoryModel>();
			list = CategoryModel.getCategoryList();
			StringBuilder sb = new StringBuilder();
			boolean filled = false;		
			if (request.getParameter("categoryTitle") != null && 
				request.getParameter("descriptionCategoryTextfield") != null) 
			{
				category.setTitle(request.getParameter("categoryTitle"));
				category.setDescription(request.getParameter("descriptionCategoryTextfield"));
				try {
					category.setHash(hashCategory(category));
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
					byte[] input = objectToJson(category).getBytes("utf-8");
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
				//TODO load indexcontroller
			}
			else
			{
				sb.append("Title: " + request.getParameter("categoryTitle") + "\n");
				sb.append("Description: " + request.getParameter("descriptionCategoryTextfield") + "\n");
				System.out.println(sb.toString() + "\n" + "There is something missing in category!");
				connection.disconnect();
			}
		}
		else
		{
			System.out.println("SubmitButton not found.");
			connection.disconnect();
		}
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
	private String objectToJson(CategoryModel category) {
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(category);
			return json;
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "Error";
	}
	
	private String hashCategory(CategoryModel category) throws NoSuchAlgorithmException {
		String QuestionString = category.getTitle() + category.getDescription();
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
}
