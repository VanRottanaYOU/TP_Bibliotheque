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
import javax.servlet.annotation.MultipartConfig;
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
@MultipartConfig
@WebServlet(urlPatterns= {"/livre","/livre/*"})
public class CRULivres extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public CRULivres() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bibliotheque");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		response.setContentType("application/json");
		System.out.println(request.getPathInfo());
	
		try {
			if (!request.getPathInfo().substring(1).equals(null))
			{
				Livre monLivre = entityManager.find(Livre.class, Integer.parseInt(request.getPathInfo().substring(1)));
				JSONObject jObj;
				jObj = new JSONObject(monLivre);
				response.getWriter().append(jObj.toString());
			}
		} catch (RuntimeException e) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND,"Livre inconnu");
			JSONObject jObj;
			jObj = new JSONObject();
			jObj.put("404", "livre inconnu");
			response.getWriter().append(jObj.toString());
		}finally {
		  entityManager.close();
		}  
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bibliotheque");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		response.setContentType("application/json");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date;
		
		try {
			date = formatter.parse(request.getParameter("datePublication"));
			Auteur monAuteur = entityManager.find(Auteur.class, Integer.parseInt(request.getParameter("idAuteur")));
			Livre monLivre = new Livre(request.getParameter("titre"),date,request.getParameter("description"),
					request.getParameter("categorie"),Integer.parseInt(request.getParameter("nbExemplaires")),Integer.parseInt(request.getParameter("nbExemplairesDispo")),
					monAuteur);
			entityManager.getTransaction().begin();
			entityManager.persist(monLivre);
			entityManager.getTransaction().commit();
		} catch (ParseException e) {			
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST,"Echec création du livre");
			JSONObject jObj;
			jObj = new JSONObject();
			jObj.put("400","Echec création du livre");
			response.getWriter().append(jObj.toString());
		}				
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bibliotheque");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		response.setContentType("application/json");
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date;
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String[] brokentext = null;
		String text = "";
		text=br.readLine();
		System.out.println("text :" + text.toString());
		brokentext = text.split("&");
		JSONArray jsonArrayResultat = new JSONArray();
		JSONObject jObj;
		jObj = new JSONObject();
		
		try {
			String[] brokentext2 = null;
			brokentext2 = brokentext[1].split("=");
			Livre monLivre = entityManager.find(Livre.class, Integer.parseInt(brokentext2[1]));
			if (!monLivre.equals(null))
			{
				entityManager.getTransaction().begin();
				brokentext2 = null;
				brokentext2 = brokentext[3].split("=");
				date = formatter.parse(brokentext2[1]);
				monLivre.setDatePublication(date);
				
				
				brokentext2 = null;
				brokentext2 = brokentext[0].split("=");
				Auteur monAuteur = entityManager.find(Auteur.class, Integer.parseInt(brokentext2[1]));
				monLivre.setAuteur(monAuteur);
				
				brokentext2 = null;
				brokentext2 = brokentext[5].split("=");
				monLivre.setCategorie(brokentext2[1]);

				
				brokentext2 = null;
				brokentext2 = brokentext[4].split("=");
				monLivre.setDescription(brokentext2[1]);
				
				brokentext2 = null;
				brokentext2 = brokentext[6].split("=");
				monLivre.setNbExemplaires(Integer.parseInt(brokentext2[1]));
				
				brokentext2 = null;
				brokentext2 = brokentext[7].split("=");
				monLivre.setNbExemplairesDispo(Integer.parseInt(brokentext2[1]));
				
				brokentext2 = null;
				brokentext2 = brokentext[2].split("=");
				System.out.println(brokentext2[1]);
				monLivre.setTitre(brokentext2[1]);
												
				entityManager.persist(monLivre);
				entityManager.getTransaction().commit();
			}
			else {
				response.setStatus(HttpServletResponse.SC_NOT_FOUND,"Livre inconnu");
				jsonArrayResultat.put("Livre inconnu");
				jObj.put("erreur", jsonArrayResultat);
				response.getWriter().append(jObj.toString());
			}
		} catch (ParseException e) {				
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST,"Mise à jour non exécutée");

			jsonArrayResultat.put("Mise à jour non exécutée, problèm de format date");
			jObj.put("erreur", jsonArrayResultat);
			response.getWriter().append(jObj.toString());
		}finally {
			entityManager.close();
		}						
}
}

