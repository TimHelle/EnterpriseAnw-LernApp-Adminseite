package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

/**
 * Servlet implementation class DeleteQuestionController
 */
@WebServlet("/DeleteQuestionController")
public class DeleteQuestionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteQuestionController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		URL url = new URL("http://51.137.215.185:9000/api/questions/" + id);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded" );
		httpCon.setRequestMethod("DELETE");
		httpCon.connect();	
		StringBuilder responseString = new StringBuilder();
		try(BufferedReader br = new BufferedReader(
		  new InputStreamReader(httpCon.getInputStream(), "utf-8"))) {				    
		    String responseLine = null;
		    while ((responseLine = br.readLine()) != null) {
		        responseString.append(responseLine.trim());
		    }
		    System.out.println("Response Stream: " + response.toString());
		}
		catch(Exception ex)
		{
			httpCon.disconnect();
			System.out.println("Get response error: \n" + ex.getMessage());
		}
		response.sendRedirect("/AdminPageLearingApp/AdminPage");
		httpCon.disconnect();		
	}
}
