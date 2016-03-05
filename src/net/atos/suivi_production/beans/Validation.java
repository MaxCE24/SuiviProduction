package net.atos.suivi_production.beans;

import java.io.Serializable;
import java.util.Date;

public class Validation implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Collaborateur valideur;
	private Date date;
	private DemandeDAchat demandeDAchat;

	public Validation(Integer id, Date date, Collaborateur valideur, DemandeDAchat demandeDAchat) {
		super();
		this.id = id;
		this.date = date;
		this.valideur = valideur;
		this.demandeDAchat = demandeDAchat;
	}

	public Validation() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DemandeDAchat getDemandeDAchat() {
		return demandeDAchat;
	}

	public void setDemandeDAchat(DemandeDAchat demandeDAchat) {
		this.demandeDAchat = demandeDAchat;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Collaborateur getValideur() {
		return valideur;
	}

	public void setValideur(Collaborateur valideur) {
		this.valideur = valideur;
	}

}
