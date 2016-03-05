package net.atos.suivi_production.forms;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import net.atos.suivi_production.beans.Astreinte;
import net.atos.suivi_production.beans.Collaborateur;
import net.atos.suivi_production.dao.CollaborateurDAO;

public class CreationAstreinteForm {

	public static final String CHAMP_REFERENCE = "referenceAstreinte";
	public static final String CHAMP_DATE = "dateAstreinte";
	public static final String CHAMP_NOMBRE_HEURES = "nombreHeuresAstreinte";
	public static final String CHAMP_TYPE = "typeAstreinte";
	public static final String CHAMP_NOM_COLLABORATEUR = "nomCollaborateur";
	public static final String CHAMP_PRENOM_COLLABORATEUR = "prenomCollaborateur";

	private static final String CHAMP_CHOIX_COLLABORATEUR = "choixNouveauCollaborateur";

	private static final String ANCIEN_COLLABORATEUR = "ancienCollaborateur";
	private static final String CHAMP_COLLABORATEUR = "listeCollaborateurs";

	private static final String FORMAT_DATE = "yyyy-MM-dd";
	private static final String ATT_COLLABORATEURS = "collaborateurs";

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public String getResultat() {
		return resultat;
	}

	@SuppressWarnings("unchecked")
	public Astreinte creerAstreinte(HttpServletRequest request) throws IOException {
		String reference = getValeurChamp(request, CHAMP_REFERENCE);
		String date = getValeurChamp(request, CHAMP_DATE);
		String nombreHeures = getValeurChamp(request, CHAMP_NOMBRE_HEURES);
		String type = getValeurChamp(request, CHAMP_TYPE);
		Astreinte astreinte = new Astreinte();

		try {
			validationReference(reference);
		} catch (Exception e) {
			setErreur(CHAMP_REFERENCE, e.getMessage());
		}
		astreinte.setReference(reference);

		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
		Date parsedDate = new Date();
		try {
			validationDate(date);
			parsedDate = format.parse(date);

		} catch (Exception e) {
			setErreur(CHAMP_DATE, e.getMessage());
		}
		astreinte.setDate(parsedDate);

		double valeurNombreHeures = -1;
		try {
			valeurNombreHeures = validationNombreHeures(nombreHeures);

		} catch (Exception e) {
			setErreur(CHAMP_NOMBRE_HEURES, e.getMessage());
		}
		astreinte.setNombreHeures(valeurNombreHeures);

		try {
			validationType(type);
		} catch (Exception e) {
			setErreur(CHAMP_TYPE, e.getMessage());
		}
		astreinte.setType(type);

		Collaborateur collaborateur = null;
		String choixNouveauCollaborateur = getValeurChamp(request, CHAMP_CHOIX_COLLABORATEUR);
		if (ANCIEN_COLLABORATEUR.equals(choixNouveauCollaborateur)) {

			try {
				if (getValeurChamp(request, CHAMP_COLLABORATEUR) != null) {
					Integer idCollaborateur = Integer.parseInt(getValeurChamp(request, CHAMP_COLLABORATEUR));
					HttpSession session = request.getSession();
					collaborateur = (Collaborateur) ((Map<Integer, Collaborateur>) session
							.getAttribute(ATT_COLLABORATEURS)).get(idCollaborateur);
				} else
					throw new Exception("Merci de sélectionner un collaborateur.");
			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();
			} catch (Exception e) {

				setErreur(CHAMP_NOM_COLLABORATEUR, e.getMessage());
			}

			astreinte.setCollaborateur(collaborateur);

		} else {
			CreationCollaborateurForm collaborateurForm = new CreationCollaborateurForm();

			collaborateur = collaborateurForm.creerCollaborateur(request);

			boolean cEstUnNouveauCollaborateur = false;

			try {
				validationNomCollaborateur(collaborateur);
				cEstUnNouveauCollaborateur = true;
			} catch (Exception e) {
				setErreur(CHAMP_NOM_COLLABORATEUR, e.getMessage());
				cEstUnNouveauCollaborateur = false;
			}

			try {
				validationPrenomCollaborateur(collaborateur);
				cEstUnNouveauCollaborateur = true;
			} catch (Exception e) {
				setErreur(CHAMP_PRENOM_COLLABORATEUR, e.getMessage());
				cEstUnNouveauCollaborateur = false;
			}

			try {
				if (cEstUnNouveauCollaborateur) {
					collaborateur.setId(CollaborateurDAO.getID());

					new CollaborateurDAO().creer(collaborateur);

				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
			astreinte.setCollaborateur(collaborateur);

		}

		if (erreurs.isEmpty()) {
			resultat = "Succès de la création de l'astreinte.";
		} else {
			resultat = "Échec de la création de l'astreinte.";
		}

		return astreinte;
	}

	private void validationReference(String reference) throws Exception {
		if (reference != null) {
			if (reference.length() != 4) {
				throw new Exception("La référence d'astreinte doit contenir 4 caractères.");
			} else if (!(reference.charAt(0) == 'r' || reference.charAt(0) == 'R')) {
				throw new Exception("La référence doit commencer par 'R'.");
			} else if (!StringUtils.isNumeric(reference.substring(1, 4))) {
				throw new Exception("La référence doit se terminer par 3 chiffres.");
			}
		} else {
			throw new Exception("Merci d'entrer une référence d'astreinte.");
		}
	}

	private void validationDate(String date) throws Exception {
		if (date == null || date.trim().equals("")) {
			{
				throw new Exception("Merci d'entrer une date d'astreinte.");
			}
		}
	}

	private double validationNombreHeures(String nombreHeures) throws Exception {
		double temp = -1;
		if (nombreHeures == null) {
			throw new Exception("Merci d'entrer un nombre d'heures.");
		} else {
			temp = Double.parseDouble(nombreHeures);
		}
		return temp;
	}

	private void validationType(String type) throws Exception {
		if (type == null) {
			throw new Exception("Merci d'entrer un type d'astreinte.");
		}
	}

	private void validationNomCollaborateur(Collaborateur collaborateur) throws Exception {
		if (collaborateur.getNom() != null) {
			if (collaborateur.getNom().length() < 2) {
				throw new Exception("Le nom du collaborateur doit contenir au moins 2 caractères.");
			}
		} else {
			throw new Exception("Merci d'entrer le nom du collaborateur.");
		}
	}

	private void validationPrenomCollaborateur(Collaborateur collaborateur) throws Exception {
		if (collaborateur.getPrenom() != null) {
			if (collaborateur.getPrenom().length() < 2) {
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
