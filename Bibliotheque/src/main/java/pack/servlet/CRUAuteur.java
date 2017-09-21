package pack.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import pack.beans.Auteur;
import pack.beans.Livre;

/**
 * Servlet implementation class ListerLivres
 */
@WebServlet(urlPatterns= {"/auteur","/auteur/*"})
public class CRUAuteur extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public CRUAuteur() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bibliotheque");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
//		response.setContentType("application/json");
		System.out.println(request.getPathInfo());
	
		if (!request.getPathInfo().substring(1).equals(null))
		{
			Auteur monAuteur = entityManager.find(Auteur.class, Integer.parseInt(request.getPathInfo().substring(1)));
			JSONObject jObj;
			jObj = new JSONObject(monAuteur);
			System.out.println(jObj.getClass());
			response.getWriter().append(jObj.toString());
		}
		  entityManager.close();
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bibliotheque");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		System.out.println(request.getParameter("idAuteur"));
		System.out.println(request.getParameter("nom"));
		System.out.println(request.getParameter("prenom"));
		System.out.println(request.getParameter("langue"));
		Auteur monAuteur = new Auteur(request.getParameter("nom"),request.getParameter("prenom"),request.getParameter("langue"));
		entityManager.getTransaction().begin();
		entityManager.persist(monAuteur);
		entityManager.getTransaction().commit();
	}
	
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bibliotheque");
		EntityManager entityManager = entityManagerFactory.createEntityManager();

		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String[] brokentext = null;
		String text = "";
		text=br.readLine();
		System.out.println("text :" + text.toString());
		brokentext = text.split("&");
		try {
			String[] brokentext2 = null;
			brokentext2 = brokentext[0].split("=");
			Auteur monAuteur = entityManager.find(Auteur.class, Integer.parseInt(brokentext2[1]));
			if (!monAuteur.equals(null))
			{
				
				entityManager.getTransaction().begin();
				brokentext2 = null;
				brokentext2 = brokentext[1].split("=");
				monAuteur.setLangue(brokentext2[1]);
				
				
				brokentext2 = null;
				brokentext2 = brokentext[2].split("=");
				monAuteur.setNom(brokentext2[1]);
				
				brokentext2 = null;
				brokentext2 = brokentext[3].split("=");
				monAuteur.setPrenom(brokentext2[1]);
												
				entityManager.persist(monAuteur);
				entityManager.getTransaction().commit();
			}
			else {
				System.out.println("pas d'auteur");
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				

}
}