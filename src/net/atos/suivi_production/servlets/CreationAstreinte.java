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
import net.atos.suivi_production.beans.Collaborateur;
import net.atos.suivi_production.dao.AstreinteDAO;
import net.atos.suivi_production.dao.CollaborateurDAO;
import net.atos.suivi_production.forms.CreationAstreinteForm;

public class CreationAstreinte extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String ATT_ASTREINTE = "astreinte";
	public static final String ATT_COLLABORATEURS = "collaborateurs";
	public static final String ATT_FORM = "form";
	public static final String VUE_SUCCES = "/listeAstreintes";
	public static final String VUE_FORM = "/WEB-INF/creerAstreinte.jsp";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			SortedMap<Integer, Collaborateur> collaborateurs = new CollaborateurDAO().lister();
			HttpSession session = request.getSession();
			session.setAttribute(ATT_COLLABORATEURS, collaborateurs);
		} catch (SQLException e) {

			e.printStackTrace();
		}

		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CreationAstreinteForm form = new CreationAstreinteForm();

		Astreinte astreinte = null;
		try {
			astreinte = form.creerAstreinte(request);
		} catch (Exception e1) {

			e1.printStackTrace();
		}

		request.setAttribute(ATT_ASTREINTE, astreinte);
		request.setAttribute(ATT_FORM, form);
		if (form.getErreurs().isEmpty()) {

			try {
				new AstreinteDAO().creer(astreinte);
			} catch (SQLException e) {

				e.printStackTrace();
			}

			response.sendRedirect(request.getContextPath() + VUE_SUCCES);
		} else {

			this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
		}
	}

}