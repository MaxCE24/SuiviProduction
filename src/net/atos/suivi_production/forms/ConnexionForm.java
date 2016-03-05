package net.atos.suivi_production.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.atos.suivi_production.config.Utilisateur;

public class ConnexionForm {
	private static final String CHAMP_EMAIL = "eMail";
	private static final String CHAMP_PASS = "motDePasse";
	private static final String CONF_UTLISATEUR = "utilisateur";

	private Utilisateur confUtilisateur;

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	public String getResultat() {
		return resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public Utilisateur connecterUtilisateur(HttpServletRequest request) {
		String eMail = getValeurChamp(request, CHAMP_EMAIL);
		String motDePasse = getValeurChamp(request, CHAMP_PASS);
		confUtilisateur = (Utilisateur) request.getAttribute(CONF_UTLISATEUR);
		Utilisateur utilisateur = new Utilisateur();
		try {
			validationEmail(eMail);
		} catch (Exception e) {
			setErreur(CHAMP_EMAIL, e.getMessage());
		}
		utilisateur.seteMail(eMail);
		try {
			if (eMail.equals(confUtilisateur.geteMail())) {
				validationMotDePasse(motDePasse);
			}
		} catch (Exception e) {
			setErreur(CHAMP_PASS, e.getMessage());
		}
		utilisateur.setMotDePasse(motDePasse);
		if (erreurs.isEmpty()) {
			resultat = "Succès de la connexion.";
		} else {
			resultat = "Échec de la connexion.";
		}
		return utilisateur;
	}

	private void validationEmail(String email) throws Exception {
		if (email != null && !email.matches("([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)")) {
			throw new Exception("Merci de saisir une adresse mail valide.");
		}
		if (!email.equals(confUtilisateur.geteMail())) {
			throw new Exception("L'adresse mail est incorrecte.");
		}
	}

	private void validationMotDePasse(String motDePasse) throws Exception {
		if (motDePasse != null) {
			if (motDePasse.length() < 3) {
				throw new Exception("Le mot de passe contient au moins 3 caractères.");
			} else if (!motDePasse.equals(confUtilisateur.getMotDePasse())) {
				throw new Exception("Le mot de passe est incorrecte.");
			}
		} else {
			throw new Exception("Merci de saisir votre mot de passe.");
		}
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