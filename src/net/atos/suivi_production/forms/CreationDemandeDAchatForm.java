package net.atos.suivi_production.forms;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.atos.suivi_production.beans.Collaborateur;
import net.atos.suivi_production.beans.DemandeDAchat;
import net.atos.suivi_production.beans.Validation;
import net.atos.suivi_production.dao.CollaborateurDAO;
import net.atos.suivi_production.dao.DemandeDAchatDAO;
import net.atos.suivi_production.dao.ValidationDAO;

public class CreationDemandeDAchatForm {

	public static final String CHAMP_NUMERO = "numeroDemandeDAchat";
	public static final String CHAMP_DATE = "dateDemandeDAchat";
	public static final String CHAMP_DESCRIPTION = "descriptionDemandeDAchat";
	public static final String CHAMP_STATUT = "statutDemandeDAchat";
	public static final String CHAMP_NUMERO_DE_BON_DE_COMMANDE = "numeroDeBonDeCommandeDemandeDAchat";

	public static final String CHAMP_NOM_COLLABORATEUR = "nomCollaborateur";
	public static final String CHAMP_PRENOM_COLLABORATEUR = "prenomCollaborateur";

	private static final String CHAMP_CHOIX_COLLABORATEUR = "choixNouveauCollaborateur";

	private static final String ANCIEN_COLLABORATEUR = "ancienCollaborateur";

	private static final String CHAMP_COLLABORATEUR = "listeCollaborateurs";

	private static final String CHAMP_COLLABORATEUR_1 = "listeCollaborateurs1";
	public static final String CHAMP_DATE_VALIDATION_1 = "dateValidation1DemandeDAchat";
	private static final String CHAMP_COLLABORATEUR_2 = "listeCollaborateurs2";
	public static final String CHAMP_DATE_VALIDATION_2 = "dateValidation2DemandeDAchat";
	private static final String CHAMP_COLLABORATEUR_3 = "listeCollaborateurs3";
	public static final String CHAMP_DATE_VALIDATION_3 = "dateValidation3DemandeDAchat";
	private static final String CHAMP_COLLABORATEUR_4 = "listeCollaborateurs4";
	public static final String CHAMP_DATE_VALIDATION_4 = "dateValidation4DemandeDAchat";
	private static final String CHAMP_COLLABORATEUR_5 = "listeCollaborateurs5";
	public static final String CHAMP_DATE_VALIDATION_5 = "dateValidation5DemandeDAchat";
	private static final String CHAMP_COLLABORATEUR_6 = "listeCollaborateurs6";
	public static final String CHAMP_DATE_VALIDATION_6 = "dateValidation6DemandeDAchat";

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
	public DemandeDAchat creerDemandeDAchat(HttpServletRequest request)
			throws IOException, SQLException, ParseException {

		DemandeDAchat demandeDAchat = new DemandeDAchat();
		String numero = getValeurChamp(request, CHAMP_NUMERO);
		String date = getValeurChamp(request, CHAMP_DATE);
		String description = getValeurChamp(request, CHAMP_DESCRIPTION);
		String statut = getValeurChamp(request, CHAMP_STATUT);
		String numeroDeBonDeCommande = getValeurChamp(request, CHAMP_NUMERO_DE_BON_DE_COMMANDE);
		String dateValidation1 = getValeurChamp(request, CHAMP_DATE_VALIDATION_1);
		String collaborateur1 = getValeurChamp(request, CHAMP_COLLABORATEUR_1);

		String dateValidation2 = getValeurChamp(request, CHAMP_DATE_VALIDATION_2);
		String collaborateur2 = getValeurChamp(request, CHAMP_COLLABORATEUR_2);

		String dateValidation3 = getValeurChamp(request, CHAMP_DATE_VALIDATION_3);
		String collaborateur3 = getValeurChamp(request, CHAMP_COLLABORATEUR_3);

		String dateValidation4 = getValeurChamp(request, CHAMP_DATE_VALIDATION_4);
		String collaborateur4 = getValeurChamp(request, CHAMP_COLLABORATEUR_4);

		String dateValidation5 = getValeurChamp(request, CHAMP_DATE_VALIDATION_5);
		String collaborateur5 = getValeurChamp(request, CHAMP_COLLABORATEUR_5);

		String dateValidation6 = getValeurChamp(request, CHAMP_DATE_VALIDATION_6);
		String collaborateur6 = getValeurChamp(request, CHAMP_COLLABORATEUR_6);

		int valeurNumero = -1;
		try {
			valeurNumero = validationNumero(numero);
		} catch (Exception e) {
			setErreur(CHAMP_NUMERO, e.getMessage());
		}
		demandeDAchat.setNumero(valeurNumero);

		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
		Date parsedDate = new Date();
		try {
			validationDate(date);
			parsedDate = format.parse(date);

		} catch (Exception e) {
			setErreur(CHAMP_DATE, e.getMessage());
		}
		demandeDAchat.setDate(parsedDate);

		try {
			validationDescription(description);
		} catch (Exception e) {
			setErreur(CHAMP_DESCRIPTION, e.getMessage());
		}
		demandeDAchat.setDescription(description);

		try {
			validationStatut(statut);
		} catch (Exception e) {
			setErreur(CHAMP_STATUT, e.getMessage());
		}
		demandeDAchat.setStatut(statut);

		int valeurNumeroDeBonDeCommande = -1;
		try {
			valeurNumeroDeBonDeCommande = validationNumeroDeBonDeCommande(numeroDeBonDeCommande);
		} catch (Exception e) {
			setErreur(CHAMP_NUMERO_DE_BON_DE_COMMANDE, e.getMessage());
		}
		demandeDAchat.setNumeroDeBonDeCommande(valeurNumeroDeBonDeCommande);

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

			demandeDAchat.setCollaborateur(collaborateur);

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
			demandeDAchat.setCollaborateur(collaborateur);
		}

