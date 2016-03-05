package net.atos.suivi_production.forms;

import javax.servlet.http.HttpServletRequest;

import net.atos.suivi_production.beans.Profil;

public class CreationProfilForm {

	private static final String CHAMP_LIBELLE = "libelleProfil";

	public Profil creerProfil(HttpServletRequest request) {
		String libelle = getValeurChamp(request, CHAMP_LIBELLE);

		Profil profil = new Profil();

		profil.setLibelle(libelle);

		return profil;
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
