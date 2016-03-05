package net.atos.suivi_production.config;

public class Utilisateur {

	private String eMail;
	private String motDePasse;

	public Utilisateur(String eMail, String motDePasse) {
		super();
		this.eMail = eMail;
		this.motDePasse = motDePasse;
	}

	public Utilisateur() {
		super();
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

}
