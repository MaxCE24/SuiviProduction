package net.atos.suivi_production.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.atos.suivi_production.beans.Facture;
import net.atos.suivi_production.dao.FactureDAO;
import net.atos.suivi_production.forms.CreationFactureForm;

public class CreationFacture extends HttpServlet {

	 
	private static final long serialVersionUID = 1L;
	 
	public static final String ATT_FACTURE = "facture";
	public static final String ATT_FORM = "form";
	public static final String VUE_SUCCES = "/listeFactures";
	public static final String VUE_FORM = "/WEB-INF/creerFacture.jsp";

	public SortedMap<Integer, Facture> factures;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		 
		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		CreationFactureForm form = new CreationFactureForm();
		 
		Facture facture = form.creerFacture(request);
		 
		request.setAttribute(ATT_FACTURE, facture);
		request.setAttribute(ATT_FORM, form);
		if (form.getErreurs().isEmpty()) {

			try {
				new FactureDAO().creer(facture);
			} catch (SQLException e) {
				 
				e.printStackTrace();
			}

			 
			response.sendRedirect(request.getContextPath() + VUE_SUCCES);
		} else {
			 
			this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
		}
	}
}