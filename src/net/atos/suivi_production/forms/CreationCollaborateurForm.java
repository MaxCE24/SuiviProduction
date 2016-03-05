package net.atos.suivi_production.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.atos.suivi_production.beans.Collaborateur;

public class CreationCollaborateurForm {

	private static final String CHAMP_NOM = "nomCollaborateur";
	private static final String CHAMP_PRENOM = "prenomCollaborateur";

	private Map<String, String> erreurs = new HashMap<String, String>();
	private String resultat;

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public String getResultat() {
		return resultat;
	}

	public Collaborateur creerCollaborateur(HttpServletRequest request) {
		String nom = getValeurChamp(request, CHAMP_NOM);
		String prenom = getValeurChamp(request, CHAMP_PRENOM);

		Collaborateur collaborateur = new Collaborateur();

		try {
			validationNomCollaborateur(nom);
		} catch (Exception e) {
			setErreur(CHAMP_NOM, e.getMessage());
		}

		collaborateur.setNom(nom);

		try {
			validationPrenomCollaborateur(prenom);
		} catch (Exception e) {
			setErreur(CHAMP_PRENOM, e.getMessage());
		}

		collaborateur.setPrenom(prenom);

		if (erreurs.isEmpty()) {
			resultat = "Succès de la création du collaborateur.";
		} else {
			resultat = "Échec de la création du collaborateur.";
		}

		return collaborateur;
	}

	private void validationNomCollaborateur(String nom) throws Exception {
		if (nom != null) {
			if (nom.length() < 2) {
				throw new Exception("Le nom du collaborateur doit contenir au moins 2 caractères.");
			}
		} else {
			throw new Exception("Merci d'entrer le nom du collaborateur.");
		}
	}

	private void validationPrenomCollaborateur(String prenom) throws Exception {
		if (prenom != null) {
			if (prenom.length() < 2) {
				throw new Exception("Le prénom du collaborateur doit contenir au moins 2 caractères.");
			}
		} else {
			throw new Exception("Merci d'entrer le prénom du collaborateur.");
		}
	}

	 
	private static String getValeurChamp(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur;
		}
	}

	private void setErreur(String champ, String message) {
		erreurs.put(champ, message);
	}

}
