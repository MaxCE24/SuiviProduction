package net.atos.suivi_production.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.atos.suivi_production.beans.Collaborateur;
import net.atos.suivi_production.dao.CollaborateurDAO;
import net.atos.suivi_production.forms.CreationCollaborateurForm;

public class CreationCollaborateur extends HttpServlet {

	 
	private static final long serialVersionUID = 1L;
	 
	public static final String ATT_COLLABORATEUR = "collaborateur";
	public static final String ATT_FORM = "form";
	public static final String VUE_SUCCES = "/listeDemandesDAchat";
	public static final String VUE_FORM = "/WEB-INF/creerCollaborateur.jsp";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 
		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		CreationCollaborateurForm form = new CreationCollaborateurForm();
		 

		Collaborateur collaborateur = null;
		try {
			collaborateur = form.creerCollaborateur(request);
		} catch (Exception e1) {
			 
			e1.printStackTrace();
		}

		 
		request.setAttribute(ATT_COLLABORATEUR, collaborateur);
		request.setAttribute(ATT_FORM, form);
		if (form.getErreurs().isEmpty()) {

			try {
				new CollaborateurDAO().creer(collaborateur);
			} catch (SQLException e) {
				 
				e.printStackTrace();
			}

			 
			response.sendRedirect(request.getContextPath() + VUE_SUCCES);
		} else {
			 
			this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
		}
	}

}