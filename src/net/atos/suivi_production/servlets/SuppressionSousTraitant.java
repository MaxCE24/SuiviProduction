package net.atos.suivi_production.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.atos.suivi_production.beans.SousTraitant;
import net.atos.suivi_production.dao.SousTraitantDAO;

public class SuppressionSousTraitant extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String PARAM_ID_SOUS_TRAITANT = "idSousTraitant";
	public static final String ATT_SOUS_TRAITANTS = "sousTraitants";
	public static final String VUE = "/listeSousTraitants";

	public static final String ATT_UTLISATEUR = "utilisateur";

	public SortedMap<Integer, SousTraitant> sousTraitants;

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Integer idSousTraitant = Integer.parseInt(getValeurParametre(request, PARAM_ID_SOUS_TRAITANT));

		HttpSession session = request.getSession();

		sousTraitants = (SortedMap<Integer, SousTraitant>) session.getAttribute(ATT_SOUS_TRAITANTS);

		if (idSousTraitant != null && sousTraitants != null) {

			try {
				new SousTraitantDAO().supprimer(sousTraitants.get(idSousTraitant));
			} catch (SQLException e) {

				e.printStackTrace();
			}
			sousTraitants.remove(idSousTraitant);

			session.setAttribute(ATT_SOUS_TRAITANTS, sousTraitants);
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
