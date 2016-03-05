package net.atos.suivi_production.beans;

import java.io.File;
import java.util.Date;

public class SousTraitant implements java.io.Serializable {

	public SousTraitant(Integer id, String nom, String prenom, String sexe, File cV, String numeroDeTelephone,
			Date dateRecrutement, Profil profil, Equipe equipe, Societe societe) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.sexe = sexe;
		this.cV = cV;
		this.numeroDeTelephone = numeroDeTelephone;
		this.dateRecrutement = dateRecrutement;
		this.profil = profil;
		this.equipe = equipe;
		this.societe = societe;
	}

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nom;
	private String prenom;
	private String sexe;
	private File cV;
	private String numeroDeTelephone;
	private Date dateRecrutement;
	private Profil profil;
	private Equipe equipe;
	private Societe societe;

	public SousTraitant() {
		super();
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

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public String getNumeroDeTelephone() {
		return numeroDeTelephone;
	}

	public void setNumeroDeTelephone(String numeroDeTelephone) {
		this.numeroDeTelephone = numeroDeTelephone;
	}

	public Date getDateRecrutement() {
		return dateRecrutement;
	}

	public void setDateRecrutement(Date dateRecrutement) {
		this.dateRecrutement = dateRecrutement;
	}

	public Profil getProfil() {
		return profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}

	public Equipe getEquipe() {
		return equipe;
	}

	public void setEquipe(Equipe equipe) {
		this.equipe = equipe;
	}

	public Societe getSociete() {
		return societe;
	}

	public void setSociete(Societe societe) {
		this.societe = societe;
	}

	public File getCV() {
		return cV;
	}

	public void setCV(File cV) {
		this.cV = cV;
	}

}
