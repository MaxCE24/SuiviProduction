package net.atos.suivi_production.beans;

public class Collaborateur implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nom;
	private String prenom;

	public Collaborateur() {
		super();
	}

	public Collaborateur(Integer id, String nom, String prenom) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

}
