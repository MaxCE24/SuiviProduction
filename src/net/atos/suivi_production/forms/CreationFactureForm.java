package net.atos.suivi_production.forms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.atos.suivi_production.beans.Facture;
import net.atos.suivi_production.beans.SousTraitant;

public class CreationFactureForm {

	public static final String CHAMP_NUMERO = "numeroFacture";
	public static final String CHAMP_DATE_RECEPTION = "dateReceptionFacture";
	public static final String CHAMP_MONTANT_TTC = "montantTTCFacture";
	public static final String CHAMP_DATE_REMISE_AUX_OP = "dateRemiseAuxOPFacture";
	public static final String CHAMP_DATE_RETOUR_OP = "dateRetourOPFacture";
	public static final String CHAMP_DATE_REMISE_AU_TMU = "dateRemiseAuTMUFacture";
	public static final String CHAMP_DATE_ENVOI_DAF = "dateEnvoiDAFFacture";
	public static final String CHAMP_PERIODE_FACTUREE = "periodeFactureeFacture";
	public static final String CHAMP_NOTES = "notesFacture";

	private static final String CHAMP_SOUS_TRAITANT = "sousTraitantFacture";

	private static final String FORMAT_DATE = "yyyy-MM-dd";
	private static final String ATT_SOUS_TRAITANTS = "sousTraitants";

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public String getResultat() {
		return resultat;
	}

	@SuppressWarnings("unchecked")
	public Facture creerFacture(HttpServletRequest request) {
		Facture facture = new Facture();

		String numero = getValeurChamp(request, CHAMP_NUMERO);
		String dateReception = getValeurChamp(request, CHAMP_DATE_RECEPTION);
		String montantTTC = getValeurChamp(request, CHAMP_MONTANT_TTC);
		String dateRemiseAuxOP = getValeurChamp(request, CHAMP_DATE_REMISE_AUX_OP);
		String dateRetourOP = getValeurChamp(request, CHAMP_DATE_RETOUR_OP);
		String dateRemiseAuTMU = getValeurChamp(request, CHAMP_DATE_REMISE_AU_TMU);
		String dateEnvoiDAF = getValeurChamp(request, CHAMP_DATE_ENVOI_DAF);
		String periodeFacturee = getValeurChamp(request, CHAMP_PERIODE_FACTUREE);
		String notes = getValeurChamp(request, CHAMP_NOTES);
		String sousTraitant = getValeurChamp(request, CHAMP_SOUS_TRAITANT);

		int valeurNumero = -1;
		try {
			valeurNumero = validationNumero(numero);
		} catch (Exception e) {
			setErreur(CHAMP_NUMERO, e.getMessage());
		}
		facture.setNumero(valeurNumero);

		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
		Date parsedDate = new Date();
		try {
			validationDate(dateReception);
			parsedDate = format.parse(dateReception);

		} catch (Exception e) {
			setErreur(CHAMP_DATE_RECEPTION, e.getMessage());
		}
		facture.setDateReception(parsedDate);

		double valeurMontantTTC = -1;
		try {
			valeurMontantTTC = validationMontantTTC(montantTTC);
		} catch (Exception e) {
			setErreur(CHAMP_MONTANT_TTC, e.getMessage());
		}
		facture.setMontantTTC(valeurMontantTTC);

		try {
			validationDate(dateRemiseAuxOP);
			parsedDate = format.parse(dateRemiseAuxOP);

		} catch (Exception e) {
			setErreur(CHAMP_DATE_REMISE_AUX_OP, e.getMessage());
		}
		facture.setDateRemiseAuxOP(parsedDate);

		try {
			validationDate(dateRetourOP);
			parsedDate = format.parse(dateRetourOP);

		} catch (Exception e) {
			setErreur(CHAMP_DATE_RETOUR_OP, e.getMessage());
		}
		facture.setDateRetourOP(parsedDate);

		try {
			validationDate(dateRemiseAuTMU);
			parsedDate = format.parse(dateRemiseAuTMU);

		} catch (Exception e) {
			setErreur(CHAMP_DATE_REMISE_AU_TMU, e.getMessage());
		}
		facture.setDateRemiseAuTMU(parsedDate);

		try {
			validationDate(dateEnvoiDAF);
			parsedDate = format.parse(dateEnvoiDAF);

		} catch (Exception e) {
			setErreur(CHAMP_DATE_ENVOI_DAF, e.getMessage());
		}
		facture.setDateEnvoiDAF(parsedDate);

		try {
			validationPeriodeFacturee(periodeFacturee);
		} catch (Exception e) {
			setErreur(CHAMP_PERIODE_FACTUREE, e.getMessage());
		}
		facture.setPeriodeFacturee(periodeFacturee);

		try {
			validationNotes(notes);
		} catch (Exception e) {
			setErreur(CHAMP_NOTES, e.getMessage());
		}
		facture.setNotes(notes);

		SousTraitant leSousTraitant = null;
		try {
			validationSousTraitant(sousTraitant);
			Integer idSousTraitant = Integer.parseInt(sousTraitant);
			HttpSession session = request.getSession();
			leSousTraitant = (SousTraitant) ((Map<Integer, SousTraitant>) session.getAttribute(ATT_SOUS_TRAITANTS))
					.get(idSousTraitant);
		} catch (Exception e) {
			setErreur(CHAMP_SOUS_TRAITANT, e.getMessage());
		}

		facture.setSousTraitant(leSousTraitant);

		if (erreurs.isEmpty()) {
			resultat = "Succès de la création de la facture.";
		} else {
			resultat = "Échec de la création de la facture.";
		}

		return facture;
	}

	private int validationNumero(String numero) throws Exception {
		int temp = -1;
		if (numero == null) {
			throw new Exception("Merci d'entrer un numéro.");
		} else {
			temp = Integer.parseInt(numero);
		}
		return temp;
	}

	private void validationDate(String date) throws Exception {
		if (date == null || date.trim().equals("")) {
			{
				throw new Exception("Merci d'entrer une date.");
			}
		}
	}

	private double validationMontantTTC(String montantTTC) throws Exception {
		int temp = -1;
		if (montantTTC == null) {
			throw new Exception("Merci d'entrer le montant.");
		} else {
			temp = Integer.parseInt(montantTTC);
		}
		return temp;
	}

	private void validationPeriodeFacturee(String periodeFacturee) throws Exception {
		if (periodeFacturee != null) {
			if (periodeFacturee.length() < 2) {
				throw new Exception("La periode facturée de la facture doit contenir au moins 2 caractères.");
			}
		} else {
			throw new Exception("Merci d'entrer une periode facturée de la facture.");
		}
	}

	private void validationNotes(String notes) throws Exception {
		if (notes != null) {
			if (notes.length() < 2) {
				throw new Exception("Les notes à propos de la facture doivent contenir au moins 2 caractères.");
			}
		} else {
			throw new Exception("Merci d'entrer des notes à propos de la facture.");
		}
	}

	private void validationSousTraitant(String sousTraitant) throws Exception {
		if (sousTraitant == null)

			throw new Exception("Merci de sélectionner le sous traitant.");

	}

	 
	private void setErreur(String champ, String message) {
		erreurs.put(champ, message);
	}

	 
	private static String getValeurChamp(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur;
		}
	}

}
