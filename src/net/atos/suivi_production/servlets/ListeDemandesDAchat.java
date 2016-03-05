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
import net.atos.suivi_production.beans.Validation;
import net.atos.suivi_production.dao.DemandeDAchatDAO;
import net.atos.suivi_production.dao.ValidationDAO;

public class ListeDemandesDAchat extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ATT_DEMANDES_D_ACHAT = "demandesDAchat";
	public static final String ATT_VALIDATIONS = "validations";
	public static final String ATT_FORM = "form";
	public static final String VUE = "/WEB-INF/listerDemandesDAchat.jsp";

	public SortedMap<Integer, DemandeDAchat> demandesDAchat;
	public SortedMap<Integer, Validation> validations;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			demandesDAchat = new DemandeDAchatDAO().lister();
			validations = new ValidationDAO().lister();
		} catch (SQLException e) {
			 
			e.printStackTrace();
		}

		HttpSession session = request.getSession();
		session.setAttribute(ATT_DEMANDES_D_ACHAT, demandesDAchat);
		session.setAttribute(ATT_VALIDATIONS, validations);

		 
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
