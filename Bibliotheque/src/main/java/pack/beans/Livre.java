package pack.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LIVRE")
public class Livre {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idLivre;
	
	@Column(name="TITRE")
	private String titre;
	
	@Column(name="DATEPUBLICATION")
	private Date datePublication;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="CATEGORIE")
	private String categorie;
	
	@ManyToOne
	private Auteur auteur;
	
	@Column(name="NBEXEMPLAIRES")
	private int nbExemplaires;
	
	@Column(name="NBEXEMPLAIRESDISPO")
	private int nbExemplairesDispo;
	
	
	public Livre() {
		super();
	}


	public Livre(String titre, Date datePublication, String description, String categorie,
			int nbExemplaires, int nbExemplairesDispo,Auteur auteur) {
		super();
		this.titre = titre;
		this.datePublication = datePublication;
		this.description = description;
		this.categorie = categorie;
		this.auteur = auteur;
		this.nbExemplaires = nbExemplaires;
		this.nbExemplairesDispo = nbExemplairesDispo;
	}


	public String getTitre() {
		return titre;
	}


	public void setTitre(String titre) {
		this.titre = titre;
	}


	public Date getDatePublication() {
		return datePublication;
	}


	public void setDatePublication(Date datePublication) {
		this.datePublication = datePublication;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getCategorie() {
		return categorie;
	}


	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}


	public Auteur getAuteur() {
		return auteur;
	}


	public void setAuteur(Auteur auteur) {
		this.auteur = auteur;
	}


	public int getNbExemplaires() {
		return nbExemplaires;
	}


	public void setNbExemplaires(int nbExemplaires) {
		this.nbExemplaires = nbExemplaires;
	}


	public int getNbExemplairesDispo() {
		return nbExemplairesDispo;
	}


	public void setNbExemplairesDispo(int nbExemplairesDispo) {
		this.nbExemplairesDispo = nbExemplairesDispo;
	}


	@Override
	public String toString() {
		return "Livre [titre=" + titre + ", datePublication=" + datePublication + ", description=" + description
				+ ", categorie=" + categorie + ", auteur=" + auteur + ", nbExemplaires=" + nbExemplaires
				+ ", nbExemplairesDispo=" + nbExemplairesDispo + "]";
	}
	
	
	
}
