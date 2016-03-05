package net.atos.suivi_production.beans;

public class Profil implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String libelle;

	public Profil() {
		super();
	}

	public Profil(Integer id, String libelle) {
		super();
		this.id = id;
		this.libelle = libelle;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

}
