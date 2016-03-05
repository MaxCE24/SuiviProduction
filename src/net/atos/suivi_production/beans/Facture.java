package net.atos.suivi_production.beans;

import java.util.Date;

public class Facture implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer numero;
	private Date dateReception;
	private Double montantTTC;
	private Date dateRemiseAuxOP;
	private Date dateRetourOP;
	private Date dateRemiseAuTMU;
	private Date dateEnvoiDAF;
	private String periodeFacturee;
	private String notes;
	private SousTraitant sousTraitant;

	public Facture() {
		super();
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public Date getDateReception() {
		return dateReception;
	}

	public void setDateReception(Date dateReception) {
		this.dateReception = dateReception;
	}

	public Double getMontantTTC() {
		return montantTTC;
	}

	public void setMontantTTC(Double montantTTC) {
		this.montantTTC = montantTTC;
	}

	public Date getDateRemiseAuxOP() {
		return dateRemiseAuxOP;
	}

	public void setDateRemiseAuxOP(Date dateRemiseAuxOP) {
		this.dateRemiseAuxOP = dateRemiseAuxOP;
	}

	public Date getDateRetourOP() {
		return dateRetourOP;
	}

	public void setDateRetourOP(Date dateRetourOP) {
		this.dateRetourOP = dateRetourOP;
	}

	public Date getDateRemiseAuTMU() {
		return dateRemiseAuTMU;
	}

	public void setDateRemiseAuTMU(Date dateRemiseAuTMU) {
		this.dateRemiseAuTMU = dateRemiseAuTMU;
	}

	public Date getDateEnvoiDAF() {
		return dateEnvoiDAF;
	}

	public void setDateEnvoiDAF(Date dateEnvoiDAF) {
		this.dateEnvoiDAF = dateEnvoiDAF;
	}

	public Facture(Integer id, Integer numero, Date dateReception, Double montantTTC, Date dateRemiseAuxOP,
			Date dateRetourOP, Date dateRemiseAuTMU, Date dateEnvoiDAF, String periodeFacturee, String notes,
			SousTraitant sousTraitant) {
		super();
		this.id = id;
		this.numero = numero;
		this.dateReception = dateReception;
		this.montantTTC = montantTTC;
		this.dateRemiseAuxOP = dateRemiseAuxOP;
		this.dateRetourOP = dateRetourOP;
		this.dateRemiseAuTMU = dateRemiseAuTMU;
		this.dateEnvoiDAF = dateEnvoiDAF;
		this.periodeFacturee = periodeFacturee;
		this.notes = notes;
		this.sousTraitant = sousTraitant;
	}

	public String getPeriodeFacturee() {
		return periodeFacturee;
	}

	public void setPeriodeFacturee(String periodeFacturee) {
		this.periodeFacturee = periodeFacturee;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public SousTraitant getSousTraitant() {
		return sousTraitant;
	}

	public void setSousTraitant(SousTraitant sousTraitant) {
		this.sousTraitant = sousTraitant;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
