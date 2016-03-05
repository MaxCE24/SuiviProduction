package net.atos.suivi_production.forms;

import javax.servlet.http.HttpServletRequest;

import net.atos.suivi_production.beans.Equipe;

public class CreationEquipeForm {

	private static final String CHAMP_NOM = "nomEquipe";

	public Equipe creerEquipe(HttpServletRequest request) {
		String nom = getValeurChamp(request, CHAMP_NOM);

		Equipe equipe = new Equipe();
		equipe.setNom(nom);

		return equipe;
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
