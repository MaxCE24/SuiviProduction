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

public class ListeAstreintes extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String ATT_ASTREINTES = "astreintes";

	public static final String ATT_FORM = "form";
	public static final String VUE = "/WEB-INF/listerAstreintes.jsp";

	public SortedMap<Integer, Astreinte> astreintes;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			astreintes = new AstreinteDAO().lister();

		} catch (SQLException e) {
			 
			e.printStackTrace();
		}

		HttpSession session = request.getSession();
		session.setAttribute(ATT_ASTREINTES, astreintes);

		 
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
	}

}
