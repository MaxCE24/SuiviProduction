package net.atos.suivi_production.beans;

import java.util.Date;
import java.util.SortedMap;

public class DemandeDAchat implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer numero;
	private Date date;
	private String description;
	private String statut;
	private Integer numeroDeBonDeCommande;
	private Collaborateur collaborateur;
	private SortedMap<Integer, Validation> validations;

	public DemandeDAchat() {
		super();
	}

	public DemandeDAchat(Integer id, Integer numero, Date date, String description, String statut,
			Integer numeroDeBonDeCommande, Collaborateur collaborateur, SortedMap<Integer, Validation> validations) {
		super();
		this.id = id;
		this.numero = numero;
		this.date = date;
		this.description = description;
		this.statut = statut;
		this.numeroDeBonDeCommande = numeroDeBonDeCommande;
		this.collaborateur = collaborateur;
		this.validations = validations;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date2) {
		this.date = date2;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public Integer getNumeroDeBonDeCommande() {
		return numeroDeBonDeCommande;
	}

	public void setNumeroDeBonDeCommande(Integer numeroDeBonDeCommande) {
		this.numeroDeBonDeCommande = numeroDeBonDeCommande;
	}

	public Collaborateur getCollaborateur() {
		return collaborateur;
	}

	public void setCollaborateur(Collaborateur collaborateur) {
		this.collaborateur = collaborateur;
	}

	public SortedMap<Integer, Validation> getValidations() {
		return validations;
	}

	public void setValidations(SortedMap<Integer, Validation> validations) {
		this.validations = validations;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
