package net.atos.suivi_production.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.atos.suivi_production.beans.DemandeDAchat;
import net.atos.suivi_production.dao.DemandeDAchatDAO;

public class SuppressionDemandeDAchat extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String PARAM_ID_DEMANDE_D_ACHAT = "idDemandeDAchat";
	public static final String ATT_DEMANDES_D_ACHAT = "demandesDAchat";
	public static final String VUE = "/listeDemandesDAchat";

	public static final String ATT_UTLISATEUR = "utilisateur";

	public SortedMap<Integer, DemandeDAchat> demandesDAchat;

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Integer idDemandeDAchat = Integer.parseInt(getValeurParametre(request, PARAM_ID_DEMANDE_D_ACHAT));

		HttpSession session = request.getSession();
		demandesDAchat = (SortedMap<Integer, DemandeDAchat>) session.getAttribute(ATT_DEMANDES_D_ACHAT);

		if (idDemandeDAchat != null && demandesDAchat != null) {

			try {
				new DemandeDAchatDAO().supprimer(demandesDAchat.get(idDemandeDAchat));
			} catch (SQLException e) {

				e.printStackTrace();
			}
			demandesDAchat.remove(idDemandeDAchat);

			session.setAttribute(ATT_DEMANDES_D_ACHAT, demandesDAchat);
		}

		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

	private static String getValeurParametre(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur;
		}
	}

}
