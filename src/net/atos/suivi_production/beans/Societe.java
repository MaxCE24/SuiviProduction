package net.atos.suivi_production.beans;

public class Societe implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String raisonSociale;

	public Societe() {
		super();
	}

	public Societe(Integer id, String raisonSociale) {
		super();
		this.id = id;
		this.raisonSociale = raisonSociale;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRaisonSociale() {
		return raisonSociale;
	}

	public void setRaisonSociale(String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

}
