package net.atos.suivi_production.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.atos.suivi_production.beans.Astreinte;
import net.atos.suivi_production.dao.AstreinteDAO;

public class SuppressionAstreinte extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static final String PARAM_ID_ASTREINTE = "idAstreinte";
	public static final String ATT_ASTREINTES = "astreintes";
	public static final String VUE = "/listeAstreintes";

	public static final String ATT_UTLISATEUR = "utilisateur";

	public SortedMap<Integer, Astreinte> astreintes;

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int idAstreinte = Integer.parseInt(getValeurParametre(request, PARAM_ID_ASTREINTE));

		HttpSession session = request.getSession();
		astreintes = (SortedMap<Integer, Astreinte>) session.getAttribute(ATT_ASTREINTES);

		if (idAstreinte != 0 && astreintes != null) {

			try {
				new AstreinteDAO().supprimer(astreintes.get(idAstreinte));
			} catch (SQLException e) {

				e.printStackTrace();
			}
			astreintes.remove(idAstreinte);

			session.setAttribute(ATT_ASTREINTES, astreintes);
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