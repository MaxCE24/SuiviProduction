package net.atos.suivi_production.beans;

import java.util.Date;

public class Astreinte implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String reference;
	private Date date;
	private Double nombreHeures;
	private String type;
	private Collaborateur collaborateur;

	public Astreinte() {
		super();
	}

	public Astreinte(Integer id, String reference, Date date, Double nombreHeures, String type,
			Collaborateur collaborateur) {
		super();
		this.id = id;
		this.reference = reference;
		this.date = date;
		this.nombreHeures = nombreHeures;
		this.type = type;
		this.collaborateur = collaborateur;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getNombreHeures() {
		return nombreHeures;
	}

	public void setNombreHeures(Double nombreHeures) {
		this.nombreHeures = nombreHeures;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Collaborateur getCollaborateur() {
		return collaborateur;
	}

	public void setCollaborateur(Collaborateur collaborateur) {
		this.collaborateur = collaborateur;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
