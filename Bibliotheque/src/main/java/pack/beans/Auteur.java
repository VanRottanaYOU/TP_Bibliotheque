package pack.beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "AUTEUR")

public class Auteur {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idAuteur;
	
	@Column(name="NOM")
	private String nom;
	
	@Column(name="PRENOM")
	private String prenom;
	
	@Column(name="LANGUE")
	private String langue;
	
	@OneToMany(mappedBy="auteur")
	private transient Set<Livre> maListeLivres =new HashSet<Livre>();
	
	
	public Auteur() {
		super();
	}

	public Auteur(String nom, String prenom, String langue) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.langue = langue;		
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getLangue() {
		return langue;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}

	public Set<Livre> getMaListeLivres() {
		return maListeLivres;
	}

	public void setMaListeLivres(Set<Livre> maListeLivres) {
		this.maListeLivres = maListeLivres;
	}

	@Override
	public String toString() {
		return "Auteur [nom=" + nom + ", prenom=" + prenom + ", langue=" + langue 
				+ "]";
	}
		
}
