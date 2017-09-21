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
@WebServlet(urlPatterns= {"/auteurs"})
public class ListerAuteurs extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public ListerAuteurs() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bibliotheque");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		TypedQuery<Auteur> query = entityManager.createQuery("from Auteur", Auteur.class);
		JSONArray jsonArrayResultat = new JSONArray();
		JSONObject jObj; 
		jObj = new JSONObject();
		  for (int i = 0 ; i<query.getResultList().size();i++) {
			  Auteur monAuteur = (Auteur) query.getResultList().get(i);
			  jsonArrayResultat.put(monAuteur);			  			  		  
		  } 
		  jObj.put("listeAuteurs", jsonArrayResultat);
		  response.getWriter().append(jObj.toString());
		  entityManager.close();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
		
	}

}
