package net.atos.suivi_production.forms;

import javax.servlet.http.HttpServletRequest;

import net.atos.suivi_production.beans.Societe;

public class CreationSocieteForm {

	private static final String CHAMP_RAISON_SOCIALE = "raisonSocialeSociete";

	public Societe creerSociete(HttpServletRequest request) {

		String raisonSociale = getValeurChamp(request, CHAMP_RAISON_SOCIALE);
		Societe societe = new Societe();
		societe.setRaisonSociale(raisonSociale);

		return societe;

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
