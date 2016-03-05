package net.atos.suivi_production.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.atos.suivi_production.beans.Facture;
import net.atos.suivi_production.dao.FactureDAO;

public class SuppressionFacture extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String PARAM_ID_FACTURE = "idFacture";
	public static final String ATT_FACTURES = "factures";
	public static final String VUE = "/listeFactures";

	public static final String ATT_UTLISATEUR = "utilisateur";

	public SortedMap<Integer, Facture> factures;

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 
		int idFacture = Integer.parseInt(getValeurParametre(request, PARAM_ID_FACTURE));
		 
		HttpSession session = request.getSession();

		factures = (SortedMap<Integer, Facture>) session.getAttribute(ATT_FACTURES);

		if (idFacture != 0 && factures != null) {
			 
			try {
				new FactureDAO().supprimer(factures.get(idFacture));
			} catch (SQLException e) {
				 
				e.printStackTrace();
			}
			factures.remove(idFacture);
			 

			session.setAttribute(ATT_FACTURES, factures);
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
