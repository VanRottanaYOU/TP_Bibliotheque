package pack.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.service.spi.ServiceException;
import org.json.JSONArray;
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

    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bibliotheque");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		response.setContentType("application/json");
		
		try {
			if (!request.getPathInfo().substring(1).equals(null))
			{
				Auteur monAuteur = entityManager.find(Auteur.class, Integer.parseInt(request.getPathInfo().substring(1)));
				JSONObject jObj;
				jObj = new JSONObject(monAuteur);
				response.getWriter().append(jObj.toString());
			}
		} catch (RuntimeException e) {	
			
			response.setStatus(HttpServletResponse.SC_NOT_FOUND,"Auteur inconnu");
			JSONObject jObj;
			jObj = new JSONObject();
			jObj.put("404", "auteur inconnu");
			response.getWriter().append(jObj.toString());

		}finally {
			entityManager.close();
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bibliotheque");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		response.setContentType("application/json");
		Pattern p = Pattern.compile("([a-zA-Z])");
		boolean b= true;
		
		String res=request.getParameter("nom");
		Matcher m = p.matcher(res);	

		if (!m.find()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST,"Erreur format du nom");
			JSONObject jObj;
			jObj = new JSONObject();
			jObj.put("400","Erreur format du nom");
			response.getWriter().append(jObj.toString());
			b=false;
			return;
		}else{
		
			res=request.getParameter("prenom");
			m = p.matcher(res);	
	
			if (!m.find()) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST,"Erreur format du prenom");
				JSONObject jObj;
				jObj = new JSONObject();
				jObj.put("400","Erreur format du prenom");
				response.getWriter().append(jObj.toString());
				b=false;
				return;
			}else {
		
				res=request.getParameter("langue");
				m = p.matcher(res);	
		
				if (!m.find()) {
					response.setStatus(HttpServletResponse.SC_BAD_REQUEST,"Erreur format de la langue");
					JSONObject jObj;
					jObj = new JSONObject();
					jObj.put("400","Erreur format de la langue");
					response.getWriter().append(jObj.toString());
					b=false;
					return;
				}
			}	
		}
		try {
			if(b) {
				Auteur monAuteur = new Auteur(request.getParameter("nom"),request.getParameter("prenom"),request.getParameter("langue"));
				entityManager.getTransaction().begin();
				entityManager.persist(monAuteur);
				entityManager.getTransaction().commit();
			}	
			else {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST,"Echec création de l'auteur");
				JSONObject jObj;
				jObj = new JSONObject();
				jObj.put("400","Echec création de l'auteur");
				response.getWriter().append(jObj.toString());
				return;
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Echec serveur");
			JSONObject jObj;
			jObj = new JSONObject();
			jObj.put("500","Echec serveur");
			response.getWriter().append(jObj.toString());
		}finally {
			entityManager.close();
		}
	}
	
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("bibliotheque");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		response.setContentType("application/json");
		
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
				response.setStatus(HttpServletResponse.SC_NOT_FOUND,"Auteur inconnu");
				jsonArrayResultat.put("Auteur inconnu");
				jObj.put("erreur", jsonArrayResultat);
				response.getWriter().append(jObj.toString());
			}
		} catch (RuntimeException e) {				
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST,"Mise à jour non exécutée");
			jsonArrayResultat.put("Mise à jour non exécutée");
			jObj.put("erreur", jsonArrayResultat);
			response.getWriter().append(jObj.toString());
		}finally {
			entityManager.close();
		}			

}
}