package net.atos.suivi_production.forms;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.atos.suivi_production.beans.Equipe;
import net.atos.suivi_production.beans.Profil;
import net.atos.suivi_production.beans.Societe;
import net.atos.suivi_production.beans.SousTraitant;
import net.atos.suivi_production.dao.EquipeDAO;
import net.atos.suivi_production.dao.ProfilDAO;
import net.atos.suivi_production.dao.SocieteDAO;

public class CreationSousTraitantForm {

	public static final String CHAMP_NOM = "nomSousTraitant";
	public static final String CHAMP_PRENOM = "prenomSousTraitant";
	public static final String CHAMP_SEXE = "sexeSousTraitant";

	public static final String CHAMP_NUMERO_DE_TELEPHONE = "numeroDeTelephoneSousTraitant";
	public static final String CHAMP_DATE_RECRUTEMENT = "dateRecrutementSousTraitant";

	public static final String CHAMP_LIBELLE_PROFIL = "libelleProfil";
	public static final String CHAMP_NOM_EQUIPE = "nomEquipe";
	public static final String CHAMP_RAISON_SOCIALE_SOCIETE = "raisonSocialeSociete";

	private static final String CHAMP_CHOIX_PROFIL = "choixNouveauProfil";
	private static final String ANCIEN_PROFIL = "ancienProfil";
	private static final String CHAMP_PROFIL = "listeProfils";

	private static final String CHAMP_CHOIX_EQUIPE = "choixNouveauEquipe";
	private static final String ANCIEN_EQUIPE = "ancienEquipe";
	private static final String CHAMP_EQUIPE = "listeEquipes";

	private static final String CHAMP_CHOIX_SOCIETE = "choixNouveauSociete";
	private static final String ANCIEN_SOCIETE = "ancienSociete";
	private static final String CHAMP_SOCIETE = "listeSocietes";

	private static final String FORMAT_DATE = "yyyy-MM-dd";

	private static final String ATT_PROFILS = "profils";
	private static final String ATT_EQUIPES = "equipes";
	private static final String ATT_SOCIETES = "societes";

	private String resultat;
	private Map<String, String> erreurs = new HashMap<String, String>();

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public String getResultat() {
		return resultat;
	}

	@SuppressWarnings("unchecked")
	public SousTraitant creerSousTraitant(HttpServletRequest request) throws IOException {
		SousTraitant sousTraitant = new SousTraitant();

		String nom = getValeurChamp(request, CHAMP_NOM);
		String prenom = getValeurChamp(request, CHAMP_PRENOM);
		String sexe = getValeurChamp(request, CHAMP_SEXE);

		String numeroDeTelephone = getValeurChamp(request, CHAMP_NUMERO_DE_TELEPHONE);
		String dateRecrutement = getValeurChamp(request, CHAMP_DATE_RECRUTEMENT);

		try {
			validationNom(nom);
		} catch (Exception e) {
			setErreur(CHAMP_NOM, e.getMessage());
		}
		sousTraitant.setNom(nom);

		try {
			validationPrenom(prenom);
		} catch (Exception e) {
			setErreur(CHAMP_PRENOM, e.getMessage());
		}
		sousTraitant.setPrenom(prenom);

		try {
			validationSexe(sexe);
		} catch (Exception e) {
			setErreur(CHAMP_SEXE, e.getMessage());
		}
		sousTraitant.setSexe(sexe);

		try {
			validationNumeroDeTelephone(numeroDeTelephone);
		} catch (Exception e) {
			setErreur(CHAMP_NUMERO_DE_TELEPHONE, e.getMessage());
		}
		sousTraitant.setNumeroDeTelephone(numeroDeTelephone);

		SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE);
		Date parsedDate = new Date();
		try {
			validationDate(dateRecrutement);
			parsedDate = format.parse(dateRecrutement);

		} catch (Exception e) {
			setErreur(CHAMP_DATE_RECRUTEMENT, e.getMessage());
		}
		sousTraitant.setDateRecrutement(parsedDate);

		Profil profil = null;

