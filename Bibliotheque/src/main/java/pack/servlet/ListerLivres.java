package pack.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import pack.beans.Auteur;
import pack.beans.Livre;

/**
 * Servlet implementation class ListerLivres
 */
@WebServlet(urlPatterns= {"/livres"})
public class ListerLivres extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public ListerLivres() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bibliotheque");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
//		response.setContentType("application/json");
		
			TypedQuery<Livre> query = entityManager.createQuery("from Livre", Livre.class);
			  System.out.println(query.getResultList().toString());
			  
			  JSONArray jsonArrayResultat = new JSONArray();
			  JSONObject jObj; 
			  jObj = new JSONObject();
			  for (int i = 0 ; i<query.getResultList().size();i++) {
				  Livre monLivre = (Livre) query.getResultList().get(i);
					  jsonArrayResultat.put(monLivre);			  			  		  
				  } 
			  jObj.put("listeLivres", jsonArrayResultat);
			  response.getWriter().append(jObj.toString());
			  entityManager.close();
		  entityManager.close();
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
