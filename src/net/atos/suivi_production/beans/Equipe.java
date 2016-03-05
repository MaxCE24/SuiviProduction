package net.atos.suivi_production.beans;

public class Equipe implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nom;

	public Equipe() {
		super();
	}

	public Equipe(Integer id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
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

}