		String choixNouveauProfil = getValeurChamp(request, CHAMP_CHOIX_PROFIL);
		if (ANCIEN_PROFIL.equals(choixNouveauProfil)) {

			try {
				if (getValeurChamp(request, CHAMP_PROFIL) != null) {
					Integer idProfil = Integer.parseInt(getValeurChamp(request, CHAMP_PROFIL));
					HttpSession session = request.getSession();
					profil = (Profil) ((Map<Integer, Profil>) session.getAttribute(ATT_PROFILS)).get(idProfil);
				} else
					throw new Exception("Merci de sélectionner un profil.");
			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();
			} catch (Exception e) {

				setErreur(CHAMP_LIBELLE_PROFIL, e.getMessage());
			}

			sousTraitant.setProfil(profil);

		} else {

			CreationProfilForm profilForm = new CreationProfilForm();

			profil = profilForm.creerProfil(request);

			boolean cEstUnNouveauProfil = false;

			try {
				validationLibelleProfil(profil);
				cEstUnNouveauProfil = true;
			} catch (Exception e) {
				setErreur(CHAMP_LIBELLE_PROFIL, e.getMessage());
				cEstUnNouveauProfil = false;
			}

			try {
				if (cEstUnNouveauProfil) {
					profil.setId(ProfilDAO.getID());
					new ProfilDAO().creer(profil);

				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
			sousTraitant.setProfil(profil);

		}

		Equipe equipe = null;

		String choixNouveauEquipe = getValeurChamp(request, CHAMP_CHOIX_EQUIPE);
		if (ANCIEN_EQUIPE.equals(choixNouveauEquipe)) {

			try {
				if (getValeurChamp(request, CHAMP_EQUIPE) != null) {
					Integer idEquipe = Integer.parseInt(getValeurChamp(request, CHAMP_EQUIPE));
					HttpSession session = request.getSession();
					equipe = (Equipe) ((Map<Integer, Equipe>) session.getAttribute(ATT_EQUIPES)).get(idEquipe);
				} else
					throw new Exception("Merci de sélectionner un equipe.");
			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();
			} catch (Exception e) {

				setErreur(CHAMP_NOM_EQUIPE, e.getMessage());
			}

			sousTraitant.setEquipe(equipe);

		} else {

			CreationEquipeForm equipeForm = new CreationEquipeForm();

			equipe = equipeForm.creerEquipe(request);

			boolean cEstUnNouveauEquipe = false;

			try {
				validationNomEquipe(equipe);
				cEstUnNouveauEquipe = true;
			} catch (Exception e) {
				setErreur(CHAMP_NOM_EQUIPE, e.getMessage());
				cEstUnNouveauEquipe = false;
			}

			try {
				if (cEstUnNouveauEquipe) {
					equipe.setId(EquipeDAO.getID());
					new EquipeDAO().creer(equipe);

				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
			sousTraitant.setEquipe(equipe);

		}

		Societe societe = null;

		String choixNouveauSociete = getValeurChamp(request, CHAMP_CHOIX_SOCIETE);
		if (ANCIEN_SOCIETE.equals(choixNouveauSociete)) {

			try {
				if (getValeurChamp(request, CHAMP_SOCIETE) != null) {
					Integer idSociete = Integer.parseInt(getValeurChamp(request, CHAMP_SOCIETE));
					HttpSession session = request.getSession();
					societe = (Societe) ((Map<Integer, Societe>) session.getAttribute(ATT_SOCIETES)).get(idSociete);
				} else
					throw new Exception("Merci de sélectionner un societe.");
			} catch (NumberFormatException e) {

				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();
			} catch (Exception e) {

				setErreur(CHAMP_RAISON_SOCIALE_SOCIETE, e.getMessage());
			}

			sousTraitant.setSociete(societe);

		} else {

			CreationSocieteForm societeForm = new CreationSocieteForm();

			societe = societeForm.creerSociete(request);

			boolean cEstUnNouveauSociete = false;

			try {
				validationRaisonSocialeSociete(societe);
				cEstUnNouveauSociete = true;
			} catch (Exception e) {
				setErreur(CHAMP_RAISON_SOCIALE_SOCIETE, e.getMessage());
				cEstUnNouveauSociete = false;
			}

			try {
				if (cEstUnNouveauSociete) {
					societe.setId(SocieteDAO.getID());
					new SocieteDAO().creer(societe);

				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
			sousTraitant.setSociete(societe);

		}

		if (erreurs.isEmpty()) {
			resultat = "Succès de création du sous traitant.";
		} else {
			resultat = "Échec de création du sous traitant.";
		}

		return sousTraitant;
	}

	private void validationLibelleProfil(Profil profil) throws Exception {
		if (profil.getLibelle() != null) {
			if (profil.getLibelle().length() < 2) {
				throw new Exception("Le libellé du profil doit contenir au moins 2 caractères.");
			}
		} else {
			throw new Exception("Merci d'entrer le libellé du profil.");
		}

	}

	private void validationNomEquipe(Equipe equipe) throws Exception {
		if (equipe.getNom() != null) {
			if (equipe.getNom().length() < 2) {
				throw new Exception("Le nom d'équipe doit contenir au moins 2 caractères.");
			}
		} else {
			throw new Exception("Merci d'entrer le nom d'équipe.");
		}

	}

	private void validationRaisonSocialeSociete(Societe societe) throws Exception {
		if (societe.getRaisonSociale() != null) {
			if (societe.getRaisonSociale().length() < 2) {
				throw new Exception("La raison socialé de la société doit contenir au moins 2 caractères.");
			}
		} else {
			throw new Exception("Merci d'entrer la raison sociale de la société.");
		}

	}

	private void validationNom(String nom) throws Exception {
		if (nom != null) {
			if (nom.length() < 2) {
				throw new Exception("Le nom du sous traitant doit contenir au moins 2 caractères.");
			}
		} else {
			throw new Exception("Merci d'entrer un nom de sous traitant.");
		}
	}

	private void validationPrenom(String prenom) throws Exception {
		if (prenom != null) {
			if (prenom.length() < 2) {
				throw new Exception("Le prenom du sous traitant doit contenir au moins 2 caractères.");
			}
		} else {
			throw new Exception("Merci d'entrer un prenom de sous traitant.");
		}
	}

	private void validationSexe(String sexe) throws Exception {
		if (sexe == null) {
			throw new Exception("Merci de sélectionner le sexe.");
		}
	}

	private void validationNumeroDeTelephone(String numeroDeTelephone) throws Exception {
		if (numeroDeTelephone != null) {
			if (numeroDeTelephone.length() < 9) {
				throw new Exception("Le numero de téléphone du sous traitant doit contenir au moins 9 caractères.");
			}
		} else {
			throw new Exception("Merci d'entrer un numero de téléphone du sous traitant.");
		}
	}

	private void validationDate(String date) throws Exception {
		if (date == null || date.trim().equals("")) {
			{
				throw new Exception("Merci d'entrer une date de recrutement.");
			}
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
