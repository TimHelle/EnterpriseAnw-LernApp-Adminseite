package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import Model.AnswerModel;
import Model.CategoryModel;
import Model.QuestionModel;
import Model.SendQuestionModel;

public class ServiceHelper {
	public static void getCategories() throws IOException {
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
	    	category.setId(item.getInt("id"));
	    	category.setHash(item.optString("hash"));
	    	if(CategoryModel.addCategoryToList(category) != true) {
	    		System.out.println("Category wurde nicht hinzugefügt.");
	    	}	    	
	    }
	    //System.out.println(CategoryModel.ToStringCategoryList());
	}
	public static void getQuestions() throws IOException{
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
	//Obj to Json converter
	public static String categoryToJson(CategoryModel category) {
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
	public static String sendQuestionModelToJson(SendQuestionModel question) {
		String json = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(question);	
			System.out.println(json);
			return json;
		}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "Error";
	}
	
	//Hashmethod
	public static String hashCategory(CategoryModel category) throws NoSuchAlgorithmException {
		String QuestionString = category.getTitle() + category.getDescription();
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hashbyte = digest.digest(QuestionString.getBytes(StandardCharsets.UTF_8));
		String shaHex = bytesToHex(hashbyte);
		return shaHex;
	}
	public static String hashQuestion(SendQuestionModel question) throws NoSuchAlgorithmException {
		String QuestionString = question.getText() + question.getExplanation();
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