		int idDemandeDAchat = 1;
		try {
			try {
				idDemandeDAchat = DemandeDAchatDAO.getID();
			} catch (Exception e) {

			}
		} catch (Exception e) {

		}

		demandeDAchat.setId(idDemandeDAchat);

		if ((dateValidation1 != null) && (collaborateur1 != null)) {

			Collaborateur valideur1 = null;
			Validation validation1 = null;

			try {

				Integer idValideur1 = Integer.parseInt(collaborateur1);
				HttpSession session = request.getSession();
				valideur1 = (Collaborateur) ((Map<Integer, Collaborateur>) session.getAttribute(ATT_COLLABORATEURS))
						.get(idValideur1);

			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (Exception e) {

			}
			validation1 = new Validation(ValidationDAO.getID(), format.parse(dateValidation1), valideur1,
					demandeDAchat);
			new ValidationDAO().creer(validation1);
			demandeDAchat.setValidations(new TreeMap<Integer, Validation>());
			demandeDAchat.getValidations().put(validation1.getId(), validation1);

		}

		if ((dateValidation2 != null) && (collaborateur2 != null)) {

			Collaborateur valideur2 = null;
			Validation validation2 = null;

			try {

				Integer idValideur2 = Integer.parseInt(collaborateur2);
				HttpSession session = request.getSession();
				valideur2 = (Collaborateur) ((Map<Integer, Collaborateur>) session.getAttribute(ATT_COLLABORATEURS))
						.get(idValideur2);

			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (Exception e) {

			}
			validation2 = new Validation(ValidationDAO.getID(), format.parse(dateValidation2), valideur2,
					demandeDAchat);
			new ValidationDAO().creer(validation2);
			demandeDAchat.setValidations(new TreeMap<Integer, Validation>());
			demandeDAchat.getValidations().put(validation2.getId(), validation2);

		}

		if ((dateValidation3 != null) && (collaborateur3 != null)) {

			Collaborateur valideur3 = null;
			Validation validation3 = null;

			try {

				Integer idValideur3 = Integer.parseInt(collaborateur3);
				HttpSession session = request.getSession();
				valideur3 = (Collaborateur) ((Map<Integer, Collaborateur>) session.getAttribute(ATT_COLLABORATEURS))
						.get(idValideur3);

			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (Exception e) {

			}
			validation3 = new Validation(ValidationDAO.getID(), format.parse(dateValidation3), valideur3,
					demandeDAchat);
			new ValidationDAO().creer(validation3);
			demandeDAchat.getValidations().put(validation3.getId(), validation3);

		}

		if ((dateValidation4 != null) && (collaborateur4 != null)) {

			Collaborateur valideur4 = null;
			Validation validation4 = null;

			try {

				Integer idValideur4 = Integer.parseInt(collaborateur4);
				HttpSession session = request.getSession();
				valideur4 = (Collaborateur) ((Map<Integer, Collaborateur>) session.getAttribute(ATT_COLLABORATEURS))
						.get(idValideur4);

			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (Exception e) {

			}
			validation4 = new Validation(ValidationDAO.getID(), format.parse(dateValidation4), valideur4,
					demandeDAchat);
			new ValidationDAO().creer(validation4);
			demandeDAchat.getValidations().put(validation4.getId(), validation4);

		}

		if ((dateValidation5 != null) && (collaborateur5 != null)) {

			Collaborateur valideur5 = null;
			Validation validation5 = null;

			try {

				Integer idValideur5 = Integer.parseInt(collaborateur5);
				HttpSession session = request.getSession();
				valideur5 = (Collaborateur) ((Map<Integer, Collaborateur>) session.getAttribute(ATT_COLLABORATEURS))
						.get(idValideur5);

			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (Exception e) {

			}
			validation5 = new Validation(ValidationDAO.getID(), format.parse(dateValidation5), valideur5,
					demandeDAchat);
			new ValidationDAO().creer(validation5);
			demandeDAchat.setValidations(new TreeMap<Integer, Validation>());
			demandeDAchat.getValidations().put(validation5.getId(), validation5);

		}

		if ((dateValidation6 != null) && (collaborateur6 != null)) {

			Collaborateur valideur6 = null;
			Validation validation6 = null;

			try {

				Integer idValideur6 = Integer.parseInt(collaborateur6);
				HttpSession session = request.getSession();
				valideur6 = (Collaborateur) ((Map<Integer, Collaborateur>) session.getAttribute(ATT_COLLABORATEURS))
						.get(idValideur6);

			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (Exception e) {

			}
			validation6 = new Validation(ValidationDAO.getID(), format.parse(dateValidation6), valideur6,
					demandeDAchat);
			new ValidationDAO().creer(validation6);
			demandeDAchat.setValidations(new TreeMap<Integer, Validation>());
			demandeDAchat.getValidations().put(validation6.getId(), validation6);

		}

		if (erreurs.isEmpty()) {
			resultat = "Succès de la création de la demande d'achat.";
		} else {
			resultat = "Échec de la création de la demande d'achat.";
		}

		return demandeDAchat;
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

	private void validationDescription(String description) throws Exception {
		if (description == null) {
			throw new Exception("Merci d'entrer une description de la demande d'achat.");
		}
	}

	private void validationStatut(String statut) throws Exception {
		if (statut == null) {
			throw new Exception("Merci d'entrer un statut de la demande d'achat.");
		}
	}

	private int validationNumeroDeBonDeCommande(String numeroDeBonDeCommande) throws Exception {
		int temp = -1;
		if (numeroDeBonDeCommande == null) {
			throw new Exception("Merci d'entrer un numéro de bon de commande.");
		} else {
			temp = Integer.parseInt(numeroDeBonDeCommande);
		}
		return temp;
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
