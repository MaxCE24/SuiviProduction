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

public class ListeSousTraitants extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ATT_SOUS_TRAITANTS = "sousTraitants";
	public static final String ATT_FORM = "form";
	public static final String VUE = "/WEB-INF/listerSousTraitants.jsp";

	public SortedMap<Integer, SousTraitant> sousTraitants;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			sousTraitants = new SousTraitantDAO().lister();
		} catch (SQLException e) {
			 
			e.printStackTrace();
		}
		
		HttpSession session = request.getSession();
		session.setAttribute(ATT_SOUS_TRAITANTS, sousTraitants);

		 
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
