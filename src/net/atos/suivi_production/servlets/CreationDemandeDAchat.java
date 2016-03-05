package net.atos.suivi_production.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.atos.suivi_production.beans.Collaborateur;
import net.atos.suivi_production.beans.DemandeDAchat;
import net.atos.suivi_production.dao.CollaborateurDAO;
import net.atos.suivi_production.dao.DemandeDAchatDAO;
import net.atos.suivi_production.forms.CreationDemandeDAchatForm;

public class CreationDemandeDAchat extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ATT_DEMANDE_D_ACHAT = "demandeDAchat";
	public static final String ATT_COLLABORATEURS = "collaborateurs";
	public static final String ATT_FORM = "form";
	public static final String VUE_SUCCES = "/listeDemandesDAchat";
	public static final String VUE_FORM = "/WEB-INF/creerDemandeDAchat.jsp";

	public SortedMap<Integer, DemandeDAchat> demandesDAchat;

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

		CreationDemandeDAchatForm form = new CreationDemandeDAchatForm();

		DemandeDAchat demandeDAchat = null;
		try {
			demandeDAchat = form.creerDemandeDAchat(request);
		} catch (SQLException e1) {

			e1.printStackTrace();
		} catch (ParseException e) {

			e.printStackTrace();
		}

		request.setAttribute(ATT_DEMANDE_D_ACHAT, demandeDAchat);
		request.setAttribute(ATT_FORM, form);
		if (form.getErreurs().isEmpty()) {

			try {
				new DemandeDAchatDAO().creer(demandeDAchat);
			} catch (Exception e) {

			}

			response.sendRedirect(request.getContextPath() + VUE_SUCCES);
		} else {

			this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
		}
	}
}